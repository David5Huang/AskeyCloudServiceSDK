package com.askeycloud.iottest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.askeycloud.sdk.core.api.response.BasicResponse;
import com.askeycloud.sdk.device.builder.ActiveDeviceRequestBuilder;
import com.askeycloud.sdk.device.response.AWSIoTCertResponse;
import com.askeycloud.sdk.device.response.IoTDeviceInfoResponse;
import com.askeycloud.sdk.device.response.UserIoTDeviceListResponse;
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.api.ApiStatus;
import com.askeycloud.webservice.sdk.iot.AskeyIoTUtils;
import com.askeycloud.webservice.sdk.iot.MqttActionConst;
import com.askeycloud.webservice.sdk.iot.callback.MqttConnectionCallback;
import com.askeycloud.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import com.askeycloud.webservice.sdk.iot.callback.ShadowReceiveListener;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttDesiredBuilder;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.service.device.AskeyIoTDeviceService;
import com.askeycloud.webservice.sdk.service.iot.AskeyIoTService;

/**
 * Created by david5_huang on 2017/6/22.
 */

public class DeviceApiTestActivity extends Activity implements
        View.OnClickListener,
        DemoConst,
        MqttServiceConnectedCallback,
        ShadowReceiveListener{

    private IoTDeviceInfoResponse deviceInfoResponse;
    private String cert;
    private String pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_test_layout);

        findViewById(R.id.iottest_cert_btn).setOnClickListener(this);
        findViewById(R.id.iottest_list_device_btn).setOnClickListener(this);
        findViewById(R.id.iottest_get_devive_btn).setOnClickListener(this);
        findViewById(R.id.iottest_connect_to_mqtt_btn).setOnClickListener(this);
        findViewById(R.id.iottest_publish_shadow_btn).setOnClickListener(this);
        findViewById(R.id.iottest_get_rest_shadow_btn).setOnClickListener(this);
        findViewById(R.id.iottest_mqtt_disconnect_btn).setOnClickListener(this);

        findViewById(R.id.iottest_remove_device_btn).setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iottest_cert_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AWSIoTCertResponse response = AskeyIoTDeviceService.getInstance(DeviceApiTestActivity.this).getIotCert();
                    if(response.getCode() == ApiStatus.API_SUCCESS){
                        Log.d(DebugConst.DEBUG_TAG, response.getCertificatePem());
                        Log.d(DebugConst.DEBUG_TAG, response.getPrivateKey());

                        cert = response.getCertificatePem();
                        pk = response.getPrivateKey();
                    }
                    else{
                        Log.e(DebugConst.DEBUG_TAG, response.getCode()+":\n"+response.getMessage());
                    }
                }
            }).start();
        }
        else if(v.getId() == R.id.iottest_list_device_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserIoTDeviceListResponse response =
                            AskeyIoTDeviceService.getInstance(DeviceApiTestActivity.this).userIoTDeviceList();

                    Log.d(DebugConst.DEBUG_TAG, response.getCode()+":\n"+response.getDevices().size());
                    StringBuilder builder = new StringBuilder();
                    for(int i=0;i<response.getDevices().size();i++){
                        Log.d(DebugConst.DEBUG_TAG, response.getDevices().get(i).getDeviceid());
                        builder.append(response.getDevices().get(i).getDeviceid()+"\n");
                    }
                    showToastInfo(builder.toString());
                }
            }).start();
        }
        else if(v.getId() == R.id.iottest_get_devive_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    deviceInfoResponse = AskeyIoTDeviceService.getInstance(DeviceApiTestActivity.this).lookupIoTDevice(YOUR_DEVICE_MODEL, YOUR_DEVICE_UNIQUEID);
                    if(deviceInfoResponse.getCode() == ApiStatus.API_SUCCESS){
                        Log.d(DebugConst.DEBUG_TAG, "First get device success, device id: "+deviceInfoResponse.getDeviceid());
                        showToastInfo("Get Device Info Success: "+deviceInfoResponse.getDeviceid());
                    }
                    else{
                        Log.d(DebugConst.DEBUG_TAG, "Can't lookup device: "+deviceInfoResponse.getCode());
                        Log.d(DebugConst.DEBUG_TAG, "Can't lookup message: "+deviceInfoResponse.getMessage());
                        Log.d(DebugConst.DEBUG_TAG, "Can't lookup exception: "+deviceInfoResponse.getAddtionMessage());
//                        ActiveDeviceRequestBuilder builder = new ActiveDeviceRequestBuilder(
//                                YOUR_DEVICE_MODEL,
//                                YOUR_DEVICE_UNIQUEID,
//                                YOUR_DEVICE_DISPLAYNAME
//                        );
//
//                        deviceInfoResponse = AskeyIoTDeviceService.getInstance(DeviceApiTestActivity.this)
//                                .createIoTDevice(builder.getActiveDeviceRequest());
//
//                        if(deviceInfoResponse.getCode() == ApiStatus.API_SUCCESS){
//                            Log.d(DebugConst.DEBUG_TAG, "device id: "+deviceInfoResponse.getDeviceid());
//                            showToastInfo("Get Device Info Success");
//                        }
//                        else{
//                            Log.e(DebugConst.DEBUG_TAG, deviceInfoResponse.getCode()+": "+deviceInfoResponse.getMessage());
//                            Log.e(DebugConst.DEBUG_TAG, "token: "+ServicePreference.getAuthV3UserDataModel(DeviceApiTestActivity.this).getAccessToken());
//                            showToastInfo("Get Device Info Error: "+deviceInfoResponse.getCode());
//                        }
                    }
                }
            }).start();
        }
        else if(v.getId() == R.id.iottest_connect_to_mqtt_btn){
            AskeyIoTService.getInstance(getApplicationContext())
                    .configAWSIot(
                            AskeyIoTUtils.translatMqttUseEndpoint(deviceInfoResponse.getRestEndpoint()),
                            this
                    );
        }
        else if(v.getId() == R.id.iottest_publish_shadow_btn){
            MqttDesiredBuilder builder = new MqttDesiredBuilder(deviceInfoResponse.getDeviceid());
            builder.addCommand("key1", "value1");
            builder.addCommand("key2", 90);
            builder.addCommand("key3", new Boolean(true));

            AskeyIoTService.getInstance(this).publishDesiredMessage(deviceInfoResponse.getShadowTopic(), builder);
        }
        else if(v.getId() == R.id.iottest_get_rest_shadow_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String shadowState = AskeyIoTService.getInstance(DeviceApiTestActivity.this)
                            .readApiThingShadow(deviceInfoResponse.getDeviceid());
                    Log.i(DebugConst.DEBUG_TAG, "Get rest shadow: "+shadowState);
                    showToastInfo(shadowState);
                }
            }).start();
        }
        else if(v.getId() == R.id.iottest_mqtt_disconnect_btn){
            AskeyIoTService.getInstance(this).disconnectIoTMQTTManager();
        }
        else if(v.getId() == R.id.iottest_remove_device_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BasicResponse response =
                            AskeyIoTDeviceService.getInstance(DeviceApiTestActivity.this)
                                    .removeIoTDevice(deviceInfoResponse);

                    Log.e(DebugConst.DEBUG_TAG, "status: "+response.getCode()+", msg: "+response.getMessage()+" ,addmsg: "+response.getAddtionMessage());
                }
            }).start();
        }
    }

    private void showToastInfo(final String info){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DeviceApiTestActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMqttServiceConnectedSuccess() {
        AskeyIoTService.getInstance(getApplicationContext()).connectToAWSIot(
                YOUR_MQTT_KEYSTORE_FILE_NAME,
                cert,
                pk,
                new MqttConnectionCallback() {
                    @Override
                    public void onConnected() {

                        if(deviceInfoResponse != null){
                            AskeyIoTService.getInstance(getApplicationContext())
                                    .subscribeGetShadowMqtt(deviceInfoResponse.getShadowTopic());
                            AskeyIoTService.getInstance(getApplicationContext())
                                    .subscribeMqttDelta(deviceInfoResponse.getShadowTopic());
                            AskeyIoTService.getInstance(getApplicationContext())
                                    .setShadowReceiverListener(DeviceApiTestActivity.this);
                        }

                        showToastInfo("Connect MQTT Service success.");
                    }

                    @Override
                    public void unConnected(AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus status) {
                    }
                });
    }

    @Override
    public void onMqttServiceConnectedError() {}

    @Override
    public void receiveShadowDocument(String tag, String data) {
        if(tag.equals(MqttActionConst.MQTT_RECEIVER_MESSAGE_DATA_TAG)){
            Log.i(DebugConst.DEBUG_TAG, tag+" : "+data);
            showToastInfo(data);
        }
        else if(tag.equals(MqttActionConst.MQTT_GET_SHADOW_DATA_TAG)){
            Log.i(DebugConst.DEBUG_TAG, tag+" : "+data);
            showToastInfo(data);
        }
    }

}
