package tw.com.askey.webservice.sdk.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;

import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.response.AddUserResponse;
import tw.com.askey.webservice.sdk.api.response.GetUserResponse;
import tw.com.askey.webservice.sdk.model.CognitoDataModel;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.setting.LoginSource;

/**
 * Created by david5_huang on 2016/8/23.
 */
abstract public class LoginHelper {

    protected final Context context;
    private LoginSource loginSource;

    public LoginHelper(Context context, LoginSource loginSource){
        this.context = context;
        this.loginSource = loginSource;
    }

    protected GetUserResponse checkUser(LoginSource loginSource){
        return AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).queryGetUser(loginSource.genGetUserRequest());
    }

    protected AddUserResponse queryAddUserApi(LoginSource loginSource){
        return AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).queryAddUser(loginSource.genAddUserRequest());
    }

    protected GetCredentialsForIdentityResult getCredentialsForIdentityResult(LoginSource loginSource, String identityId){
        GetCredentialsForIdentityRequest identityRequest = new GetCredentialsForIdentityRequest();
        identityRequest.setLogins(loginSource.getLoginSource());
        identityRequest.setIdentityId(identityId);

        AmazonCognitoIdentityClient identityClient = new AmazonCognitoIdentityClient(new AnonymousAWSCredentials());
        GetCredentialsForIdentityResult result = identityClient.getCredentialsForIdentity(identityRequest);
        return result;
    }

    protected CognitoDataModel combineCognitoDataModel(GetCredentialsForIdentityResult result, CognitoDataModel dataModel){
        if(result == null){
            return dataModel;
        }
        Log.e("ia4test", "expire time: "+result.getCredentials().getExpiration().toString());
        dataModel.setAccessKey(result.getCredentials().getAccessKeyId());
        dataModel.setSecretKey(result.getCredentials().getSecretKey());
        dataModel.setSessionToken(result.getCredentials().getSessionToken());

        return dataModel;
    }

    protected void updateAWSData(CognitoDataModel dataModel){
        SharedPreferences preferences = ServicePreference.getServicePreference(context);
        preferences.edit().putString(ServicePreference.AWS_ACCESS_KEY, dataModel.getAccessKey()).commit();
        preferences.edit().putString(ServicePreference.AWS_SECRET_KEY, dataModel.getSecretKey()).commit();
        preferences.edit().putString(ServicePreference.AWS_SESSION_TOKEN, dataModel.getSessionToken()).commit();
        preferences.edit().putString(ServicePreference.AWS_IDENTITY_ID, dataModel.getIdentityID()).commit();
        preferences.edit().putString(ServicePreference.ASKEY_USER_ID, dataModel.getUserID()).commit();

    }

    public void setLoginSource(LoginSource loginSource) {
        this.loginSource = loginSource;
    }

    public LoginSource getLoginSource() {
        return loginSource;
    }

    abstract public CognitoDataModel activeCognitoDataModel();
    abstract GetCredentialsForIdentityResult actGetCredentialsForIdentityResult(LoginSource loginSource, CognitoDataModel dataModel);
}
