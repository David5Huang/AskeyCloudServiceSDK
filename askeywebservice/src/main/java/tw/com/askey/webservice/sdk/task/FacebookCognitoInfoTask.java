package tw.com.askey.webservice.sdk.task;

import android.content.Context;

import tw.com.askey.webservice.sdk.helper.LoginHelper;

/**
 * Created by david5_huang on 2016/8/10.
 */
abstract public class FacebookCognitoInfoTask extends GetCognitoInfoTask {

    public FacebookCognitoInfoTask(Context context, LoginHelper loginHelper) {
        super(context, loginHelper);
    }

//    public FacebookCognitoInfoTask(Context context, FacebookLoginSource loginSource) {
//        super(context, loginSource);
//    }
//
//    @Override
//    public GetCredentialsForIdentityResult actGetCredentialsForIdentityResult(LoginSource loginSource, CognitoDataModel dataModel) {
//        GetCredentialsForIdentityResult result = null;
//        GetUserResponse userResponse = checkUser(loginSource);
//        if(userResponse == null || userResponse.getUser() == null){
//            Log.e("ia4test", "get user null");
//            AddUserResponse addUserResponse = queryAddUserApi(loginSource);
//            if(addUserResponse != null){
//
//                dataModel.setIdentityID(addUserResponse.getUser().getCognitoidentityid());
//                dataModel.setUserID(addUserResponse.getUser().getUserid());
//
////                settingLongTokenToLoginSource(addUserResponse.getUser().getUserid(), loginSource);
//
//                result = getCredentialsForIdentityResult(loginSource,
//                        addUserResponse.getUser().getCognitoidentityid());
//            }
//        }
//        else{
//            Log.e("ia4test", "get user not null");
//            dataModel.setIdentityID(userResponse.getUser().getCognitoidentityid());
//            dataModel.setUserID(userResponse.getUser().getUserid());
//
////            settingLongTokenToLoginSource(userResponse.getUser().getUserid(), loginSource);
//
//            result = getCredentialsForIdentityResult(loginSource,
//                    userResponse.getUser().getCognitoidentityid());
//        }
//        return result;
//    }
//
//    private String getFacebookLongToken(String userId, LoginSource loginSource){
//        GetLongTokenRequest request = new GetLongTokenRequest();
//        GetLongTokenRequest.Oauth oauth = new GetLongTokenRequest().new Oauth();
//        GetLongTokenRequest.Token token = new GetLongTokenRequest().new Token();
//
//        token.setAccess(((FacebookLoginSource)loginSource).getFbToken());
//        oauth.setProvider(FacebookLoginSource.FB_PROVIDER);
//        oauth.setToken(token);
//        request.setOauth(oauth);
//        request.setUserid(userId);
//
//        GetLongTokenResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_URL).queryLongToken(request);
//
//        return response.getToken().getAccess();
//    }
//
//    private void settingLongTokenToLoginSource(String userId, LoginSource loginSource){
//        String longToken = getFacebookLongToken(userId, loginSource);
//        if(longToken != null){
//            ((FacebookLoginSource)loginSource).setFbToken(longToken);
//        }
//    }

}
