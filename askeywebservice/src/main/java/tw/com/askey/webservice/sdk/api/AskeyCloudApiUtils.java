package tw.com.askey.webservice.sdk.api;

import retrofit2.Call;
import tw.com.askey.webservice.sdk.api.request.ActiveDeviceRequest;
import tw.com.askey.webservice.sdk.api.request.AddUserRequest;
import tw.com.askey.webservice.sdk.api.request.GetKeypairRequest;
import tw.com.askey.webservice.sdk.api.request.GetLongTokenRequest;
import tw.com.askey.webservice.sdk.api.request.GetUserRequest;
import tw.com.askey.webservice.sdk.api.request.auth.LoginUserRequest;
import tw.com.askey.webservice.sdk.api.request.auth.RegisterUserRequest;
import tw.com.askey.webservice.sdk.api.request.UpdateDeviceInfoRequest;
import tw.com.askey.webservice.sdk.api.response.auth.AccountUserResponse;
import tw.com.askey.webservice.sdk.api.response.AddUserResponse;
import tw.com.askey.webservice.sdk.api.response.DeviceInfoResponse;
import tw.com.askey.webservice.sdk.api.response.GetCertResponse;
import tw.com.askey.webservice.sdk.api.response.GetDeviceBasicInfoResponse;
import tw.com.askey.webservice.sdk.api.response.GetDeviceDetailResponse;
import tw.com.askey.webservice.sdk.api.response.GetKeypairResponse;
import tw.com.askey.webservice.sdk.api.response.GetLongTokenResponse;
import tw.com.askey.webservice.sdk.api.response.GetUserResponse;
import tw.com.askey.webservice.sdk.api.response.UpdateDeviceInfoResponse;
import tw.com.askey.webservice.sdk.api.response.UserDeviceListResponse;
import tw.com.askey.webservice.sdk.api.response.auth.AWSIoTCertResponse;
import tw.com.askey.webservice.sdk.api.response.device.IoTDeviceInfoResponse;
import tw.com.askey.webservice.sdk.api.response.device.UserIoTDeviceListResponse;

/**
 * Created by david5_huang on 2016/7/27.<br/>
 * <br/>
 * AskeyWebService query RESTful api interface.<br/>
 * <br/>
 * You can use like:<br/>
 * <b>AskeyCloudApiUtils.getInstance(String url)<b/>
 */
public class AskeyCloudApiUtils extends BaseApiUtils {

    private static AskeyCloudApiUtils instance;

    public static AskeyCloudApiUtils getInstance(String url){
        if(instance == null || url == null || !AskeyCloudApiUtils.url.equals(url)){
            instance = new AskeyCloudApiUtils(url);
        }
        return instance;
    }

    AskeyCloudApiUtils(String url) {
        super(url);
    }

    public AddUserResponse queryAddUser(AddUserRequest request){
        Call<String> response = webService.addUser(convertRequestToJsonString(request));
        return parseJson(doApi(response), AddUserResponse.class);
    }

    public GetUserResponse queryGetUser(GetUserRequest request){
        Call<String> response = webService.getUser(convertRequestToJsonString(request));
        return parseJson(doApi(response), GetUserResponse.class);
    }

    public GetLongTokenResponse queryLongToken(GetLongTokenRequest request){
        Call<String> response = webService.getLongToken(convertRequestToJsonString(request));
        return parseJson(doApi(response), GetLongTokenResponse.class);
    }

    public AccountUserResponse registerUser(RegisterUserRequest request){
        Call<String> response = webService.registerUser(convertRequestToJsonString(request));
        return parseJson(doApi(response), AccountUserResponse.class);
    }

    public AccountUserResponse loginUser(LoginUserRequest request){
        Call<String> response = webService.loginUser(convertRequestToJsonString(request));
        return parseJson(doApi(response), AccountUserResponse.class);
    }

    @Deprecated
    public GetKeypairResponse getKeypair(GetKeypairRequest request){
        Call<String> response = webService.getKeyPair(convertRequestToJsonString(request));
        return parseJson(doApi(response), GetKeypairResponse.class);
    }

    @Deprecated
    public GetCertResponse getCert(String userId){
        Call<String> response = webService.getCert(userId);
        return parseJson(doApi(response), GetCertResponse.class);
    }

    @Deprecated
    public DeviceInfoResponse activeDevice(ActiveDeviceRequest request){
        Call<String> response = webService.activeDevice(convertRequestToJsonString(request));
        return parseJson(doApi(response), DeviceInfoResponse.class);
    }

    @Deprecated
    public DeviceInfoResponse getDeviceInfo(String userid, String module, String uniqueid){
        Call<String> response = webService.getDeviceInfo(userid, module, uniqueid);
        return parseJson(doApi(response), DeviceInfoResponse.class);
    }

    public UpdateDeviceInfoResponse updateDeviceInfo(String deviceId, UpdateDeviceInfoRequest request){
        Call<String> response = webService.updateDeviceInfo(deviceId, convertRequestToJsonString(request));
        return parseJson(doApi(response), UpdateDeviceInfoResponse.class);
    }

    @Deprecated
    public UserDeviceListResponse userDeviceList(String userId){
        Call<String> response = webService.getUserDeviceList(userId);
        return parseJson(doApi(response), UserDeviceListResponse.class);
    }

    public GetDeviceBasicInfoResponse getDeviceBasicInfo(String deviceId){
        Call<String> response = webService.getDeviceBasicInfo(deviceId);
        return parseJson(doApi(response), GetDeviceBasicInfoResponse.class);
    }

    public GetDeviceDetailResponse getDeviceDetailInfo(String deviceId){
        Call<String> response = webService.getDeviceDetailInfo(deviceId);
        return parseJson(doApi(response), GetDeviceDetailResponse.class);
    }

    public AWSIoTCertResponse awsIoTCert(String userId){
        Call<String> response = webService.getIoTCert(userId);
        return parseJson(doApi(response), AWSIoTCertResponse.class);
    }

    public IoTDeviceInfoResponse createIoTDevice(ActiveDeviceRequest request){
        Call<String> response = webService.createIoTDevice(convertRequestToJsonString(request));
        return parseJson(doApi(response), IoTDeviceInfoResponse.class);
    }

    public IoTDeviceInfoResponse lookupIoTDevice(String userId, String model, String uniqueId){
        Call<String> response = webService.lookupIoTDeviceInfo(userId, model, uniqueId);
        return parseJson(doApi(response), IoTDeviceInfoResponse.class);
    }

    public UserIoTDeviceListResponse userIoTDeviceList(String userId){
        Call<String> response = webService.userIoTDeviceList(userId);
        return parseJson(doApi(response), UserIoTDeviceListResponse.class);
    }

}
