package tw.com.askey.webservice.sdk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import tw.com.askey.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.iot.MqttActionConst;
import tw.com.askey.webservice.sdk.iot.callback.MqttConnectionCallback;
import tw.com.askey.webservice.sdk.iot.MqttService;
import tw.com.askey.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import tw.com.askey.webservice.sdk.iot.callback.ReadThingShadowCallback;
import tw.com.askey.webservice.sdk.task.ReadThingShadowTask;

/**
 * Created by david5_huang on 2016/8/3.<br/>
 * Using AskeyIoTService, needing to follow this steps:<br/>
 * 1. Register MqttService at AndroidManifest.<br/>
 * 2. Get AWS key and token by AskeyWebService.active<br/>
 * <br/>
 * Only when you passive read IoT thing shadow, needn't connect MqttManager.<br/>
 */
public class AskeyIoTService {

    private final Context context;
    private static AskeyIoTService instance;

    private MqttService mqttService;

    public static AskeyIoTService getInstance(Context context){
        if(instance == null){
            instance = new AskeyIoTService(context);
        }
        return instance;
    }

    public AskeyIoTService(Context context){
        this.context = context;
    }

    /**
     * Using AskeyIoTService to publish IoT message need follow this steps:
     * step1: configAWSIot<br/>
     * step2: connectToAWSIot<br/>
     * step3: subscribeMqtt <b>or<b/> publishMqttMessage
     * @param endpoint aws iot thing endpoint.
     */
    public void configAWSIot(final String endpoint, final MqttServiceConnectedCallback callback){
        ServiceConnection mConnection = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mqttService = ((MqttService.MqttServiceBinder) service).getService();
                mqttService.configMqttManager(endpoint);
                callback.onMqttServiceConnectedSuccess();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mqttService = null;
                callback.onMqttServiceConnectedError();
            }
        };
        context.bindService(new Intent(context, MqttService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * @param callback The callback can set null, but it will use default callback(implement in MqttService).
     */
    public void connectToAWSIot(MqttConnectionCallback callback){
        if(mqttService != null && mqttService.isMqttManagerConfig()){
            mqttService.mqttManageConnect(callback);
        }
    }

//    public void connectToAWSIot(String cert, String pk, MqttConnectionCallback callback){
//        if(mqttService != null && mqttService.isMqttManagerConfig()){
//            mqttService.mqttManageConnect(cert, pk, callback);
//        }
//    }

    public void connectToAWSIot(String cert, String pk, MqttConnectionCallback callback){
        connectToAWSIot("", cert, pk, callback);
    }

    public void connectToAWSIot(String userName, String cert, String pk, MqttConnectionCallback callback){
        if(mqttService != null && mqttService.isMqttManagerConfig()){
            mqttService.mqttManageConnect(userName, cert, pk, callback);
        }
    }

    /**
     * @param topic AWS IoT thing use topic.
     * @param mqttMsg
     */
    private void publishMqttMessage(String topic, String mqttMsg){
        if(mqttService != null && mqttService.isMqttManagerConnected()){
            mqttService.publishMqttMessage(topic, mqttMsg);
        }
        else{
            Log.e(this.getClass().getName(), "mqtt manager not connected.");
        }
    }

    /**
     * Publish IoT reported message.<br/>
     * Reported message is using to record IoT thing states and detected data, e.g., update data when temperature changed.
     * @param shadowTopic aws thing used shadow topic.
     * @param builder reported message builder
     */
    public void publishReportedMessage(String shadowTopic, MqttMsgBuilder builder){
//        Log.e("ia4test", "report topic: "+(shadowTopic+reportTopic));
//        Log.e("ia4test", "report msg: "+builder.buildMqttMessage());

//        publishMqttMessage(shadowTopic+reportTopic, builder.buildMqttMessage());
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Publish IoT "desired" tag message, desired is use for control IoT thing, e.g., control switch on/off.
     * @param shadowTopic aws thing used shadow topic.
     * @param builder desired message builder
     */
    public void publishDesiredMessage(String shadowTopic, MqttMsgBuilder builder){
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Publish IoT desired and reported message.
     * @param shadowTopic
     * @param builder
     */
    public void publishStatesMqttMessage(String shadowTopic, MqttMsgBuilder builder){
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Subscribe IoT thing to MQTT manager, when receive message from AWS IoT service,
     * Askey Web Service SDK will broadcast data by using LocalBroadcastManager.<br/>
     *
     * About receive action you can see MqttActionConst.
     * @param topic The aws iot thing generate topic.(Get this topic from server.)
     * @see MqttActionConst
     */
    public void subscribeMqtt(String topic) {
        if(mqttService.isMqttManagerConnected()){
            mqttService.subscribeMqttTopic(topic);
            Log.d(this.getClass().getName(), "mqtt subscribe success.");
        }
        else{
            Log.e(this.getClass().getName(), "mqtt manager not connected.");
            throw new IllegalArgumentException("mqtt manager not connected.");
        }
    }

    /**
     * Subscribe IoT thing to MQTT manager.<br/>
     * Subscribe delta is means, when data is different between "desired" tag and "reported" tag,
     * the MQTT manager will receive the different value, otherwise not.<br/>
     * @param topic The aws iot thing generate topic.(Get this topic from server.)
     */
    public void subscribeMqttDelta(String topic){
        if(!topic.contains("/delta")){
            topic = new StringBuilder(topic).append("/delta").toString();
        }
        subscribeMqtt(topic);
    }

    /**
     * Read IoT thing shadow by using endpoint.<br/>
     * ReadThingShadowCallback must return length zero's string when this thing is no shadow.<br/>
     * <br/>
     * Because read shadow is using https protocol, so it's needn't to connecting MqttService.
     * @param endpoint aws iot thing endpoint.
     * @param thingName aws iot thing name.
     * @param readThingShadowCallback 取得資料回來後, 接收的 callback
     */
    public void readThingShadow(String endpoint, String thingName, ReadThingShadowCallback readThingShadowCallback) {
        if(ServicePreference.isCredentialsParamsExist(context)){
            ReadThingShadowTask task = new ReadThingShadowTask(context);
            task.setReadThingShadowCallback(readThingShadowCallback);
            task.execute(endpoint, thingName);
        }
    }

    public MqttService getMqttService() {
        return mqttService;
    }
}
