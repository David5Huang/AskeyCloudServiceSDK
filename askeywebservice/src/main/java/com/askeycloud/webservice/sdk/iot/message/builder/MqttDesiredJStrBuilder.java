package com.askeycloud.webservice.sdk.iot.message.builder;

import com.askeycloud.webservice.sdk.iot.message.Desired;
import com.askeycloud.webservice.sdk.iot.message.MqttMsg;
import com.askeycloud.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/8/17.
 */
public class MqttDesiredJStrBuilder extends MqttMsgBuilder {

    private String jsonString;

    public MqttDesiredJStrBuilder(String deviceId) {
        super(deviceId);
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Desired desired = new Desired();

        if(jsonString != null){
            desired.setAdditionalProperty(TAG_DATA, getJsonNodeByJsonStr(jsonString));
        }

        state.setDesired(desired);
        mqttMsg.setState(state);
        mqttMsg.setAdditionalProperty(TAG_CLIENT_TOKEN, getDeviceId());

        return convertToJsonString(mqttMsg);
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
