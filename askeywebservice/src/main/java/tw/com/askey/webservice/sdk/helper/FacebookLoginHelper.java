package tw.com.askey.webservice.sdk.helper;

import android.content.Context;
import android.util.Log;

import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;

import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.request.GetLongTokenRequest;
import tw.com.askey.webservice.sdk.api.response.AddUserResponse;
import tw.com.askey.webservice.sdk.api.response.GetLongTokenResponse;
import tw.com.askey.webservice.sdk.api.response.GetUserResponse;
import tw.com.askey.webservice.sdk.model.CognitoDataModel;
import tw.com.askey.webservice.sdk.setting.FacebookLoginSource;
import tw.com.askey.webservice.sdk.setting.LoginSource;

/**
 * Created by david5_huang on 2016/8/23.
 */
public class FacebookLoginHelper extends LoginHelper {

    public FacebookLoginHelper(Context context, LoginSource loginSource) {
        super(context, loginSource);
    }

    @Override
    public CognitoDataModel activeCognitoDataModel() {
        try {
            CognitoDataModel dataModel = new CognitoDataModel();

            GetCredentialsForIdentityResult result = actGetCredentialsForIdentityResult(getLoginSource(), dataModel);

            combineCognitoDataModel(result, dataModel);

            updateAWSData(dataModel);
            return dataModel;
        } catch (Exception e){
            Log.e(getClass().getName(), e.getMessage());
            return null;
        }
    }

    @Override
    public GetCredentialsForIdentityResult actGetCredentialsForIdentityResult(LoginSource loginSource, CognitoDataModel dataModel) {
        GetCredentialsForIdentityResult result = null;
        GetUserResponse userResponse = checkUser(loginSource);
        if(userResponse == null || userResponse.getUser() == null){
            AddUserResponse addUserResponse = queryAddUserApi(loginSource);
            if(addUserResponse != null){

                dataModel.setIdentityID(addUserResponse.getUser().getCognitoidentityid());
                dataModel.setUserID(addUserResponse.getUser().getUserid());

                settingLongTokenToLoginSource(addUserResponse.getUser().getUserid(), loginSource);

                result = getCredentialsForIdentityResult(loginSource,
                        addUserResponse.getUser().getCognitoidentityid());
            }
        }
        else{
            dataModel.setIdentityID(userResponse.getUser().getCognitoidentityid());
            dataModel.setUserID(userResponse.getUser().getUserid());

            settingLongTokenToLoginSource(userResponse.getUser().getUserid(), loginSource);

            result = getCredentialsForIdentityResult(loginSource,
                    userResponse.getUser().getCognitoidentityid());
        }
        return result;
    }

    private String getFacebookLongToken(String userId, LoginSource loginSource){
        GetLongTokenRequest request = new GetLongTokenRequest();
        GetLongTokenRequest.Oauth oauth = new GetLongTokenRequest().new Oauth();
        GetLongTokenRequest.Token token = new GetLongTokenRequest().new Token();

        token.setAccess(((FacebookLoginSource)loginSource).getFbToken());
        oauth.setProvider(FacebookLoginSource.FB_PROVIDER);
        oauth.setToken(token);
        request.setOauth(oauth);
        request.setUserid(userId);

        GetLongTokenResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).queryLongToken(request);

        return response.getToken().getAccess();
    }

    private void settingLongTokenToLoginSource(String userId, LoginSource loginSource){
        String longToken = getFacebookLongToken(userId, loginSource);
        if(longToken != null){
            ((FacebookLoginSource)loginSource).setFbToken(longToken);
        }
    }
}
