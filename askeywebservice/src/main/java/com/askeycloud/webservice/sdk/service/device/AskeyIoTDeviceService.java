package com.askeycloud.webservice.sdk.service.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.model.auth.BasicUserDataModel;
import com.askeycloud.webservice.sdk.model.auth.v3.OAuthLoginResultModel;

/**
 * AskeyIoTDeviceService use to create IoT device and control device's parameter.
 */
public class AskeyIoTDeviceService extends AskeyIoTUserIdDeviceService {

    private static AskeyIoTDeviceService instance;

    AskeyIoTDeviceService(Context context){
        super(context);
    }

    public static AskeyIoTDeviceService getInstance(Context context){
        if(instance == null){
            instance = new AskeyIoTDeviceService(context);
        }
        else {
            if(!instance.context.equals(context)){
                instance = new AskeyIoTDeviceService(context);
            }
        }
        return instance;
    }

    /**
     * Get the IoT cert data. This function must to be execute after user login.
     * @return
     */
    public AWSIoTCertResponse getIotCert(){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            AWSIoTCertResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl)
                    .awsIoTCert(apiKey, getOAuthUserDataModel().getAccessToken());
            return response;
        } else {
            return getAuthParameterErrResponse(AWSIoTCertResponse.class);
        }
    }

    /**
     * @param request Creating IoT device api request.
     * @return
     */
    public IoTDeviceInfoResponse createIoTDevice(CreateIoTDeviceRequest request){

        if(ServicePreference.isAuthV3UserDataExist(context)){
            IoTDeviceInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).createIoTDevice(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    request);
            return response;
        } else{
            return getAuthParameterErrResponse(IoTDeviceInfoResponse.class);
        }

    }

    /**
     * Get IoT device data(e.g. device ID, IoT endpoint...) via model and device unique ID.
     * @param model Maybe is the product name or product ID.
     * @param uniqueId The device Unique ID, maybe use the serial number.
     * @return
     */
    public IoTDeviceInfoResponse lookupIoTDevice(String model, String uniqueId){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            IoTDeviceInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).lookupIoTDevice(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    model,
                    uniqueId);
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceInfoResponse.class);
        }

    }

    /**
     * @param deviceInfoResponse The device need to remove via IoTDeviceInfoResponse.
     * @return Only return API response status and message.
     */
    public BasicResponse removeIoTDevice(IoTDeviceInfoResponse deviceInfoResponse){
        return removeIoTDevice(deviceInfoResponse.getDeviceid());
    }


    /**
     * @param deviceId The device need to remove via device ID.
     * @return Only return API response status and message.
     */
    public BasicResponse removeIoTDevice(String deviceId){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            BasicResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).removeIoTDevice(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    deviceId
            );
            return response;
        } else {
            return getAuthParameterErrResponse(BasicResponse.class);
        }
    }

    /**
     * List user all IoT devices.
     * @return
     */
    public UserIoTDeviceListResponse userIoTDeviceList(){
        if(ServicePreference.isAuthV3UserDataExist(context)){

            Log.e(DebugConst.DEBUG_TAG, "device api url: "+useApiUrl);

            UserIoTDeviceListResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).userIoTDeviceList(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken());
            return response;
        } else {
            return getAuthParameterErrResponse(UserIoTDeviceListResponse.class);
        }
    }

    /**
     * The device details is created when call createIoTDevice function, and put all details to CreateIoTDeviceRequest.<br/>
     * @param deviceId Get device details via device ID.
     * @return
     */
    public IoTDeviceDetailInfoResponse getIoTDeviceDetailInfo(String deviceId){
        if(ServicePreference.isAuthV3UserDataExist(context)){

            IoTDeviceDetailInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceDetailInfo(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    deviceId);
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceDetailInfoResponse.class);
        }
    }

    /**
     * UpdateIoTDeviceDetailBuilder initial need to input a Hash
     * @param builder Put all details needed to update.
     * @return
     */
    public IoTDeviceDetailInfoResponse updateIoTDeviceDetailInfo(UpdateIoTDeviceDetailBuilder builder){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            IoTDeviceDetailInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTDeviceDetailInfo(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    builder
            );
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceDetailInfoResponse.class);
        }
    }

    public BasicResponse updateIoTDeviceDisplayInfo(String deviceId, String newDisplay){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            BasicResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTDeviceDisplayInfo(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    deviceId,
                    newDisplay);
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceDisplayInfoResponse.class);
        }
    }

    /**
     * @param deviceId The device register ID in AskeyCloud service.
     * @return
     */
    public IoTDeviceDisplayInfoResponse getIoTDeviceDisplayInfo(String deviceId){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            IoTDeviceDisplayInfoResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceDisplayInfo(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    deviceId
            );
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceDisplayInfoResponse.class);
        }
    }

    /**
     * @param deviceId The device register ID in AskeyCloud service.
     * @return
     */
    public IoTDeviceEndpointResponse getIoTDeviceEndpoint(String deviceId){
        if(ServicePreference.isAuthV3UserDataExist(context)){
            IoTDeviceEndpointResponse response = AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTDeviceEndpoint(
                    apiKey,
                    getOAuthUserDataModel().getAccessToken(),
                    deviceId
            );
            return response;
        } else {
            return getAuthParameterErrResponse(IoTDeviceEndpointResponse.class);
        }
    }

    private OAuthLoginResultModel getOAuthUserDataModel(){
        return ServicePreference.getAuthV3UserDataModel(context);
    }

}
