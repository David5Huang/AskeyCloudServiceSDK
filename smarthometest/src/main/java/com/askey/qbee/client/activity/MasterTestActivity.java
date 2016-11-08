package com.askey.qbee.client.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.askey.qbee.client.R;
import com.askey.qbee.client.activity.extra.CameraLaunchHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import tw.com.askey.ia4test.MqttChangedReceiver;
import tw.com.askey.ia4test.QueryDeviceInfoTask;
import tw.com.askey.ia4test.TestConst;
import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.builder.ActiveDeviceRequestBuilder;
import tw.com.askey.webservice.sdk.api.builder.FBKeypairRequestBuilder;
import tw.com.askey.webservice.sdk.api.builder.GetKeypairRequestBuilder;
import tw.com.askey.webservice.sdk.api.builder.UpdateDeviceInfoRequestBuilder;
import tw.com.askey.webservice.sdk.api.builder.auth.LoginUserBuilder;
import tw.com.askey.webservice.sdk.api.builder.auth.RegisterUserBuilder;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;
import tw.com.askey.webservice.sdk.api.response.GetCertResponse;
import tw.com.askey.webservice.sdk.api.response.GetDeviceBasicInfoResponse;
import tw.com.askey.webservice.sdk.api.response.GetDeviceDetailResponse;
import tw.com.askey.webservice.sdk.api.response.GetKeypairResponse;
import tw.com.askey.webservice.sdk.api.response.UserDeviceListResponse;
import tw.com.askey.webservice.sdk.api.response.auth.AccountUserResponse;
import tw.com.askey.webservice.sdk.iot.AskeyIoTUtils;
import tw.com.askey.webservice.sdk.iot.MqttActionConst;
import tw.com.askey.webservice.sdk.iot.callback.MqttConnectionCallback;
import tw.com.askey.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import tw.com.askey.webservice.sdk.iot.callback.ReadThingShadowCallback;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttDesiredBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttReportedJStrBuilder;
import tw.com.askey.webservice.sdk.iot.message.builder.MqttStatesMsgBuilder;
import tw.com.askey.webservice.sdk.model.CognitoDataModel;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.service.AskeyIoTService;
import tw.com.askey.webservice.sdk.service.AskeyWebService;
import tw.com.askey.webservice.sdk.setting.FacebookLoginSource;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * sample的執行順序大致上是依照button由上到下的順序
 * 1. 先登入facebook後取得AWS相關的token
 * 2. 取得device資訊
 * 3. config mqtt manager(連結mqtt service)
 * 4. 連接mqtt manager
 * 5. publish message
 */
public class MasterTestActivity extends AppCompatActivity implements View.OnClickListener, ServiceCallback, MqttServiceConnectedCallback, ReadThingShadowCallback, TestConst {

    CallbackManager callbackManager;
    String testDeviceId;

    DeviceInfoResponse deviceInfoResponse;

    FacebookLoginSource loginSource;

    String cert;
    String pk;

    String fbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

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

        findViewById(R.id.ia4_test_pk_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_register_btn).setOnClickListener(this);

        findViewById(R.id.ia4_test_email_login_btn).setOnClickListener(this);

//        findViewById(R.id.ia4_test_mqtt_cert_connect_btn).setOnClickListener(this);

        initFBSetting();

        MqttChangedReceiver receiver = new MqttChangedReceiver();
        IntentFilter filter = new IntentFilter(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        Log.e("ia4test", "onclick");
        if(view.getId() == R.id.ia4_test_fb_login_btn){
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
        }
        else if(view.getId() == R.id.ia4_test_device_list_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserDeviceListResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).
                            userDeviceList(ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID());
                    if(response != null){
                        List<String> devices = response.getDevices();
                        for(int i=0;i<devices.size();i++){
                            Log.e("ia4test", devices.get(i));
                        }
                    }
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

                    if(deviceInfoResponse != null){
                        AskeyIoTService.getInstance(getApplicationContext())
                                .configAWSIot(
                                        AskeyIoTUtils.translatMqttUseEndpoint(deviceInfoResponse.getDevice().getRestEndpoint()),
                                        MasterTestActivity.this);
                    }
                }
            }).start();

        }
        else if(view.getId() == R.id.ia4_test_mqtt_desire_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext()).publishDesiredMessage(deviceInfoResponse.getDevice().getShadowTopic(), buildTestDesiredMqttMsg());
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_mqtt_report_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext()).publishReportedMessage(
                            deviceInfoResponse.getDevice().getShadowTopic(),
                            buildTestReportedMqttMsg()
                    );
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_get_shadow_btn){
            AskeyIoTService.getInstance(getApplicationContext()).readThingShadow(
                    AskeyIoTUtils.translatMqttUseEndpoint(deviceInfoResponse.getDevice().getRestEndpoint()),
                    deviceInfoResponse.getDevice().getIotThingname(), this);
        }
        else if(view.getId() == R.id.ia4_test_device_info_btn){
            getDeviceInfoTest(ServicePreference.getCognitoDataFromPreference(this).getUserID(),
                    TEST_MODULE,
                    TEST_UNIQUEID
                    );
        }
        else if(view.getId() == R.id.ia4_test_change_name_btn){
            updateDeviceNameTest(testDeviceId, "New Camera Name");
        }
        else if(view.getId() == R.id.ia4_test_device_basic_info_btn){
            if(deviceInfoResponse != null && deviceInfoResponse.getDevice() != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetDeviceBasicInfoResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).getDeviceBasicInfo(deviceInfoResponse.getDevice().getDeviceid());
                        Log.e("ia4test", response.getDevice().getIotThingname());
                    }
                }).start();
            }
        }
        else if(view.getId() == R.id.ia4_test_device_detail_info_btn){
            if(deviceInfoResponse != null && deviceInfoResponse.getDevice() != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetDeviceDetailResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).getDeviceDetailInfo(deviceInfoResponse.getDevice().getDeviceid());
                        Log.e("ia4test", (String)response.getDevice().getDeviceDetails().get("macaddress"));
                    }
                }).start();
            }
        }
        else if(view.getId() == R.id.ia4_test_mqtt_states_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AskeyIoTService.getInstance(getApplicationContext()).publishStatesMqttMessage(
                            deviceInfoResponse.getDevice().getShadowTopic(),
                            buildTestStatesMqttMsg());
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_cert_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    GetCertResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).getCert(
                            ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID()
                    );
                    Log.e("ia4test", response.getCertificatePem());
                    cert = response.getCertificatePem();
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_pk_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(loginSource != null){
                        GetKeypairRequestBuilder builder = new FBKeypairRequestBuilder(
                                ServicePreference.getCognitoDataFromPreference(MasterTestActivity.this).getUserID(),
                                loginSource
                        );
                        GetKeypairResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).getKeypair(builder.getKeypairRequest());
                        Log.e("ia4test", response.getPrivateKey());
                        pk = response.getPrivateKey();
                    }
                }
            }).start();

//            Intent it = new Intent(this, CognitoMqttTestActivity.class);
//            Intent it = new Intent(this, AuthTestActivity.class);
//            this.startActivity(it);

        }
        else if(view.getId() == R.id.ia4_test_email_register_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RegisterUserBuilder builder = new RegisterUserBuilder(
                            TEST_EMAIL,
                            TEST_PWD,
                            TEST_NAME
                    );
                    AccountUserResponse response = AskeyWebService.getInstance(MasterTestActivity.this).registerUser(builder);
                    Log.e("ia4test", "Register result: "+response.getUserid()+", "+response.getToken());
                }
            }).start();
        }
        else if(view.getId() == R.id.ia4_test_email_login_btn){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginUserBuilder builder = new LoginUserBuilder(
                            TEST_EMAIL,
                            TEST_PWD
                    );
                    AccountUserResponse response = AskeyWebService.getInstance(MasterTestActivity.this).loginUser(builder);
                    Log.e("ia4test", "Login result: "+response.getUserid()+", "+response.getToken());
                }
            }).start();
        }
    }

    private void initFBSetting(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        final String token = AccessToken.getCurrentAccessToken().getToken();

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

//                                Log.e("testing", "FB name: "+object.optString("name"));
//                                Log.e("testing", "FB id: "+object.optString("id"));
//                                Log.e("testing", "FB mail: "+object.optString("email"));
////                                Log.e("testing", "FB frient list: "+object.optString(""));
//                                Log.e("testing", object.toString());
//
//
//                                StringBuilder builder = new StringBuilder();
//                                builder.append("askey"+"://")
//                                        .append("smacam.cameraList"+"/")
//                                        .append("live"+"?")
//                                        .append("camID=AAIKVPCX"+"&")
//                                        .append("accountID="+"101736383609303"+"&")
//                                        .append("accessToken="+"EAAIfSh676FYBANi1zx7AAVSH2ZAkO1fiZACZAAmA173wBHIq8ERnCNZAOW1Oljyohcg5hFfETsaxqLD7Ld1JyrlWbCK7E6oxLs3xeGidNsMmI6wvLKXzMVPSW06Ha805ynw3EaEXyXeKEH34rZAKwFL12r4isweg6CELiIjO6QgZDZD"+"&")
//                                        .append("authType="+"Facebook");

//                                String scheme = CameraLaunchHelper.getLaunchScheme(
//                                        CameraLaunchHelper.CameraAction.live,
//                                        CameraLaunchHelper.AuthType.Facebook,
//                                        "AAIKVPCX",
//                                        "101736383609303",
//                                        "EAAIfSh676FYBANHbzBkemBYFfxP5MWyERFPEx0mRZAs28LBKHEOqglCjXsdbTGnd3chLKTknXeZCaZAXFOu0m9pWpuI0KUdxYCuzCrSB6ltvnqPtIsAuBpK9XTZC8dMSZAiHyHuqLnmdjU44oF6ZCkKaWhjazn7K6T7ZCUy9FVKDgZDZD"
//                                        );
//
//                                Log.e("ia4test", scheme);
//
//                                Intent it = new Intent(Intent.ACTION_VIEW);
//                                it.setData(Uri.parse(scheme));
//
//                                startActivity(it);



                                fbName = object.optString("name");
                                apiTest(AccessToken.getCurrentAccessToken().getToken(),
                                        object.optString("name"),
                                        object.optString("id"),
                                        object.optString("email"));



//                                new GraphRequest(AccessToken.getCurrentAccessToken(), "/"+object.optString("id")+"/friends",
//                                        null,
//                                        HttpMethod.GET,
//                                        new GraphRequest.Callback() {
//                                            public void onCompleted(GraphResponse response) {
//                                                Log.e("testing", response.getRawResponse());
//
//                                                String appLinkUrl, previewImageUrl;
//
//
//                                                appLinkUrl = "https://fb.me/672148159615320";
//
//                                                Log.e("testing", "link url: "+appLinkUrl);
//
//                                                previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";
//
//                                                if (AppInviteDialog.canShow()) {
//                                                    AppInviteContent content = new AppInviteContent.Builder()
//                                                            .setApplinkUrl(appLinkUrl)
//                                                            .setPreviewImageUrl(previewImageUrl)
//                                                            .build();
//                                                    AppInviteDialog.show(MasterTestActivity.this, content);
//                                                }
//                                            }
//                                        }).executeAsync();
//
//                                Log.e("testing", "Get para end.");



                            }
                        });
                        Bundle b = new Bundle();
                        b.putString("fields", "id,name,email");
                        request.setParameters(b);
                        request.executeAsync();


//                        Log.e("testing", "FB login complete: "+ ShareDialog.canShow(ShareLinkContent.class));

//                        List<String> ids = new ArrayList<String>();
//                        ids.add("100012630090765");
//
//                        Log.e("testing", "id: "+ids.get(0));
//
//                        final ShareLinkContent content = new ShareLinkContent.Builder()
//                                .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                                .setPeopleIds(ids)
//                                .build();
//
//                        ShareDialog.show(MasterTestActivity.this, content);


                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private MqttMsgBuilder buildTestDesiredMqttMsg(){
        MqttDesiredBuilder builder = new MqttDesiredBuilder(deviceInfoResponse.getDevice().getDeviceid());
        builder.addCommand("temp", "55");
        builder.addCommand("humidity", "60");

//        MqttDesiredJStrBuilder builder = new MqttDesiredJStrBuilder(deviceInfoResponse.getDevice().getDeviceid());
//        builder.setJsonString("{\"humidity\": \"70\",\"temp\": \"30\",\"act\": {\"act03\": \"test\"}}");

        return builder;
    }

    private MqttMsgBuilder buildTestReportedMqttMsg(){
//        MqttReportedBuilder builder = new MqttReportedBuilder(deviceInfoResponse.getDevice().getDeviceid());
//        builder.addReportData("temp", "30");
//        builder.addReportData("humidity", "70");
//        HashMap<String, String> actMap = new HashMap<>();
//        actMap.put("act01", "test");
//        actMap.put("act02", "test02");
//        builder.addReportData("act", actMap);

        MqttReportedJStrBuilder builder = new MqttReportedJStrBuilder(deviceInfoResponse.getDevice().getDeviceid());
        builder.setJsonString("{\"humidity\": \"70\",\"temp\": \"30\",\"act\": {\"act01\": \"test\",\"act02\": \"test02\"}}");

        return builder;
    }

    private MqttMsgBuilder buildTestStatesMqttMsg(){
        MqttStatesMsgBuilder builder = new MqttStatesMsgBuilder(deviceInfoResponse.getDevice().getDeviceid());
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
        else if(tag.equals(QueryDeviceInfoTask.class.getName())){
            deviceInfoResponse = (DeviceInfoResponse) result;
            Log.e("ia4test", "Get device info response.");
        }
    }

    @Override
    public void error(String tag, Object result) {
        if(tag.equals("ia4register")){
            loginSource = null;
        }
    }

    @Override
    public void problem(String tag, Object result) {

    }

    @Override
    public void onMqttServiceConnectedSuccess() {

        Log.e("ia4test", "bind success");
        AskeyIoTService.getInstance(getApplicationContext()).connectToAWSIot(fbName, cert, pk, new MqttConnectionCallback() {
            @Override
            public void onConnected() {

                if(deviceInfoResponse != null && deviceInfoResponse.getDevice() != null){
                    AskeyIoTService.getInstance(getApplicationContext()).subscribeMqttDelta(deviceInfoResponse.getDevice().getShadowTopic());
//                    AskeyIoTService.getInstance(getApplicationContext()).subscribeMqtt(deviceInfoResponse.getDevice().getShadowTopic());
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

    private void apiTest(final String fbToken, String name, String id, String mail){
        loginSource = new FacebookLoginSource(name,
                id,
                mail,
                fbToken);

        AskeyWebService.getInstance(getApplicationContext()).active(loginSource, "ia4register", this);

    }

    //目前上面的是測試資料, 使用時請填上正式資料
    private void getDeviceInfoTest(final String userid, final String module, final String uniqueid){
        ActiveDeviceRequestBuilder builder = new ActiveDeviceRequestBuilder(
                userid,
                module,
                uniqueid,
                "Door Camera"
        );

        HashMap<String, String> details = new HashMap<>();
        details.put("macaddress", "FCB4E6CC179A");
        QueryDeviceInfoTask task = new QueryDeviceInfoTask();
        task.setServiceCallback(this);
        task.execute(builder);
    }

    private void updateDeviceNameTest(final String deviceid, final String name){
        if(deviceid == null){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateDeviceInfoRequestBuilder builder = new UpdateDeviceInfoRequestBuilder(name);
                AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).updateDeviceInfo(deviceid, builder.getUpdateDeviceInfoRequest());
            }
        }).start();
    }

    @Override
    public void getThingShadow(String shadowResult) {
        Log.e("ia4test", "shadow: "+shadowResult);
    }
}
