package askey.com.tw.cognitomqtt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import junit.framework.Test;

import java.util.HashMap;

import tw.com.askey.ia4test.QueryDeviceInfoTask;
import tw.com.askey.ia4test.TestConst;
import tw.com.askey.webservice.sdk.api.builder.ActiveDeviceRequestBuilder;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;
import tw.com.askey.webservice.sdk.iot.AskeyIoTUtils;
import tw.com.askey.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import tw.com.askey.webservice.sdk.service.AskeyIoTService;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/24.
 */
public class DeviceTestHandler implements TestConst {

    Context context;
    DeviceInfoResponse deviceInfoResponse;

    public DeviceTestHandler(Context context){
        this.context = context;
    }

    //目前上面的是測試資料, 使用時請填上正式資料
    public void getDeviceInfoTest(final String userid, final String module, final String uniqueid, ServiceCallback callback){
        ActiveDeviceRequestBuilder builder = new ActiveDeviceRequestBuilder(
                userid,
                module,
                uniqueid,
                "Door Camera"
        );

        HashMap<String, String> details = new HashMap<>();
        details.put("macaddress", "FCB4E6CC179A");
        QueryDeviceInfoTask task = new QueryDeviceInfoTask();
        task.setServiceCallback(callback);
        task.execute(builder);
    }

    public void connectToMQTT(final MqttServiceConnectedCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AskeyIoTService.getInstance(context)
                        .configAWSIot(
                                AskeyIoTUtils.translatMqttUseEndpoint(deviceInfoResponse.getDevice().getRestEndpoint()),
                                callback);
            }
        }).start();
    }

    public DeviceInfoResponse getDeviceInfoResponse() {
        return deviceInfoResponse;
    }

    public void setDeviceInfoResponse(DeviceInfoResponse deviceInfoResponse) {
        this.deviceInfoResponse = deviceInfoResponse;
    }

    public Bundle getDeviceInfoBundle(){
        if(deviceInfoResponse != null && deviceInfoResponse.getDevice() != null){
            Bundle bundle = new Bundle();
            bundle.putString(TOPIC_KEY, deviceInfoResponse.getDevice().getShadowTopic());
            bundle.putString(ENDPOINT_KEY, deviceInfoResponse.getDevice().getRestEndpoint());
            bundle.putString(THING_NAME_KEY, deviceInfoResponse.getDevice().getIotThingname());
            bundle.putString(DEVICE_ID_KEY, deviceInfoResponse.getDevice().getDeviceid());

            return bundle;
        }
        return null;
    }
}
