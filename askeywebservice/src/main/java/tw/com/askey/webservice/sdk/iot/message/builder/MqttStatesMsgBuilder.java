package tw.com.askey.webservice.sdk.iot.message.builder;

import java.util.ArrayList;
import java.util.HashMap;

import tw.com.askey.webservice.sdk.iot.message.Data;
import tw.com.askey.webservice.sdk.iot.message.Desired;
import tw.com.askey.webservice.sdk.iot.message.MqttMsg;
import tw.com.askey.webservice.sdk.iot.message.Reported;
import tw.com.askey.webservice.sdk.iot.message.State;

/**
 * Created by david5_huang on 2016/8/18.
 */
public class MqttStatesMsgBuilder extends MqttMsgBuilder {

    private HashMap<String, String> desiredMap;
    private HashMap<String, String> reportedMap;

    public MqttStatesMsgBuilder(String deviceId) {
        super(deviceId);
        desiredMap = new HashMap<>();
        reportedMap = new HashMap<>();
    }

    @Override
    public String buildMqttMessage() {
        MqttMsg mqttMsg = genDefaultMqttMsg();
        Data desiredData = new Data();
        Data reportedData= new Data();

        insertData(desiredData, desiredMap);
        insertData(reportedData, reportedMap);

        mqttMsg.getState().getDesired().setData(desiredData);
        mqttMsg.getState().getReported().setData(reportedData);

        return convertToJsonString(mqttMsg);
    }

    protected MqttMsg genDefaultMqttMsg(){
        MqttMsg mqttMsg = new MqttMsg();
        State state = new State();
        Desired desired = new Desired();
        Reported reported = new Reported();
        state.setDesired(desired);
        state.setReported(reported);
        mqttMsg.setState(state);
        mqttMsg.setAdditionalProperty(TAG_CLIENT_TOKEN, getDeviceId());
        return mqttMsg;
    }

    protected void insertData(Data data, HashMap<String, String> dataMap){
        ArrayList<String> keys = new ArrayList<>(dataMap.keySet());
        for(int i=0;i<keys.size();i++){
            data.setAdditionalProperty(keys.get(i), dataMap.get(keys.get(i)));
        }
    }

    public void addDesired(String key, String value){
        desiredMap.put(key, value);
    }

    public void addReported(String key, String value){
        reportedMap.put(key, value);
    }
}
