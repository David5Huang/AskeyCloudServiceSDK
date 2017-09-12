package com.askeycloud.webservice.sdk.iot.message.builder;

import java.util.ArrayList;
import java.util.HashMap;

import com.askeycloud.webservice.sdk.iot.message.Data;
import com.askeycloud.webservice.sdk.iot.message.MqttMsg;
import com.askeycloud.webservice.sdk.iot.message.Reported;
import com.askeycloud.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/8/9.<br/>
 * This builder use for building IoT desired message.<br/>
 * "reported" tag is using to record IoT thing states and detected data, e.g., update data when temperature changed.<br/>
 * <br/>
 * When send desired message, is also adding "client token" data, <br/>
 * this tag is use to recognizing device when SDK received IoT message.<br/>
 */
public class MqttReportedBuilder extends MqttMsgBuilder {

    private HashMap<String, Object> reportedMap;

    public MqttReportedBuilder(String deviceId) {
        super(deviceId);
        reportedMap = new HashMap<>();
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Reported reported = new Reported();
        reported.setDeviceid(getDeviceId());
        reported.setTstamp(Long.toString(System.currentTimeMillis()));

        Data data = new Data();

        ArrayList<String> tags = new ArrayList<>(reportedMap.keySet());
        for(int i=0;i<tags.size();i++){
            data.setAdditionalProperty(tags.get(i), reportedMap.get(tags.get(i)));
        }

        reported.setData(data);
        state.setReported(reported);
        mqttMsg.setState(state);
        mqttMsg.setAdditionalProperty(TAG_CLIENT_TOKEN, getDeviceId());

        return convertToJsonString(mqttMsg);
    }

    /**
     * Add reported data.
     * @param key
     * @param value
     */
    public void addReportData(String key, Object value){
        reportedMap.put(key, value);
    }

}
