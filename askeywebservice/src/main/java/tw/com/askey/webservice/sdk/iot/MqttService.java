package tw.com.askey.webservice.sdk.iot;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttLastWillAndTestament;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import tw.com.askey.webservice.sdk.iot.callback.MqttConnectionCallback;
import tw.com.askey.webservice.sdk.iot.helper.IotKeyHelper;
import tw.com.askey.webservice.sdk.model.auth.CognitoDataModel;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.model.MqttUseCredentialsProvider;

/**
 * Created by david5_huang on 2016/7/15.
 */
public class MqttService extends Service implements AWSIotMqttClientStatusCallback, AWSIotMqttNewMessageCallback {

    private final String TAG = MqttService.class.getName();
    private static MqttService instance;

    protected AWSIotMqttManager mqttManager;
    protected AWSIotMqttManager readMqttManager;

    private final IBinder serviceBinder = new MqttServiceBinder();

    private boolean isMqttManagerConnected;
    private boolean isMqttManagerConfig;

    private MqttConnectionCallback mqttConnectionCallback;

    private IotKeyHelper iotKeyHelper;

    public class MqttServiceBinder extends Binder{
        public MqttService getService(){
            instance = MqttService.this;
            return instance;
        }
    }

    public static MqttService getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iotKeyHelper = new IotKeyHelper(getFilesDir().getAbsolutePath());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mqttManager.disconnect();
//        readMqttManager.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onStatusChanged(AWSIotMqttClientStatus status, Throwable throwable) {
        if (status == AWSIotMqttClientStatus.Connecting) {
            Log.d(TAG, "Mqtt manager connecting");
            mqttConnectionCallback.unConnected(status);
        } else if (status == AWSIotMqttClientStatus.Connected) {
            Log.d(TAG, "Mqtt manager connected");
            isMqttManagerConnected = true;
            mqttConnectionCallback.onConnected();
        } else if (status == AWSIotMqttClientStatus.Reconnecting) {
            if (throwable != null) {
                Log.e(TAG, "Connection error.", throwable);
            }
            Log.d(TAG, "Mqtt manager reconnecting");
            mqttConnectionCallback.unConnected(status);
        } else if (status == AWSIotMqttClientStatus.ConnectionLost) {
            if (throwable != null) {
                Log.e(TAG, "Connection error.", throwable);
            }
            Log.d(TAG, "Mqtt manager disconnected");
            isMqttManagerConnected = false;
            mqttConnectionCallback.unConnected(status);
        } else {
            Log.d(TAG, "Mqtt manager disconnected");
            isMqttManagerConnected = false;
            mqttConnectionCallback.unConnected(status);
        }
        if(throwable != null){
            Log.e(TAG, "Connection error.", throwable);
        }
    }

    @Override
    public void onMessageArrived(String topic, byte[] data) {
        try {
            Log.e(TAG, "topic: "+topic);
            if(topic.contains("/get")){
                Log.e(TAG, "get");
                Intent sendIntent = new Intent(MqttActionConst.MQTT_GET_SHADOW_ACTION);
                sendIntent.putExtra(MqttActionConst.MQTT_GET_SHADOW_DATA_TAG, new String(data, "UTF-8"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
            }
            else{
                Log.e(TAG, "receive");
                Intent sendIntent = new Intent(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION);
                sendIntent.putExtra(MqttActionConst.MQTT_RECEIVER_MESSAGE_DATA_TAG, new String(data, "UTF-8"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public boolean configMqttManager(String endpoint) {
//        if(!ServicePreference.isCredentialsParamsExist(this)){
//            isMqttManagerConfig = false;
//            return false;
//        }
        if(mqttManager == null){
            String clientId = UUID.randomUUID().toString();
            mqttManager = new AWSIotMqttManager(clientId, endpoint);
            mqttManager.setKeepAlive(10);
            AWSIotMqttLastWillAndTestament lwt = new AWSIotMqttLastWillAndTestament("my/lwt/topic",
                    "Android client lost connection", AWSIotMqttQos.QOS0);
            mqttManager.setMqttLastWillAndTestament(lwt);
            isMqttManagerConfig = true;
            return true;
        }
        else{
            isMqttManagerConfig = true;
            return true;
        }
    }

//    protected void initMqttManager(){
//        if(mqttManager == null || readMqttManager == null){
//            String clientId = UUID.randomUUID().toString();
//        }
//    }

    public void mqttManageConnect(MqttConnectionCallback connectionCallback) {
        CognitoDataModel dataModel = ServicePreference.getCognitoDataFromPreference(this);
        this.mqttConnectionCallback = connectionCallback;
        final MqttUseCredentialsProvider credentialsProvider = new MqttUseCredentialsProvider(new BasicSessionCredentials(dataModel.getAccessKey(), dataModel.getSecretKey(), dataModel.getSessionToken()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                mqttManager.connect(credentialsProvider, MqttService.this);
            }
        }).start();
    }

    public void mqttManageConnect(String userName, String cert, String pk, MqttConnectionCallback connectionCallback){
        this.mqttConnectionCallback = connectionCallback;
        Log.e("ia4test", "key name: "+iotKeyHelper.KEYSTORE_NAME+userName);
        iotKeyHelper.manageKeyStore(userName, cert, pk);
        if(iotKeyHelper.isClientKeystoreExist()){
            Log.e("ia4test", "key not null prepare connect");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mqttManager.connect(iotKeyHelper.getClientKeyStore(), MqttService.this);
                }
            }).start();
        }
    }

    public void mqttManagerDisconnect(){
        if(mqttManager != null){
            mqttManager.disconnect();
        }
    }

    public void publishMqttMessage(final String topic, final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mqttManager.publishString(message, topic, AWSIotMqttQos.QOS0);
            }
        }).start();
    }

    public void publishGetShodowMqtt(final String topic){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mqttManager.publishString("", topic, AWSIotMqttQos.QOS0);
            }
        }).start();
    }

    public void subscribeMqttTopic(String topic){
        mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0, this);
    }

    public boolean isMqttManagerConnected() {
        return isMqttManagerConnected && isMqttManagerConfig;
    }

    public boolean isMqttManagerConfig() {
        return isMqttManagerConfig;
    }

}
