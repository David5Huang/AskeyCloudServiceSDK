package com.askeycloud.sdksample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.askeycloud.sdk.device.builder.ActiveDeviceRequestBuilder;
import com.askeycloud.sdk.device.builder.UpdateIoTDeviceDetailBuilder;
import com.askeycloud.sdk.device.response.AWSIoTCertResponse;
import com.askeycloud.sdk.device.response.IoTDeviceDetailInfoResponse;
import com.askeycloud.sdk.device.response.IoTDeviceDisplayInfoResponse;
import com.askeycloud.sdk.device.response.IoTDeviceEndpointResponse;
import com.askeycloud.sdk.device.response.IoTDeviceInfoResponse;
import com.askeycloud.sdk.device.response.UserIoTDeviceListResponse;
import com.askeycloud.sdksample.demo.MqttChangedReceiver;
import com.askeycloud.sdksample.demo.TestConst;
import com.askeycloud.webservice.sdk.iot.callback.ShadowReceiveListener;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.model.auth.BasicUserDataModel;
import java.util.HashMap;

import com.askeycloud.webservice.sdk.iot.AskeyIoTUtils;
import com.askeycloud.webservice.sdk.iot.MqttActionConst;
import com.askeycloud.webservice.sdk.iot.callback.MqttConnectionCallback;
import com.askeycloud.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import com.askeycloud.webservice.sdk.iot.callback.ReadThingShadowCallback;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttDesiredBuilder;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttReportedJStrBuilder;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttStatesMsgBuilder;
import com.askeycloud.webservice.sdk.model.auth.CognitoDataModel;
import com.askeycloud.webservice.sdk.service.iot.AskeyIoTService;
import com.askeycloud.webservice.sdk.service.web.AskeyWebService;
import com.askeycloud.webservice.sdk.service.device.AskeyIoTDeviceService;
import com.askeycloud.webservice.sdk.task.ServiceCallback;

/**
 * sample的執行順序大致上是依照button由上到下的順序
 * 1. 先登入facebook後取得AWS相關的token
 * 2. 取得device資訊
 * 3. config mqtt manager(連結mqtt service)
 * 4. 連接mqtt manager
 * 5. publish message
 */
/**
 * 2016/11/17
 * 使用email註冊/登入的sample流程如下:
 * 1. 註冊/登入新的User(參數在app module的TestConst.java)
 * 2. 取得certificate(cert test的button)
 * 3. 新增/詢問指定的IoT thing(參數在app module的TestConst.java)
 * 4. 連接mqtt manager
 * 5. publish message
 */
public class MasterTestActivity extends AppCompatActivity implements View.OnClickListener, ServiceCallback, MqttServiceConnectedCallback, ReadThingShadowCallback, TestConst, ShadowReceiveListener {

    final String EMAIL_SERVICE_TAG = "email_login";

    IoTDeviceInfoResponse ioTDeviceInfoResponse;

    String cert;
    String pk;

    String userId;

    String fbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.ia4_test_fb_login_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_mqtt_connect_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_mqtt_desire_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_mqtt_report_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_get_shadow_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_device_info_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_change_name_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_device_list_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_device_basic_info_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_device_detail_info_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_mqtt_states_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_cert_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_user_info_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_register_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_register2_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_login_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_login2_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_iot_http_publish_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_iot_http_get_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_get_endpoint_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_device_detail_update_btn).setOnClickListener(this);

        MqttChangedReceiver receiver = new MqttChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION);
        filter.addAction(MqttActionConst.MQTT_GET_SHADOW_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        Log.e("ia4test", "onclick");
        if(view.getId() == R.id.ia4_test_device_list_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    UserDeviceListResponse response = AskeyCloudDeviceApiUtils.getInstance(ServiceConst.API_URL).
//                            userDeviceList(ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID());
//                    if(response != null){
//                        List<String> devices = response.getDevices();
//                        for(int i=0;i<devices.size();i++){
//                            Log.e("ia4test", devices.get(i));
//                        }
//                    }

                    UserIoTDeviceListResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this).userIoTDeviceList();
                    Log.e("ia4test", "list size: "+response.getDevices().size());
                    Log.e("ia4test", "first id: "+response.getDevices().get(0).getDeviceid());

                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_mqtt_connect_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    CognitoDataModel model = ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this);
//                    BasicSessionCredentials credentials = new BasicSessionCredentials(model.getAccessKey(), model.getSecretKey(), model.getSessionToken());
//                    AWSIotClient iotClient = new AWSIotClient(credentials);
//                    iotClient.setRegion(Region.getRegion(Regions.US_EAST_1));
//
//                    AttachPrincipalPolicyRequest activeDeviceRequest = new AttachPrincipalPolicyRequest();
//                    activeDeviceRequest.setPolicyName("cognito_test_policy");
//                    activeDeviceRequest.setPrincipal(model.getIdentityID());
//                    iotClient.attachPrincipalPolicy(activeDeviceRequest);

                    if(ioTDeviceInfoResponse != null){
                        AskeyIoTService.getInstance(getApplicationContext())
                                .configAWSIot(
                                        AskeyIoTUtils.translatMqttUseEndpoint(ioTDeviceInfoResponse.getRestEndpoint()),
                                        MasterTestActivity.this);
                    }
                }
            }).start();

        }
        else if(view.getId() == R.id.ia4_test_mqtt_desire_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext())
                            .publishDesiredMessage(
                                    ioTDeviceInfoResponse.getShadowTopic(),
                                    buildTestDesiredMqttMsg()
                            );

//                    String result = AskeyIoTService.getInstance(getApplicationContext()).publishApiThingShadow(
//                            ioTDeviceInfoResponse.getDeviceid(),
//                            buildTestDesiredMqttMsg());
//
//                    Log.e("ia4test", result);

                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_mqtt_report_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext())
                            .publishReportedMessage(
                                    ioTDeviceInfoResponse.getShadowTopic(),
                                    buildTestReportedMqttMsg()
                            );
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_iot_http_publish_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = AskeyIoTService.getInstance(getApplicationContext()).publishApiThingShadow(
                            ioTDeviceInfoResponse.getDeviceid(),
                            buildTestDesiredMqttMsg());

                    Log.e("ia4test", result);
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_iot_http_get_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = AskeyIoTService.getInstance(getApplicationContext()).readApiThingShadow(ioTDeviceInfoResponse.getDeviceid());
                    Log.e("ia4test", result);
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_get_shadow_btn){

//            AskeyIoTService.getInstance(getApplicationContext()).readApiThingShadow(ioTDeviceInfoResponse.getShadowTopic());

            AskeyIoTService.getInstance(getApplicationContext())
                    .readThingShadow(
                            ioTDeviceInfoResponse.getShadowTopic()
                    );

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String result = AskeyIoTService.getInstance(MasterTestActivity.this).readApiThingShadow(
//                            ioTDeviceInfoResponse.getDeviceid()
//                    );
//
//                    Log.e("ia4test", "read shadow: "+result);
//                }
//            }).start();

        }
        else if(view.getId() == R.id.ia4_test_device_info_btn){
            getDeviceInfoTest(
                    YOUR_DEVICE_MODEL,
                    YOUR_DEVICE_UNIQUEID
            );
        }
        else if(view.getId() == R.id.ia4_test_change_name_btn){
            updateDeviceNameTest(ioTDeviceInfoResponse.getDeviceid(), "New Camera Name");
        }
        else if(view.getId() == R.id.ia4_test_device_basic_info_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IoTDeviceDisplayInfoResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this)
                            .getIoTDeviceDisplayInfo(ioTDeviceInfoResponse.getDeviceid());
                    if(response != null){
                        Log.e("ia4test", "display name: "+response.getDisplayname());
                    }
                    else{
                        Log.e("ia4test", "basic null");
                    }
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_device_detail_info_btn){
            if(ioTDeviceInfoResponse != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IoTDeviceDetailInfoResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this)
                                .getIoTDeviceDetailInfo(ioTDeviceInfoResponse.getDeviceid());
                        if(response.getDetail() == null){
                            Log.e("ia4test", "detail null");
                        }
                        else{
                            Log.e("ia4test", "detail: "+response.getDetail().getAdditionalProperties().get("macaddress"));
                        }

                    }
                }).start();
            }
        }
        else if(view.getId() == R.id.ia4_test_device_detail_update_btn){
            if(ioTDeviceInfoResponse != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IoTDeviceDetailInfoResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this)
                                .getIoTDeviceDetailInfo(ioTDeviceInfoResponse.getDeviceid());
                        if(response.getDetail() == null){
                            Log.e("ia4test", "detail null");
                        }
                        else{
                            UpdateIoTDeviceDetailBuilder builder = new UpdateIoTDeviceDetailBuilder(ioTDeviceInfoResponse.getDeviceid(), (HashMap<String, Object>) response.getDetail().getAdditionalProperties());
//                            builder.addUpdateDetail("roomId", UUID.randomUUID().toString());
                            builder.removeDetail("roomId");
                            response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this)
                                    .updateIoTDeviceDetailInfo(builder);

                            Log.e("ia4test", "detail room: "+response.getDetail().getAdditionalProperties().get("roomId"));
                        }
                    }
                }).start();
            }
        }
        else if(view.getId() == R.id.ia4_test_mqtt_states_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext()).publishStatesMqttMessage(
                            ioTDeviceInfoResponse.getShadowTopic(),
                            buildTestStatesMqttMsg());
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_cert_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    GetCertResponse response = AskeyCloudDeviceApiUtils.getInstance(ServiceConst.API_URL).getCert(
//                            ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID()
//                    );
//                    Log.e("ia4test", response.getCertificatePem());

                    AWSIoTCertResponse response = AskeyIoTDeviceService.
                            getInstance(MasterTestActivity.this).getIotCert();

                    String cert = response.getCertificatePem();
                    String pk = response.getPrivateKey();

                    Log.e("ia4test", response.getCertificatePem());
                    Log.e("ia4test", response.getPrivateKey());

                    MasterTestActivity.this.cert = response.getCertificatePem();
                    MasterTestActivity.this.pk = response.getPrivateKey();
                    fbName = TestConst.TEST_NAME;
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_user_info_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    if(loginSource != null){
//                        GetKeypairRequestBuilder builder = new FBKeypairRequestBuilder(
//                                ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID(),
//                                loginSource
//                        );
//                        GetKeypairResponse response = AskeyCloudDeviceApiUtils.getInstance(ServiceConst.API_URL).getKeypair(builder.getKeypairRequest());
//                        Log.e("ia4test", response.getPrivateKey());
//                        pk = response.getPrivateKey();

//                    }

//                    UserInfoResponse response = AskeyWebService.getInstance(MasterTestActivity.this).getUserInfo();
//
//                    if(response.getDisplayname() != null){
//                        Log.e("ia4Test", response.getDisplayname());
//                    }


//
//                    BasicUserDataModel userDataModel = ServicePreference.getBasicUserDataFromPreference(MasterTestActivity.this);
//
//                    String response = AskeyCloudAuthApiUtils.getInstance(ServiceConst.API_OAUTH_URL)
//                            .getSwaggerTestUserInfo(
//                                    "user/{userid}/info",
//                                userDataModel.getToken(),
//                                userDataModel.getUserID()
//                    );
//
//                    Log.e("ia4Test", response);

                }
            }).start();

//            Intent it = new Intent(this, CognitoMqttTestActivity.class);
//            Intent it = new Intent(this, AuthTestActivity.class);
//            this.startActivity(it);

        }
        else if(view.getId() == R.id.ia4_test_email_register_btn){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    RegisterUserBuilder builder = new RegisterUserBuilder(
//                            TEST_EMAIL,
//                            TEST_PWD,
//                            TEST_NAME
//                    );
//                    nowUser = AskeyWebService.getInstance(MasterTestActivity.this).registerUser(builder);
//                    Log.e("ia4test", "Register result: "+nowUser.getUserid()+", "+nowUser.getToken());
//                }
//            }).start();
//            EMailLoginSource loginSource = new EMailLoginSource(
//                    TEST_EMAIL3,
//                    TEST_PWD3,
//                    TEST_NAME3
//            );
//            AskeyWebService.getInstance(this).active(loginSource, EMAIL_SERVICE_TAG, this);
        }
        else if(view.getId() == R.id.ia4_test_email_register2_btn){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    RegisterUserBuilder builder = new RegisterUserBuilder(
//                            TEST_EMAIL2,
//                            TEST_PWD2,
//                            TEST_NAME2
//                    );
//                    nowUser2 = AskeyWebService.getInstance(MasterTestActivity.this).registerUser(builder);
//                    Log.e("ia4test", "Register2 result: "+nowUser2.getUserid()+", "+nowUser2.getToken());
//                }
//            }).start();
        }
        else if(view.getId() == R.id.ia4_test_email_login_btn){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    LoginUserBuilder builder = new LoginUserBuilder(
//                            TEST_EMAIL,
//                            TEST_PWD
//                    );
//                    nowUser = AskeyWebService.getInstance(MasterTestActivity.this).loginUser(builder);
//                    Log.e("ia4test", "status code: "+nowUser.getCode());
//                    Log.e("ia4test", "Login result: "+nowUser.getUserid()+", "+nowUser.getToken());
//                }
//            }).start();
//            EMailLoginSource loginSource = new EMailLoginSource(
//                    TEST_EMAIL3,
//                    TEST_PWD3
//            );
//            AskeyWebService.getInstance(this).active(loginSource, EMAIL_SERVICE_TAG, this);
        }
        else if(view.getId() == R.id.ia4_test_email_login2_btn){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    LoginUserBuilder builder = new LoginUserBuilder(
//                            TEST_EMAIL2,
//                            TEST_PWD2
//                    );
//                    nowUser2 = AskeyWebService.getInstance(MasterTestActivity.this).loginUser(builder);
//                    Log.e("ia4test", "status code: "+nowUser2.getCode());
//                    Log.e("ia4test", "Login2 result: "+nowUser2.getUserid()+", "+nowUser2.getToken());
//                }
//            }).start();
        }
        else if(view.getId() == R.id.ia4_test_get_endpoint_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IoTDeviceEndpointResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this)
                            .getIoTDeviceEndpoint(ioTDeviceInfoResponse.getDeviceid());
                    Log.e("ia4test", "status: "+response.getCode());
                    Log.e("ia4test", "message: "+response.getMessage());
                    Log.e("ia4test", "endpoint: "+response.getRestEndpoint());
                    Log.e("ia4test", "topic: "+response.getShadowTopic());
                }
            }).start();
        }
    }

    private MqttMsgBuilder buildTestDesiredMqttMsg(){

        MqttDesiredBuilder builder = new MqttDesiredBuilder(ioTDeviceInfoResponse.getDeviceid());

        builder.addCommand("callStates", "call_request");
//        builder.addCommand("humidity", "60");

//        builder.addCommand("bol_test", true);
//        builder.addCommand("num_test", 3.1426d);

//        MqttDesiredJStrBuilder builder = new MqttDesiredJStrBuilder(deviceInfoResponse.getDevice().getDeviceid());
//        builder.setJsonString("{\"humidity\": \"70\",\"temp\": \"30\",\"act\": {\"act03\": \"test\"}}");

        return builder;
    }

    private MqttMsgBuilder buildTestReportedMqttMsg(){
//        MqttReportedBuilder builder = new MqttReportedBuilder(ioTDeviceInfoResponse.getDeviceid());
//        builder.addReportData("temp", "30");
//        builder.addReportData("humidity", "70");
//        HashMap<String, String> actMap = new HashMap<>();
//        actMap.put("act01", "test");
//        actMap.put("act02", "test02");
//        builder.addReportData("act", actMap);

        MqttReportedJStrBuilder builder = new MqttReportedJStrBuilder(ioTDeviceInfoResponse.getDeviceid());
        builder.setJsonString("{\"humidity\": \"70\",\"temp\": \"30\",\"act\": {\"act01\": \"test\",\"act02\": \"test02\"}}");

        return builder;
    }

    private MqttMsgBuilder buildTestStatesMqttMsg(){
        MqttStatesMsgBuilder builder = new MqttStatesMsgBuilder(ioTDeviceInfoResponse.getDeviceid());
        builder.addDesired("temp", "30");
        builder.addDesired("humidity", "70");
        builder.addReported("temp", "20");
        builder.addReported("humidity", "70");
        return builder;
    }

    @Override
    public void success(String tag, Object result) {
        if(tag.equals("ia4register")){
            CognitoDataModel model = (CognitoDataModel) result;
            Log.e("ia4test", model.getIdentityID());
            Log.e("ia4test", model.getAccessKey());
            Log.e("ia4test", model.getSecretKey());
            Log.e("ia4test", model.getSessionToken());
            Log.e("ia4test", model.getUserID());
            Toast.makeText(this, "active aws success", Toast.LENGTH_SHORT).show();
        }
        else if(tag.equals(EMAIL_SERVICE_TAG)){
            BasicUserDataModel model = (BasicUserDataModel) result;
//            Log.e("ia4test", model.getUserID());
//            Log.e("ia4test", model.getToken());
            String userId = model.getUserID();
            String token = model.getToken();
            Toast.makeText(this, "active aws success", Toast.LENGTH_SHORT).show();
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
//        AskeyIoTService.getInstance(getApplicationContext()).connectToAWSIot("AKIAI5FJKP6G53EJ63OA",
//                "cPJvQpBGMSSHLa99vMWYP7iQU++TwB2/k0Bq11O4", new MqttConnectionCallback() {
        userId = ServicePreference.getBasicUserDataFromPreference(this).getUserID();
        AskeyIoTService.getInstance(getApplicationContext()).connectToAWSIot(
                userId,
                cert,
                pk,
                new MqttConnectionCallback() {
            @Override
            public void onConnected() {

                if(ioTDeviceInfoResponse != null){
                    AskeyIoTService.getInstance(getApplicationContext())
                            .subscribeGetShadowMqtt(ioTDeviceInfoResponse.getShadowTopic());
                    AskeyIoTService.getInstance(getApplicationContext())
                            .subscribeMqttDelta(ioTDeviceInfoResponse.getShadowTopic());
                    AskeyIoTService.getInstance(getApplicationContext())
                            .setShadowReceiverListener(MasterTestActivity.this);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MasterTestActivity.this, "aws iot config end.", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void unConnected(AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus status) {

            }
        });
    }

    @Override
    public void onMqttServiceConnectedError() {
        Toast.makeText(this, "MqttService create error.", Toast.LENGTH_SHORT).show();
    }

    //目前上面的是測試資料, 使用時請填上正式資料
    private void getDeviceInfoTest(final String module, final String uniqueid){

        String deviceName = "Door Camera";
        final ActiveDeviceRequestBuilder builder = new ActiveDeviceRequestBuilder(
                module,
                uniqueid,
                deviceName
        );

        builder.putDeviceDetail("macaddress", "f45c89a8f937");

        new Thread(new Runnable() {
            @Override
            public void run() {

                ioTDeviceInfoResponse = AskeyIoTDeviceService.
                        getInstance(MasterTestActivity.this).lookupIoTDevice(TestConst.YOUR_DEVICE_MODEL, TestConst.YOUR_DEVICE_UNIQUEID);

                Log.e("ia4test", "code: "+ioTDeviceInfoResponse.getCode());

                if(ioTDeviceInfoResponse != null && ioTDeviceInfoResponse.getDeviceid() != null){
//                    Log.e("ia4test", "code: "+ioTDeviceInfoResponse.getCode()+", message: "+ioTDeviceInfoResponse.getMessage());
                    Log.e("ia4test", "look up success");
                    Log.e("ia4test", "device name: "+ ioTDeviceInfoResponse.getIotThingname());
                }
                else{
                    Log.e("ia4test", "create device");
                    ioTDeviceInfoResponse = AskeyIoTDeviceService.getInstance(MasterTestActivity.this).createIoTDevice(builder.getActiveDeviceRequest());
//
//                    Log.e("ia4Test", nowUser.getUserid()+", "+TestConst.YOUR_DEVICE_MODEL+", "+YOUR_DEVICE_UNIQUEID);
//                    Log.e("ia4test", "lookup device id: "+ ioTDeviceInfoResponse.getDeviceid());
                    if(ioTDeviceInfoResponse.getCode() != 200){
                        Log.e("ia4Test", "err msg"+ioTDeviceInfoResponse.getAdditionalProperties().toString());
                    }
                    Log.e("ia4test", "device name: "+ ioTDeviceInfoResponse.getIotThingname());
                }
            }
        }).start();


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                IoTDeviceInfoResponse ioTDeviceInfoResponse = AskeyIoTDeviceService
//                        .getInstance(MasterTestActivity.this)
//                        .createIoTDevice(builder.getActiveDeviceRequest());
//            }
//        }).start();

        final String MODULE = YOUR_DEVICE_MODEL;
        final String UNIQUEID = YOUR_DEVICE_UNIQUEID;
        new Thread(new Runnable() {
            @Override
            public void run() {
                IoTDeviceInfoResponse ioTDeviceInfoResponse = AskeyIoTDeviceService
                        .getInstance(MasterTestActivity.this)
                        .lookupIoTDevice(
                                MODULE,
                                UNIQUEID);
            }
        }).start();

    }

    private void updateDeviceNameTest(final String deviceid, final String name){
        if(deviceid == null){
            return;
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                UpdateIoTDeviceInfoRequestBuilder builder = new UpdateIoTDeviceInfoRequestBuilder();
//                builder.setUpdateDisplayName(name+"_"+System.currentTimeMillis());
//
//                IoTDeviceDisplayInfoResponse response = AskeyIoTDeviceService.getInstance(MasterTestActivity.this).updateIoTDeviceDisplayInfo(
//                        builder.getUpdateIoTDeviceInfoRequest(),
//                        ioTDeviceInfoResponse.getDeviceid());
//
//                Log.e("ia4test", "update: "+response.getDisplayname());
//            }
//        }).start();
    }

    @Override
    public void getThingShadow(String shadowResult) {
        Log.e("ia4test", "shadow: "+shadowResult);
    }

    @Override
    public void receiveShadowDocument(String tag, String data) {
        Log.e("ia4test", tag+", \n"+data);
    }
}
