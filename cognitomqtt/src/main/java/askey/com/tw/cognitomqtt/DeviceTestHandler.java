package askey.com.tw.cognitomqtt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import tw.com.askey.ia4test.QueryDeviceInfoTask;
import tw.com.askey.ia4test.TestConst;

import com.askeycloud.webservice.sdk.api.response.device.IoTDeviceInfoResponse;
import com.askeycloud.webservice.sdk.iot.AskeyIoTUtils;
import com.askeycloud.webservice.sdk.iot.callback.MqttServiceConnectedCallback;
import com.askeycloud.webservice.sdk.service.iot.AskeyIoTService;
import com.askeycloud.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/24.
 */
public class DeviceTestHandler implements TestConst {

    Context context;
    IoTDeviceInfoResponse ioTDeviceInfoResponse;

    public DeviceTestHandler(Context context){
        this.context = context;
    }

    //目前上面的是測試資料, 使用時請填上正式資料
    public void getDeviceInfoTest(final String userid, final String module, final String uniqueid, ServiceCallback callback){
        QueryDeviceInfoTask task = new QueryDeviceInfoTask(context);
        task.setServiceCallback(callback);
        task.execute(userid);
    }

    public void connectToMQTT(final MqttServiceConnectedCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("ia4test", "connect mqtt");
                AskeyIoTService.getInstance(context)
                        .configAWSIot(
                                AskeyIoTUtils.translatMqttUseEndpoint(ioTDeviceInfoResponse.getRestEndpoint()),
                                callback);
            }
        }).start();
    }

    public IoTDeviceInfoResponse getDeviceInfoResponse() {
        return ioTDeviceInfoResponse;
    }

    public void setDeviceInfoResponse(IoTDeviceInfoResponse ioTDeviceInfoResponse) {
        this.ioTDeviceInfoResponse = ioTDeviceInfoResponse;
    }

    public Bundle getDeviceInfoBundle(){
        if(ioTDeviceInfoResponse != null){
            Bundle bundle = new Bundle();

            bundle.putString(TOPIC_KEY, ioTDeviceInfoResponse.getShadowTopic());
            bundle.putString(ENDPOINT_KEY, ioTDeviceInfoResponse.getRestEndpoint());
            bundle.putString(THING_NAME_KEY, ioTDeviceInfoResponse.getIotThingname());
            bundle.putString(DEVICE_ID_KEY, ioTDeviceInfoResponse.getDeviceid());

            return bundle;
        }
        return null;
    }
}
