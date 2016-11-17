package tw.com.askey.webservice.sdk.service.device;

import android.content.Context;

import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.request.ActiveDeviceRequest;
import tw.com.askey.webservice.sdk.api.response.auth.AWSIoTCertResponse;
import tw.com.askey.webservice.sdk.api.response.device.IoTDeviceInfoResponse;
import tw.com.askey.webservice.sdk.api.response.device.UserIoTDeviceListResponse;

/**
 * Created by david5_huang on 2016/11/14.
 */
public class AskeyIoTDeviceService {

    private final Context context;
    private static AskeyIoTDeviceService instance;

    private String useUrl;

    AskeyIoTDeviceService(Context context){
        this.context = context;
        useUrl = ServiceConst.API_DEVICE_V2_URL;
    }

    public static AskeyIoTDeviceService getInstance(Context context){
        if(instance == null){
            instance = new AskeyIoTDeviceService(context);
        }
        return instance;
    }

    public AWSIoTCertResponse getIotCert(String userId){
        AWSIoTCertResponse response = AskeyCloudApiUtils.getInstance(useUrl).awsIoTCert(userId);
        return response;
    }

    public IoTDeviceInfoResponse createIoTDevice(ActiveDeviceRequest request){
        IoTDeviceInfoResponse response = AskeyCloudApiUtils.getInstance(useUrl).createIoTDevice(request);
        return response;
    }

    public IoTDeviceInfoResponse lookupIoTDevice(String userId, String model, String uniqueId){
        IoTDeviceInfoResponse response = AskeyCloudApiUtils.getInstance(useUrl).lookupIoTDevice(userId, model, uniqueId);
        return response;
    }

    public UserIoTDeviceListResponse userIoTDeviceList(String userId){
        UserIoTDeviceListResponse response = AskeyCloudApiUtils.getInstance(useUrl).userIoTDeviceList(userId);
        return response;
    }

    protected void updateUseURL(String useUrl){
        this.useUrl = useUrl;
    }
}
