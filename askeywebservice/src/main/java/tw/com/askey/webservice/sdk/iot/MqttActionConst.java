package tw.com.askey.webservice.sdk.iot;

/**
 * Created by david5_huang on 2016/7/20.
 */
public interface MqttActionConst {
    /**
     * Mqtt subscribe接收到訊息時<br/>
     * Broadcast 使用的 action name
     */
    public static final String MQTT_RECEIVER_MESSAGE_ACTION = "mqtt_receiver_message";
    /**
     * Mqtt subscribe接收到訊息時<br/>
     * Broadcast 接收到的 data, 所使用的 intent tag
     */
    public static final String MQTT_RECEIVER_MESSAGE_DATA_TAG = "mqtt_message_data_tag";
}
