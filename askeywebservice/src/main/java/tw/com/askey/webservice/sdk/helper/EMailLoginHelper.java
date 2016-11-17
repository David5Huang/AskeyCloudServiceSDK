package tw.com.askey.webservice.sdk.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;

import tw.com.askey.webservice.sdk.ServiceConst;
import tw.com.askey.webservice.sdk.api.AskeyCloudApiUtils;
import tw.com.askey.webservice.sdk.api.builder.auth.LoginUserBuilder;
import tw.com.askey.webservice.sdk.api.builder.auth.RegisterUserBuilder;
import tw.com.askey.webservice.sdk.api.response.auth.AccountUserResponse;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.model.auth.BasicUserDataModel;
import tw.com.askey.webservice.sdk.setting.auth.EMailLoginSource;
import tw.com.askey.webservice.sdk.setting.auth.LoginSource;

/**
 * Created by david5_huang on 2016/11/16.
 */
public class EMailLoginHelper extends LoginHelper {
    public EMailLoginHelper(Context context, LoginSource loginSource) {
        super(context, loginSource);
    }

    @Override
    public BasicUserDataModel activeUserDataModel() {

        LoginUserBuilder loginUserBuilder = new LoginUserBuilder(
                getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_EMAIL),
                getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_PWD)
        );
        AccountUserResponse response = AskeyCloudApiUtils.getInstance(ServiceConst.API_OAUTH_URL).
                loginUser(loginUserBuilder.genLoginUserRequest());

        if(response == null){
            RegisterUserBuilder registerUserBuilder = new RegisterUserBuilder(
                    getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_EMAIL),
                    getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_PWD),
                    getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_DISPLAY_NAME),
                    getLoginSource().getLoginSource().get(EMailLoginSource.SOURCE_OTP)
            );
            response = AskeyCloudApiUtils.getInstance(ServiceConst.API_OAUTH_URL).
                    registerUser(registerUserBuilder.genRegisterUserRequest());
        }

        afterLogin(response);

        return genEMailLoginUserModel(response);
    }

    protected BasicUserDataModel genEMailLoginUserModel(AccountUserResponse response){
        BasicUserDataModel dataModel = new BasicUserDataModel();
        dataModel.setUserID(response.getUserid());
        return dataModel;
    }

    private void afterLogin(AccountUserResponse response){
        if(response != null){
            SharedPreferences preferences = ServicePreference.getServicePreference(context);
            preferences.edit().putString(ServicePreference.ASKEY_USER_ID, response.getUserid()).commit();
        }
    }

    @Override
    GetCredentialsForIdentityResult actGetCredentialsForIdentityResult(LoginSource loginSource, BasicUserDataModel dataModel) {
        return null;
    }
}
