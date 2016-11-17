package tw.com.askey.webservice.sdk.api.helper;

import android.util.Log;

import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.builder.ActiveDeviceRequestBuilder;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;

/**
 * Created by david5_huang on 2016/8/8.
 */
public class GetDeviceInfoHelper {

    public static DeviceInfoResponse actHelper(ActiveDeviceRequestBuilder builder){

        DeviceInfoResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).getDeviceInfo(
                builder.getActiveDeviceRequest().getUserid(),
                builder.getActiveDeviceRequest().getDevice().getModel(),
                builder.getActiveDeviceRequest().getDevice().getUniqueid()
        );

        if(response == null || response.getDevice() == null){
            response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).activeDevice(builder.getActiveDeviceRequest());
        }

        return response;

    }

}
