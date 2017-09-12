package com.askeycloud.webservice.sdk.service.device;

import android.content.Context;
import android.support.annotation.NonNull;

import com.askeycloud.sdk.core.api.response.BasicResponse;
import com.askeycloud.sdk.device.AskeyCloudDeviceApiUtils;
import com.askeycloud.sdk.device.builder.UpdateIoTDeviceDetailBuilder;
import com.askeycloud.sdk.device.request.CreateIoTDeviceRequest;
import com.askeycloud.sdk.device.response.AWSIoTCertResponse;
import com.askeycloud.sdk.device.response.IoTDeviceDetailInfoResponse;
import com.askeycloud.sdk.device.response.IoTDeviceDisplayInfoResponse;
import com.askeycloud.sdk.device.response.IoTDeviceEndpointResponse;
import com.askeycloud.sdk.device.response.IoTDeviceInfoResponse;
import com.askeycloud.sdk.device.response.UserIoTDeviceListResponse;
import com.askeycloud.webservice.sdk.ServiceConst;
import com.askeycloud.webservice.sdk.service.BasicAskeyCloudService;

/**
 * Created by david5_huang on 2017/1/20.
 */

public abstract class AskeyIoTUserIdDeviceService extends BasicAskeyCloudService {


    AskeyIoTUserIdDeviceService(Context context){
        super(context);
        settingInitApiUrl(ServiceConst.API_DEVICE_META_NAME, ServiceConst.API_DEVICE_V3_URL);
    }

    public AWSIoTCertResponse getIotCert(@NonNull String token){
        AWSIoTCertResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).awsIoTCert(apiKey, token);
        return response;
    }

    public IoTDeviceInfoResponse createIoTDevice(@NonNull String token, CreateIoTDeviceRequest request){
        IoTDeviceInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).createIoTDevice(apiKey, token, request);
        return response;
    }

    public IoTDeviceInfoResponse lookupIoTDevice(@NonNull String token, String model, String uniqueId){
        IoTDeviceInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).lookupIoTDevice(apiKey, token, model, uniqueId);
        return response;
    }

    public BasicResponse removeIoTDevice(@NonNull String token, IoTDeviceInfoResponse deviceInfoResponse){
        return removeIoTDevice(token, deviceInfoResponse.getDeviceid());
    }

    public BasicResponse removeIoTDevice(@NonNull String token, String deviceId){
        BasicResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).removeIoTDevice(apiKey, token, deviceId);
        return response;
    }

    public UserIoTDeviceListResponse userIoTDeviceList(@NonNull String token){
        UserIoTDeviceListResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).userIoTDeviceList(apiKey, token);
        return response;
    }

    public IoTDeviceDetailInfoResponse getIoTDeviceDetailInfo(@NonNull String token, String deviceId){
        IoTDeviceDetailInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceDetailInfo(apiKey, token, deviceId);
        return response;
    }

    public IoTDeviceDetailInfoResponse updateIoTDeviceDetailInfo(@NonNull String token, UpdateIoTDeviceDetailBuilder builder){
        IoTDeviceDetailInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTDeviceDetailInfo(
                apiKey,
                token,
                builder
        );
        return response;
    }

    public BasicResponse updateIoTDeviceDisplayInfo(@NonNull String token, String deviceId, String newDisplay){
        BasicResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTDeviceDisplayInfo(apiKey, token, deviceId, newDisplay);
        return response;
    }

    public IoTDeviceDisplayInfoResponse getIoTDeviceDisplayInfo(@NonNull String token, String deviceId){
        IoTDeviceDisplayInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceDisplayInfo(
                apiKey,
                token,
                deviceId
        );
        return response;
    }

    public IoTDeviceEndpointResponse getIoTDevieEndpoint(@NonNull String token, String deviceId){
        IoTDeviceEndpointResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceEndpoint(
                apiKey,
                token,
                deviceId
        );
        return response;
    }

}
