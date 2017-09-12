package tw.com.askey.ia4test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.askeycloud.webservice.sdk.api.response.device.IoTDeviceInfoResponse;
import com.askeycloud.webservice.sdk.service.device.AskeyIoTDeviceService;
import com.askeycloud.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/8.
 */
public class QueryDeviceInfoTask extends AsyncTask<String, Integer, IoTDeviceInfoResponse> {

    private ServiceCallback serviceCallback;

    private final Context context;

    public QueryDeviceInfoTask(Context context){
        this.context = context;
    }

    @Override
    protected IoTDeviceInfoResponse doInBackground(String... params) {

//        ActiveDeviceRequestBuilder builder = activeDeviceRequestBuilders[0];

        //GetDeviceInfoHelper的運作流程大致如下:
        //先 get device info
        //如果是已註冊過的則回傳結果, 否則進行下一步
        //進行 device 的註冊(activeDevice)
        //因為這層原因
        //所以使用上需要帶的是註冊 device 的參數
        //如果要個別使用 api 請參考 sdk 的 javadoc
//        DeviceInfoResponse response = GetDeviceInfoHelper.actHelper(builder);
//
//        return response;

        IoTDeviceInfoResponse ioTDeviceInfoResponse = AskeyIoTDeviceService.
                getInstance(context).lookupIoTDevice(TestConst.YOUR_DEVICE_MODEL, TestConst.YOUR_DEVICE_UNIQUEID);

        Log.e("ia4test", "device shadow topic: "+ ioTDeviceInfoResponse.getShadowTopic());

        if(ioTDeviceInfoResponse != null && ioTDeviceInfoResponse.getDeviceid() != null){
//                    Log.e("ia4test", "code: "+ioTDeviceInfoResponse.getCode()+", message: "+ioTDeviceInfoResponse.getMessage());

//            Log.e("ia4test", "device id: "+ ioTDeviceInfoResponse.getDeviceid());
            return ioTDeviceInfoResponse;
        }
        else{
            return null;
        }

    }

    @Override
    protected void onPostExecute(IoTDeviceInfoResponse ioTDeviceInfoResponse) {
        if(serviceCallback != null){
            if(ioTDeviceInfoResponse != null){
                serviceCallback.success(getClass().getName(), ioTDeviceInfoResponse);
            }
            else{
                serviceCallback.error(getClass().getName(), null);
            }
        }
        super.onPostExecute(ioTDeviceInfoResponse);
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }
}
