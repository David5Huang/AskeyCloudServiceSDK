package askey.com.tw.authapi;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import com.askeycloud.webservice.sdk.service.web.AskeyWebService;
import com.askeycloud.webservice.sdk.setting.auth.FacebookLoginSource;
import com.askeycloud.webservice.sdk.task.ServiceCallback;

/**
 * Created by david5_huang on 2016/8/25.
 */
public class AuthHelper {

    private final Context context;

    CallbackManager callbackManager;
    FacebookLoginSource loginSource;
    String fbToken;

    public AuthHelper(Context context, FacebookCallback callback){
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, callback);
    }

    public void facebookLogin(){
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void getFacebookUserData(LoginResult loginResult, GraphRequest.GraphJSONObjectCallback callback){
        fbToken = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), callback);
        Bundle b = new Bundle();
        b.putString("fields", "id,name,email");
        request.setParameters(b);
        request.executeAsync();
    }

    public void activeAskeyUser(String name, String id, String mail, ServiceCallback callback){
        loginSource = new FacebookLoginSource(name,
                id,
                mail,
                fbToken);

        AskeyWebService.getInstance(context.getApplicationContext()).active(loginSource, "ia4register", callback);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
