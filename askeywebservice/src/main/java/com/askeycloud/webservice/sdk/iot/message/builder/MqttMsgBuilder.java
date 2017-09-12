package com.askeycloud.webservice.sdk.iot.message.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by david5_huang on 2016/8/4.
 */
abstract public class MqttMsgBuilder {

    public static final String TAG_DATA = "data";
    public static final String TAG_CLIENT_TOKEN = "clientToken";

    private String deviceId;

    public MqttMsgBuilder(String deviceId){
        this.deviceId = deviceId;
    }

    protected String convertToJsonString(Object object){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected JsonNode getJsonNodeByJsonStr(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readValue(jsonString, JsonNode.class);
//            reported.setAdditionalProperty(TAG_REPORT_DATA, node);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    abstract public String buildMqttMessage();
}
