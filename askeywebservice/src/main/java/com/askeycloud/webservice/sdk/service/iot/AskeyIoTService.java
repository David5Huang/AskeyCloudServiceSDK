package com.askeycloud.webservice.sdk.service.iot;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;

import java.util.ArrayList;

import com.askeycloud.sdk.device.response.AWSIoTCertResponse;
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.iot.callback.ShadowReceiveListener;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import com.askeycloud.webservice.sdk.iot.MqttActionConst;
import com.askeycloud.webservice.sdk.iot.callback.MqttConnectionCallback;
import com.askeycloud.webservice.sdk.iot.MqttService;
import com.askeycloud.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import com.askeycloud.webservice.sdk.service.device.AskeyIoTDeviceService;

/**
 * Created by david5_huang on 2016/8/3.<br/>
 * Using AskeyIoTService, needing to follow this steps:<br/>
 * 1. Register MqttService at AndroidManifest.<br/>
 * 2. Get certificate via {@link AskeyIoTDeviceService#getIotCert()}.
 * 3. Use the parameter from AWSIoTCertResponse to connection MQTT server. See {@link #connectToAWSIot(String, String, String, MqttConnectionCallback)}
 * <br/>
 */
public class AskeyIoTService extends AskeyIoTApiService {

    private static AskeyIoTService instance;

    private MqttService mqttService;

    private ServiceConnection serviceConnection;

    public static AskeyIoTService getInstance(Context context) {
        if (instance == null) {
            instance = new AskeyIoTService(context);
        }
        else {
            if(!instance.context.equals(context)){
                instance = new AskeyIoTService(context);
            }
        }
        return instance;
    }

    AskeyIoTService(Context context) {
        super(context);
    }


    private void connectIoTMQTTService(final String endpoint,
                                      final String userName,
                                      final AWSIoTCertResponse certResponse,
                                      final ArrayList<String> topics,
                                      final MqttServiceConnectedCallback serviceCallback) {
        configAWSIot(endpoint, new MqttServiceConnectedCallback() {
            @Override
            public void onMqttServiceConnectedSuccess() {
                connectToAWSIot(userName, certResponse.getCertificatePem(), certResponse.getPrivateKey(), new MqttConnectionCallback() {
                    @Override
                    public void onConnected() {
                        for (int i = 0; i < topics.size(); i++) {
                            AskeyIoTService.getInstance(context).subscribeGetShadowMqtt(topics.get(i));
                            AskeyIoTService.getInstance(context).subscribeMqttDelta(topics.get(i));
                        }
                        serviceCallback.onMqttServiceConnectedSuccess();
                    }

                    @Override
                    public void unConnected(AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus status) {
                    }
                });
            }

            @Override
            public void onMqttServiceConnectedError() {
                Toast.makeText(context, "MqttService create error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Using AskeyIoTService to publish IoT message need follow this steps:
     * step1: configAWSIot<br/>
     * step2: connectToAWSIot<br/>
     * step3: subscribeMqtt <b>or<b/> publishMqttMessage
     *
     * @param endpoint aws iot thing endpoint.
     */
    public void configAWSIot(final String endpoint, final MqttServiceConnectedCallback callback) {
        serviceConnection = new ServiceConnection() {
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
        context.bindService(new Intent(context, MqttService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * @param callback The callback can set null, but it will use default callback(implement in MqttService).
     */
    @Deprecated
    public void connectToAWSIot(MqttConnectionCallback callback) {
        if (mqttService != null && mqttService.isMqttManagerConfig()) {
            mqttService.mqttManageConnect(callback);
        }
    }


    /**
     * @param userName This parameter use to create keystore file name. Recommended SDK user use a unique name for every end user.
     * @param cert Connected to AWS IoT MQTT server needed parameter. See <a href="http://com.askeycloud.sdk.javadoc.s3-website-us-west-2.amazonaws.com/com/askeycloud/sdk/device/response/AWSIoTCertResponse.html">AWSIoTCertResponse</a>
     * @param pk Connected to AWS IoT MQTT server needed parameter. See <a href="http://com.askeycloud.sdk.javadoc.s3-website-us-west-2.amazonaws.com/com/askeycloud/sdk/device/response/AWSIoTCertResponse.html">AWSIoTCertResponse</a>
     * @param callback Connection result callback.
     */
    public void connectToAWSIot(String userName, String cert, String pk, MqttConnectionCallback callback) {
        if (mqttService != null && mqttService.isMqttManagerConfig()) {
//            Log.e("ia4test", "connectToAWSIot if enter");
            mqttService.mqttManageConnect(userName, cert, pk, callback);
        }
    }

    public void connectToAWSIot(String accessKey, String secretKey, MqttConnectionCallback callback){
        if(mqttService != null && mqttService.isMqttManagerConfig()){
            mqttService.mqttManagerConnect(accessKey, secretKey, callback);
        }
    }

    public void disconnectIoTMQTTManager(){
        if(mqttService != null){
            Log.d(DebugConst.DEBUG_TAG, "MQTT manager disconnect.");
            mqttService.mqttManagerDisconnect();
        }
    }

    /**
     * @param topic   AWS IoT thing use topic.
     * @param mqttMsg
     */
    private void publishMqttMessage(String topic, String mqttMsg) {
        if (mqttService != null && mqttService.isMqttManagerConnected()) {
            mqttService.publishMqttMessage(topic, mqttMsg);
        } else {
            Log.e(this.getClass().getName(), "mqtt manager not connected.");
        }
    }

    /**
     * Publish IoT reported message.<br/>
     * Reported message is using to record IoT thing states and detected data, e.g., update data when temperature changed.
     *
     * @param shadowTopic aws thing used shadow topic.
     * @param builder     reported message builder
     */
    public void publishReportedMessage(String shadowTopic, MqttMsgBuilder builder) {
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Publish IoT "desired" tag message, desired is use for control IoT thing, e.g., control switch on/off.
     *
     * @param shadowTopic aws thing used shadow topic.
     * @param builder     desired message builder
     */
    public void publishDesiredMessage(String shadowTopic, MqttMsgBuilder builder) {
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Publish IoT desired and reported message.
     *
     * @param shadowTopic
     * @param builder
     */
    public void publishStatesMqttMessage(String shadowTopic, MqttMsgBuilder builder) {
        publishMqttMessage(shadowTopic, builder.buildMqttMessage());
    }

    /**
     * Subscribe IoT thing to MQTT manager, when receive message from AWS IoT service,
     * Askey Web Service SDK will broadcast data by using LocalBroadcastManager.<br/>
     * <p>
     * About receive action you can see MqttActionConst.
     *
     * @param topic The aws iot thing generate topic.(Get this topic from server.)
     * @see MqttActionConst
     */
    public void subscribeMqtt(String topic) {
        if (mqttService.isMqttManagerConnected()) {
            mqttService.subscribeMqttTopic(topic);
            Log.d(this.getClass().getName(), "mqtt subscribe success.");
        } else {
            Log.e(this.getClass().getName(), "mqtt manager not connected.");
            throw new IllegalArgumentException("mqtt manager not connected.");
        }
    }

    /**
     * @param topic Subscribe MQTT shadow when receive "GET" topic.
     */
    public void subscribeGetShadowMqtt(String topic) {
        if (!topic.contains("/get")) {
            String shadowTopic = topic.substring(0, topic.indexOf("/shadow") + (new String("/shadow").length()));
            topic = shadowTopic + "/get/accepted";
        }
        Log.e("ia4test", "get accept: " + topic);
        if (mqttService.isMqttManagerConnected()) {
            mqttService.subscribeMqttTopic(topic);
            Log.d(this.getClass().getName(), "mqtt subscribe success.");
        } else {
            Log.e(this.getClass().getName(), "mqtt manager not connected.");
            throw new IllegalArgumentException("mqtt manager not connected.");
        }
    }

    /**
     * Subscribe IoT thing to MQTT manager.<br/>
     * Subscribe delta is means, when data is different between "desired" tag and "reported" tag,
     * the MQTT manager will receive the different value, otherwise not.<br/>
     *
     * @param topic The aws iot thing generate topic.(Get this topic from server.)
     */
    public void subscribeMqttDelta(String topic) {
        if (!topic.contains("/delta")) {
            topic = new StringBuilder(topic).append("/delta").toString();
        }
        subscribeMqtt(topic);
    }

    /**
     * Get IoT shadow via MQTT.
     * @param topic The aws iot thing generate topic.(Get this topic from server.)
     */
    public void readThingShadow(String topic) {
        if (!topic.contains("/get")) {
            String shadowTopic = topic.substring(0, topic.indexOf("/shadow") + (new String("/shadow").length()));
            topic = shadowTopic + "/get";
        }
        mqttService.publishGetShodowMqtt(topic);
    }

    public MqttService getMqttService() {
        return mqttService;
    }

    /**
     * SDK default throw IoT shadow via BroadcastReceiver.<br/>
     * But developer can set ShadowReceiveListener, let MqttService throw IoT shadow by BroadcastReceiver and callback.
     * @param shadowReceiverListener The listener to receive IoT shadow and return result.
     */
    @Override
    public void setShadowReceiverListener(ShadowReceiveListener shadowReceiverListener) {
        super.setShadowReceiverListener(shadowReceiverListener);
        mqttService.setShadowReceiveListener(shadowReceiverListener);
    }

}
