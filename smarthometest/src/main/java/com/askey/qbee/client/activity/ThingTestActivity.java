package com.askey.qbee.client.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import java.util.HashMap;

import askey.com.tw.iotthingtest.MqttChangedReceiver;
import askey.com.tw.iotthingtest.ThingTestHelper;
import tw.com.askey.ia4test.BasicActivity;
import tw.com.askey.webservice.sdk.iot.AskeyIoTUtils;
import tw.com.askey.webservice.sdk.iot.MqttActionConst;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttDesiredBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttReportedBuilder;

/**
 * Created by david5_huang on 2016/8/31.
 */
public class ThingTestActivity extends BasicActivity implements View.OnClickListener {

    View scribeBtn;
    View desiredBtn;
    View reportedBtn;
    View readBtn;

    ThingTestHelper testHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(askey.com.tw.iotthingtest.R.layout.thing_test_main);

        scribeBtn = findViewById(askey.com.tw.iotthingtest.R.id.thing_test_test_subcribe_btn);
        scribeBtn.setOnClickListener(this);
        desiredBtn = findViewById(askey.com.tw.iotthingtest.R.id.thing_test_test_desired_btn);
        desiredBtn.setOnClickListener(this);
        reportedBtn = findViewById(askey.com.tw.iotthingtest.R.id.thing_test_test_reported_btn);
        reportedBtn.setOnClickListener(this);
        readBtn = findViewById(askey.com.tw.iotthingtest.R.id.thing_test_test_read_shadow_btn);
        readBtn.setOnClickListener(this);

        scribeBtn.startAnimation(getR2LTransAnim(800, 0));
        desiredBtn.startAnimation(getR2LTransAnim(800, 100));
        reportedBtn.startAnimation(getR2LTransAnim(800, 150));
        readBtn.startAnimation(getR2LTransAnim(800, 200));

        testHelper = new ThingTestHelper(this);
        testHelper.setThingParams(getIntent().getExtras());

        MqttChangedReceiver receiver = new MqttChangedReceiver();
        IntentFilter filter = new IntentFilter(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == askey.com.tw.iotthingtest.R.id.thing_test_test_subcribe_btn){
            testHelper.subcribeThing(testHelper.getTopic());
        }
        else if(view.getId() == askey.com.tw.iotthingtest.R.id.thing_test_test_desired_btn){
//            MqttDesiredBuilder builder = new MqttDesiredBuilder(testHelper.getDeviceId());
//            builder.addCommand("temp", "55");
//            builder.addCommand("humidity", "60");

            testHelper.publishDesired(testHelper.getTopic(), testHelper.getTestDesiredMsgBuilder());
        }
        else if(view.getId() == askey.com.tw.iotthingtest.R.id.thing_test_test_reported_btn){
//            MqttReportedBuilder builder = new MqttReportedBuilder(testHelper.getDeviceId());
//            builder.addReportData("temp", "30");
//            builder.addReportData("humidity", "70");
//            HashMap<String, String> actMap = new HashMap<>();
//            actMap.put("act01", "test");
//            actMap.put("act02", "test02");
//            builder.addReportData("act", actMap);

            testHelper.publishReported(testHelper.getTopic(), testHelper.getTestReportedMsgBuilder());
        }
        else if(view.getId() == askey.com.tw.iotthingtest.R.id.thing_test_test_read_shadow_btn){
            testHelper.readShodow(
                    AskeyIoTUtils.translatMqttUseEndpoint(testHelper.getEndpoint()),
                    testHelper.getThingName(),
                    testHelper);
        }
    }
}
