package com.askeycloud.webservice.sdk.iot.message.builder;

import com.askeycloud.webservice.sdk.iot.message.MqttMsg;
import com.askeycloud.webservice.sdk.iot.message.Reported;
import com.askeycloud.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/8/17.
 */
public class MqttReportedJStrBuilder extends MqttMsgBuilder {

    private String jsonString;

    public MqttReportedJStrBuilder(String deviceId) {
        super(deviceId);
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Reported reported = new Reported();
        reported.setDeviceid(getDeviceId());
        reported.setTstamp(Long.toString(System.currentTimeMillis()));

        if(jsonString != null){
            reported.setAdditionalProperty(TAG_DATA, getJsonNodeByJsonStr(jsonString));
        }

        state.setReported(reported);
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
