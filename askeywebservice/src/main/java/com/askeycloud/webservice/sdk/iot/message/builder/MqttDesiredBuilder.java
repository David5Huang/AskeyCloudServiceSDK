package com.askeycloud.webservice.sdk.iot.message.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import com.askeycloud.webservice.sdk.iot.message.Data;
import com.askeycloud.webservice.sdk.iot.message.Desired;
import com.askeycloud.webservice.sdk.iot.message.MqttMsg;
import com.askeycloud.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/8/9.<br/>
 * This builder use for building IoT desired message.<br/>
 * "desired" tag is use to commanding IoT thing, e.g., control switch.<br/>
 * <br/>
 * When send desired message, is also adding "client token" data, <br/>
 * this tag is use to recognizing device when SDK received IoT message.<br/>
 */
public class MqttDesiredBuilder extends MqttMsgBuilder {

    private HashMap<String, Object> desiredMap;

    public MqttDesiredBuilder(String deviceId) {
        super(deviceId);
        desiredMap = new HashMap<>();
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Desired desired = new Desired();
        Data data = new Data();
        ArrayList<String> tags = new ArrayList<>(desiredMap.keySet());
        for(int i=0;i<tags.size();i++){
            data.setAdditionalProperty(tags.get(i), desiredMap.get(tags.get(i)));
        }
        desired.setData(data);
        state.setDesired(desired);
        mqttMsg.setState(state);
        mqttMsg.setAdditionalProperty(TAG_CLIENT_TOKEN, getDeviceId());

        return convertToJsonString(mqttMsg);
    }

    /**
     * Add "data" tag content to desired message.
     * @param key
     * @param value
     */
    public void addCommand(String key, Object value){
        desiredMap.put(key, value);
    }

}
