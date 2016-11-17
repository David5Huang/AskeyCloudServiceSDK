package askey.com.tw.iotthingtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import tw.com.askey.ia4test.MqttResultActivity;
import tw.com.askey.ia4test.TestConst;
import tw.com.askey.webservice.sdk.iot.MqttService;
import tw.com.askey.webservice.sdk.iot.callback.ReadThingShadowCallback;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttDesiredBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttReportedBuilder;
import tw.com.askey.webservice.sdk.service.AskeyIoTService;

/**
 * Created by david5_huang on 2016/8/31.
 */
public class ThingTestHelper implements TestConst, ReadThingShadowCallback {
    private final Context context;
    MqttService service;

    String topic;
    String endpoint;
    String thingName;
    String deviceId;

    public ThingTestHelper(Context context){
        service = MqttService.getInstance();
        this.context = context;
    }

    public void setThingParams(Bundle bundle){
        if(bundle != null){
            topic = bundle.getString(TOPIC_KEY);
            endpoint = bundle.getString(ENDPOINT_KEY);
            thingName = bundle.getString(THING_NAME_KEY);
            deviceId = bundle.getString(DEVICE_ID_KEY);
        }
    }

    public void subcribeThing(final String topic){
        if(service == null){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AskeyIoTService.getInstance(context).subscribeMqttDelta(topic);
            }
        }).start();
        Toast.makeText(context, "subscribe success.", Toast.LENGTH_SHORT).show();
    }

    public void publishDesired(final String topic, final MqttMsgBuilder builder){
        if(service == null){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AskeyIoTService.getInstance(context).publishDesiredMessage(topic, builder);
            }
        }).start();

    }

    public void publishReported(final String topic, final MqttMsgBuilder builder){
        if(service == null){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                AskeyIoTService.getInstance(context).publishReportedMessage(topic, builder);
            }
        }).start();

    }

    public void readShodow(final String endpoint, final String thingName, final ReadThingShadowCallback callback){
        if(service == null){
            return;
        }
//        AskeyIoTService.getInstance(context).readThingShadow(endpoint, thingName, callback);
//        AskeyIoTService.getInstance(context).readThingShadow();
    }

    public MqttMsgBuilder getTestDesiredMsgBuilder(){
        MqttDesiredBuilder builder = new MqttDesiredBuilder(getDeviceId());
        builder.addCommand("temp", "55");
        builder.addCommand("humidity", "60");
        return builder;
    }

    public MqttMsgBuilder getTestReportedMsgBuilder(){
        MqttReportedBuilder builder = new MqttReportedBuilder(getDeviceId());
        builder.addReportData("temp", "30");
        builder.addReportData("humidity", "70");
        HashMap<String, String> actMap = new HashMap<>();
        actMap.put("act01", "test");
        actMap.put("act02", "test02");
        builder.addReportData("act", actMap);
        return builder;
    }

    @Override
    public void getThingShadow(String shadowResult) {
        Intent it = new Intent(context, MqttResultActivity.class);
        it.putExtra("mqtt", shadowResult);
        context.startActivity(it);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
