package com.askeycloud.webservice.sdk.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.askeycloud.webservice.sdk.model.auth.BasicUserDataModel;
import com.askeycloud.webservice.sdk.model.auth.CognitoDataModel;
import com.askeycloud.webservice.sdk.model.auth.v3.OAuthLoginResultModel;

/**
 * ServicePreference provider an interface to access user authorized data.
 */
public class ServicePreference {
    public static final String ASKEYCLOUD_PREF_NAME = "ia4_pref";
    public static final String AWS_ACCESS_KEY = "access_key";
    public static final String AWS_SECRET_KEY = "secret_key";
    public static final String AWS_SESSION_TOKEN = "session_token";
    public static final String AWS_IDENTITY_ID = "identity_id";

    public static final String ASKEYCLOUD_USER_ID = "user_id";

    public static final String ASKEYCLOUD_ACCESS_TOKEN = "askeycloud_access_token";
    public static final String ASKEYCLOUD_REFRESH_TOKEN = "akseycloud_refresh_token";
    public static final String ASKEYCLOUD_TOKEY_TYPE = "askeycloud_token_type";
    public static final String ASKEYCLOUD_EXRIRES_IN = "askeycloud_expires_in";

    public static SharedPreferences getServicePreference(Context context){
        return context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param context Android context.
     * @return Check this user is exist.
     */
    @Deprecated
    public static boolean isCredentialsParamsExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(AWS_ACCESS_KEY) && sharedPreferences.contains(AWS_SECRET_KEY) && sharedPreferences.contains(AWS_SESSION_TOKEN)){
            sharedPreferences.edit().remove(AWS_ACCESS_KEY).commit();
            sharedPreferences.edit().remove(AWS_SECRET_KEY).commit();
            sharedPreferences.edit().remove(AWS_SESSION_TOKEN).commit();
        }

        return false;
    }

    @Deprecated
    public static boolean isBasicUserParamsExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(ASKEYCLOUD_USER_ID) && sharedPreferences.contains(ASKEYCLOUD_ACCESS_TOKEN)){
            sharedPreferences.edit().remove(ASKEYCLOUD_USER_ID).commit();
            sharedPreferences.edit().remove(ASKEYCLOUD_ACCESS_TOKEN).commit();
        }

        return false;
    }

    @Deprecated
    public static CognitoDataModel getCognitoDataFromPreference(Context context) {
        return null;
    }

    @Deprecated
    public static BasicUserDataModel getBasicUserDataFromPreference(Context context){
        return null;
    }

    /**
     * Update user authorized data.
     * @param context Android context.
     * @param resultModel New OAuth authorized user data.
     */
    public static void updateAuthV3UserData(Context context, OAuthLoginResultModel resultModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(ASKEYCLOUD_ACCESS_TOKEN, resultModel.getAccessToken()).commit();
        sharedPreferences.edit().putString(ASKEYCLOUD_REFRESH_TOKEN, resultModel.getRefreshToken()).commit();
        sharedPreferences.edit().putString(ASKEYCLOUD_EXRIRES_IN, resultModel.getExpire()).commit();
        sharedPreferences.edit().putString(ASKEYCLOUD_TOKEY_TYPE, resultModel.getTokenType()).commit();
    }

    /**
     * Get authorized user data.
     * @param context Android context.
     * @return
     */
    public static OAuthLoginResultModel getAuthV3UserDataModel(Context context){
        if(isAuthV3UserDataExist(context)){
            SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);
            OAuthLoginResultModel model = new OAuthLoginResultModel();
            model.setAccessToken(sharedPreferences.getString(ASKEYCLOUD_ACCESS_TOKEN, null));
            model.setRefreshToken(sharedPreferences.getString(ASKEYCLOUD_REFRESH_TOKEN, null));
            model.setExpire(sharedPreferences.getString(ASKEYCLOUD_EXRIRES_IN, null));
            model.setTokenType(sharedPreferences.getString(ASKEYCLOUD_TOKEY_TYPE, null));

            return model;
        }
        else{
            return null;
        }
    }

    /**
     * Check has user login.
     * @param context Android context.
     * @return
     */
    public static boolean isAuthV3UserDataExist(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(ASKEYCLOUD_ACCESS_TOKEN) &&
                sharedPreferences.contains(ASKEYCLOUD_REFRESH_TOKEN);
    }

    /**
     * Remove user token from SharedPreferences.
     * @param context
     */
    public static void revokeToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ASKEYCLOUD_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(ASKEYCLOUD_ACCESS_TOKEN).commit();
        sharedPreferences.edit().remove(ASKEYCLOUD_REFRESH_TOKEN).commit();
        sharedPreferences.edit().remove(ASKEYCLOUD_EXRIRES_IN).commit();
        sharedPreferences.edit().remove(ASKEYCLOUD_TOKEY_TYPE).commit();
    }

}
