package com.askeycloud.webservice.sdk.iot;

/**
 * Created by david5_huang on 2016/7/20.
 */
public interface MqttActionConst {
    /**
     * Mqtt subscribe接收到訊息時<br/>
     * Broadcast 使用的 action name
     */
    public static final String MQTT_RECEIVER_MESSAGE_ACTION = "mqtt_receiver_message";
    public static final String MQTT_GET_SHADOW_ACTION = "mqtt_get_shadow";

    /**
     * Mqtt subscribe接收到訊息時<br/>
     * Broadcast 接收到的 data, 所使用的 intent tag
     */
    public static final String MQTT_RECEIVER_MESSAGE_DATA_TAG = "mqtt_message_data_tag";

    public static final String MQTT_GET_SHADOW_DATA_TAG = "mqtt_get_shadow_data_tag";
}
