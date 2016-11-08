package tw.com.askey.ia4test;

import android.os.AsyncTask;

import tw.com.askey.webservice.sdk.api.builder.ActiveDeviceRequestBuilder;
import tw.com.askey.webservice.sdk.api.helper.GetDeviceInfoHelper;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/8.
 */
public class QueryDeviceInfoTask extends AsyncTask<ActiveDeviceRequestBuilder, Integer, DeviceInfoResponse> {

    private ServiceCallback serviceCallback;

    @Override
    protected DeviceInfoResponse doInBackground(ActiveDeviceRequestBuilder... activeDeviceRequestBuilders) {

        ActiveDeviceRequestBuilder builder = activeDeviceRequestBuilders[0];

        //GetDeviceInfoHelper的運作流程大致如下:
        //先 get device info
        //如果是已註冊過的則回傳結果, 否則進行下一步
        //進行 device 的註冊(activeDevice)
        //因為這層原因
        //所以使用上需要帶的是註冊 device 的參數
        //如果要個別使用 api 請參考 sdk 的 javadoc
        DeviceInfoResponse response = GetDeviceInfoHelper.actHelper(builder);

        return response;
    }

    @Override
    protected void onPostExecute(DeviceInfoResponse deviceInfoResponse) {
        if(serviceCallback != null){
            if(deviceInfoResponse != null && deviceInfoResponse.getDevice() != null){
                serviceCallback.success(getClass().getName(), deviceInfoResponse);
            }
            else{
                serviceCallback.error(getClass().getName(), null);
            }
        }
        super.onPostExecute(deviceInfoResponse);
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }
}
