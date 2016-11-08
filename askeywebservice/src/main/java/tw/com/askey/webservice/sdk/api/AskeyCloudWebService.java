package tw.com.askey.webservice.sdk.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by david5_huang on 2016/7/26.
 */
public interface AskeyCloudWebService {

    //Auth api
    @POST("auth/adduser")
    Call<String> addUser(@Body String request);

    @POST("auth/getuser")
    Call<String> getUser(@Body String request);

    @POST("auth/getlongtoken")
    Call<String> getLongToken(@Body String request);

    /**
     * @param request
     * @return
     */
    @POST("user/getkeypair")
    Call<String> getKeyPair(@Body String request);

    /**
     * Retrieve a certificate for user's iot device to connect to AWS IoT service.
     * @param userId
     * @return
     */
    @GET("user/getcert")
    Call<String> getCert(@Query("userid") String userId);

    @POST("user/register")
    Call<String> registerUser(@Body String request);

    @POST("user/login")
    Call<String> loginUser(@Body String request);

    //IoT api
    /**
     * Register and activate a device
     * @param request
     * @return
     */
    @PUT("device")
    Call<String> activeDevice(@Body String request);

    /**
     * Lookup a device by model & uniqueid, all values need urlencode.
     * @param userid
     * @param model
     * @param uniqueid
     * @return
     */
    @GET("device")
    Call<String> getDeviceInfo(@Query("userid") String userid, @Query("model") String model, @Query("uniqueid") String uniqueid);

    /**
     * Update a device info, only displayname can be updated currently.
     * @param deviceId
     * @param request
     * @return
     */
    @PATCH("device/{deviceid}")
    Call<String> updateDeviceInfo(@Path("deviceid") String deviceId, @Body String request);

    /**
     * Get a device basic info
     * @param deviceId
     * @return
     */
    @GET("device/{deviceid}")
    Call<String> getDeviceBasicInfo(@Path("deviceid") String deviceId);

    /**
     * List user owned device
     * @param userId
     * @return
     */
    @GET("devicelist")
    Call<String> getUserDeviceList(@Query("userid") String userId);


    /**
     * Get a device detail info, the detail info is defined when activeDevice.
     * @param deviceId
     * @return
     */
    @GET("device/{deviceid}/detail")
    Call<String> getDeviceDetailInfo(@Path("deviceid") String deviceId);

}
