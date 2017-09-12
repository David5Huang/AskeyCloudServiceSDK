package com.askeycloud.webservice.sdk.iot;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttLastWillAndTestament;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.iot.callback.MqttConnectionCallback;
import com.askeycloud.webservice.sdk.iot.callback.ShadowReceiveListener;
import com.askeycloud.webservice.sdk.iot.helper.IotKeyHelper;
import com.askeycloud.webservice.sdk.model.auth.CognitoDataModel;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.model.MqttUseCredentialsProvider;

/**
 * MqttService provide simply interface to control device MQTT shadow in AWS IoT.<br>
 * Before use this service, SDK user must register MqttService at AndroidManifest.
 */
public class MqttService extends Service implements AWSIotMqttClientStatusCallback, AWSIotMqttNewMessageCallback {

    private final String TAG = MqttService.class.getName();
    private static MqttService instance;

    protected AWSIotMqttManager mqttManager;

    private final IBinder serviceBinder = new MqttServiceBinder();

    private boolean isMqttManagerConnected;
    private boolean isMqttManagerConfig;

    private MqttConnectionCallback mqttConnectionCallback;
    private ShadowReceiveListener shadowReceiveListener;

    private IotKeyHelper iotKeyHelper;

    public class MqttServiceBinder extends Binder{
        public MqttService getService(){
            instance = MqttService.this;
            return instance;
        }
    }

    public static MqttService newInstance(){
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
        mqttManagerDisconnect();
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
            if(topic.contains("/get")){
                Intent sendIntent = new Intent(MqttActionConst.MQTT_GET_SHADOW_ACTION);
                sendIntent.putExtra(MqttActionConst.MQTT_GET_SHADOW_DATA_TAG, new String(data, "UTF-8"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
                if(shadowReceiveListener != null){
                    shadowReceiveListener.receiveShadowDocument(MqttActionConst.MQTT_GET_SHADOW_DATA_TAG, new String(data, "UTF-8"));
                }
            }
            else{
                Intent sendIntent = new Intent(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION);
                sendIntent.putExtra(MqttActionConst.MQTT_RECEIVER_MESSAGE_DATA_TAG, new String(data, "UTF-8"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
                if(shadowReceiveListener != null){
                    shadowReceiveListener.receiveShadowDocument(MqttActionConst.MQTT_RECEIVER_MESSAGE_DATA_TAG, new String(data, "UTF-8"));
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public boolean configMqttManager(String endpoint) {
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

    @Deprecated
    public void mqttManageConnect(MqttConnectionCallback connectionCallback) {
        CognitoDataModel dataModel = ServicePreference.getCognitoDataFromPreference(this);
        startMqttManagerConnect(new BasicSessionCredentials(dataModel.getAccessKey(), dataModel.getSecretKey(), dataModel.getSessionToken()),
                connectionCallback);

    }

    public void mqttManagerConnect(String accessKey, String secretKey, MqttConnectionCallback connectionCallback){
        startMqttManagerConnect(new BasicAWSCredentials(accessKey, secretKey),
                connectionCallback);
    }

    private void startMqttManagerConnect(AWSCredentials credentials, MqttConnectionCallback connectionCallback){
        this.mqttConnectionCallback = connectionCallback;
        final MqttUseCredentialsProvider credentialsProvider = new MqttUseCredentialsProvider(credentials);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mqttManager.connect(credentialsProvider, MqttService.this);
            }
        }).start();
    }

    public void mqttManageConnect(String userName, String cert, String pk, MqttConnectionCallback connectionCallback){
        this.mqttConnectionCallback = connectionCallback;
        Log.d(TAG, "key name: "+iotKeyHelper.KEYSTORE_NAME+userName);
        iotKeyHelper.manageKeyStore(userName, cert, pk);
        if(iotKeyHelper.isClientKeystoreExist()){
            Log.d(TAG, "key not null prepare connect");
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
            Log.d(DebugConst.DEBUG_TAG, "MQTT Service disconnect.");
            mqttManager.disconnect();
            mqttManager = null;
        }
        else{
            Log.d(DebugConst.DEBUG_TAG, "MQTT Manager null.");
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

    public ShadowReceiveListener getShadowReceiveListener() {
        return shadowReceiveListener;
    }

    public void setShadowReceiveListener(ShadowReceiveListener shadowReceiveListener) {
        this.shadowReceiveListener = shadowReceiveListener;
    }
}
