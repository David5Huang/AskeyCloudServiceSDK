package tw.com.askey.webservice.sdk.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;

import tw.com.askey.webservice.aws.SNSSender;
import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.builder.auth.LoginUserBuilder;
import tw.com.askey.webservice.sdk.api.builder.auth.RegisterUserBuilder;
import tw.com.askey.webservice.sdk.api.response.auth.AccountUserResponse;
import tw.com.askey.webservice.sdk.exception.NotDefineException;
import tw.com.askey.webservice.sdk.helper.EMailLoginHelper;
import tw.com.askey.webservice.sdk.helper.FacebookLoginHelper;
import tw.com.askey.webservice.sdk.helper.LoginHelper;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.setting.auth.EMailLoginSource;
import tw.com.askey.webservice.sdk.setting.auth.FacebookLoginSource;
import tw.com.askey.webservice.sdk.setting.auth.LoginSource;
import tw.com.askey.webservice.sdk.task.GettingUserInfoTask;
import tw.com.askey.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/7/1.<br/>
 * <br/>
 * This is Askey Web Service SDK basic interface.<br/>
 * <br/>
 * This service is using like below:<br/>
 * <b>AskeyWebService.getInstance(getApplicationContext())<b/><br/>
 * The context must use Application Context(It's follow AWS Android SDK)<br/>
 * <br/>
 * At first, please get AWS using parameter by executing "active()".<br/>
 * <br/>
 */
public class AskeyWebService {

    private final Context context;

    private static AskeyWebService instance;
    private SNSSender snsSender;

    /**
     * @param context Please using Application Context.
     * @return
     */
    public static AskeyWebService getInstance(Context context) {
        if(instance == null){
            instance = new AskeyWebService(context);
        }
        return instance;
    }

    AskeyWebService(Context context) {
        this.context = context;
    }

    public AccountUserResponse registerUser(RegisterUserBuilder registerUserBuilder) {
        return AskeyCloudApiUtils.getInstance(ServiceConst.API_OAUTH_URL).
                registerUser(registerUserBuilder.genRegisterUserRequest());
    }

    public AccountUserResponse loginUser(LoginUserBuilder loginUserBuilder){
        return AskeyCloudApiUtils.getInstance(ServiceConst.API_OAUTH_URL).
                loginUser(loginUserBuilder.genLoginUserRequest());
    }

    /**
     * This function is for getting AWS parameter.<br/>
     * When AWS session and token expire time, also using this function to regain.
     * @param loginSource Login using data model, can see "LoginSource".
     * @param tag ServiceCallback callback tag. If null, the tag name will using GetCognitoTask's class name.
     * @param callback Use for AsyncTask callback.
     * @see LoginSource
     * @see FacebookLoginSource
     */
    public void active(FacebookLoginSource loginSource, String tag, ServiceCallback callback) {
        LoginHelper loginHelper = new FacebookLoginHelper(context, loginSource);
        activeAction(loginHelper, tag, callback);
    }

    /**
     * Don't use custom tag.
     * @param loginSource Login using data model, can see "LoginSource".
     * @param callback Use for AsyncTask callback.
     * @see LoginSource
     * @see FacebookLoginSource
     */
    public void active(FacebookLoginSource loginSource, ServiceCallback callback) {
        active(loginSource, null, callback);
    }

    public void active(EMailLoginSource loginSource, String tag, ServiceCallback callback){
        LoginHelper loginHelper = new EMailLoginHelper(context, loginSource);
        activeAction(loginHelper, tag, callback);
    }

    public void active(EMailLoginSource loginSource, ServiceCallback callback){
        active(loginSource, null, callback);
    }

    protected void activeAction(LoginHelper loginHelper, String tag, ServiceCallback callback){
        GettingUserInfoTask task = new GettingUserInfoTask(context, loginHelper);
        task.setCallback(callback);
        task.setTag(tag);
        task.execute();
    }

    public void configSNSSender(String apiKey) throws NotDefineException {
        if(ServicePreference.isCredentialsParamsExist(context) && apiKey != null){
            SharedPreferences preferences = ServicePreference.getServicePreference(context);
            String ak = preferences.getString(ServicePreference.AWS_ACCESS_KEY, null);
            String sk = preferences.getString(ServicePreference.AWS_SECRET_KEY, null);
            String st = preferences.getString(ServicePreference.AWS_SESSION_TOKEN, null);
            snsSender = new SNSSender(ak, sk, st, apiKey);
        }
        else{
            throw new NotDefineException(NotDefineException.ERR_CODE_MISSION_PARAMS, "Sns config missing parameters.");
        }
    }

    public void sendSNSMessage(final String deviceToken, final String appName, final String message)  {
        if(snsSender != null){
            Log.e(AskeyWebService.class.getName(), "Sns sender null, please config sns sender.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    snsSender.sendPushMessage(deviceToken, appName, message);
                }
            }).start();

        }
    }

    public String updateFile(String fileName, File updateFile){
        return null;
    }

    public void downloadFile(String fileName, File downloadFile) {
    }

}
