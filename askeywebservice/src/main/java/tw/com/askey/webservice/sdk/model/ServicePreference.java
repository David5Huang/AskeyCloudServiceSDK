package tw.com.askey.webservice.sdk.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david5_huang on 2016/7/11.
 */
public class ServicePreference {
    public static final String IA4_PREF_NAME = "ia4_pref";
    public static final String AWS_ACCESS_KEY = "access_key";
    public static final String AWS_SECRET_KEY = "secret_key";
    public static final String AWS_SESSION_TOKEN = "session_token";
    public static final String AWS_IDENTITY_ID = "identity_id";

    public static final String ASKEY_USER_ID = "user_id";

    public static SharedPreferences getServicePreference(Context context){
        return context.getSharedPreferences(IA4_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isCredentialsParamsExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(IA4_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(AWS_ACCESS_KEY) && sharedPreferences.contains(AWS_SECRET_KEY) && sharedPreferences.contains(AWS_SESSION_TOKEN);
    }

    public static CognitoDataModel getCognitoDataFromPreference(Context context) {
        if(isCredentialsParamsExist(context)){
            SharedPreferences sharedPreferences = ServicePreference.getServicePreference(context);
            CognitoDataModel dataModel = new CognitoDataModel();
            dataModel.setAccessKey(sharedPreferences.getString(AWS_ACCESS_KEY, null));
            dataModel.setSecretKey(sharedPreferences.getString(AWS_SECRET_KEY, null));
            dataModel.setSessionToken(sharedPreferences.getString(AWS_SESSION_TOKEN, null));
            dataModel.setIdentityID(sharedPreferences.getString(AWS_IDENTITY_ID, null));
            dataModel.setUserID(sharedPreferences.getString(ASKEY_USER_ID, null));
            return dataModel;
        }
        else{
            return null;
        }
    }

}
