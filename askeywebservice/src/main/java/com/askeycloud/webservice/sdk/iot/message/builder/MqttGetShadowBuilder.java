package com.askeycloud.webservice.sdk.iot.message.builder;

import com.askeycloud.webservice.sdk.iot.message.Data;
import com.askeycloud.webservice.sdk.iot.message.Desired;
import com.askeycloud.webservice.sdk.iot.message.MqttMsg;
import com.askeycloud.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/11/16.
 */
public class MqttGetShadowBuilder extends MqttMsgBuilder {

    public MqttGetShadowBuilder(String deviceId) {
        super(deviceId);
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Desired desired = new Desired();
        Data data = new Data();
        desired.setData(data);
        state.setDesired(desired);
        mqttMsg.setState(state);
        mqttMsg.setAdditionalProperty(TAG_CLIENT_TOKEN, getDeviceId());

        return convertToJsonString(mqttMsg);
    }
}
