package com.askey.qbee.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;

import askey.com.tw.cognitomqtt.DeviceTestHandler;
import tw.com.askey.ia4test.BasicActivity;
import tw.com.askey.ia4test.TestConst;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;
import tw.com.askey.webservice.sdk.iot.callback.MqttConnectionCallback;
import tw.com.askey.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.service.AskeyIoTService;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/23.
 */
public class CognitoMqttTestActivity extends BasicActivity implements
        Animation.AnimationListener,
        View.OnClickListener,
        ServiceCallback,
        TestConst,
        MqttServiceConnectedCallback{

    private TextView startTxt;

    private View step00Block;
    private View step01Block;
    private View step02Block;

    DeviceTestHandler testHandler;

    android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nextStep(step02Block, 800);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(askey.com.tw.cognitomqtt.R.layout.cognito_mqtt_main);

        testHandler = new DeviceTestHandler(getApplicationContext());

        startTxt = (TextView) findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_start_txt);
        step00Block = findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step00_block);
        step01Block = findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step01_block);
        step02Block = findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step02_block);

        findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step00_btn).setOnClickListener(this);
        findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step01_btn).setOnClickListener(this);
        findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step02_btn).setOnClickListener(this);
        findViewById(askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step03_btn).setOnClickListener(this);

        Animation anim = getTransAlphaAnim(1000);
        anim.setAnimationListener(this);
        startTxt.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        nextStep(step00Block, 800);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}

    @Override
    public void onClick(View view) {
        if(view.getId() == askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step00_btn){
            testHandler.getDeviceInfoTest(ServicePreference.getCognitoDataFromPreference(this).getUserID(),
                    TEST_MODULE,
                    TEST_UNIQUEID,
                    this);
        }
        else if(view.getId() == askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step01_btn){
            testHandler.connectToMQTT(this);
        }
        else if(view.getId() == askey.com.tw.cognitomqtt.R.id.cognito_mqtt_test_step02_btn){
            Intent it = new Intent(this, ThingTestActivity.class);
            it.putExtras(testHandler.getDeviceInfoBundle());
            startActivity(it);
        }
    }

    @Override
    public void success(String tag, Object result) {
        if(result != null){
            testHandler.setDeviceInfoResponse((DeviceInfoResponse) result);
            nextStep(step01Block, 800);
        }
    }

    @Override
    public void error(String tag, Object result) {

    }

    @Override
    public void problem(String tag, Object result) {

    }

    @Override
    public void onMqttServiceConnectedSuccess() {
        Log.e("ia4test", "bind success");
        AskeyIoTService.getInstance(getApplicationContext()).connectToAWSIot(new MqttConnectionCallback() {
            @Override
            public void onConnected() {
                Log.e("ia4test", "onMqttServiceConnectedSuccess onconnected");
                Message m = new Message();
                handler.sendMessage(m);
            }

            @Override
            public void unConnected(AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus status) {

            }
        });
    }

    @Override
    public void onMqttServiceConnectedError() {}

    private void nextStep(View view, long duration){
        view.setVisibility(View.VISIBLE);
        Animation anim = getTransAlphaAnim(duration);
        view.startAnimation(anim);
    }
}
