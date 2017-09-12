package com.askeycloud.webservice.sdk.view;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.model.auth.v3.OAuthLoginResultModel;
import com.askeycloud.webservice.sdk.task.ServiceCallback;

/**
 * User login/register dialog builder.<br>
 * This Dialog will embed a webview to show login page.
 */

public class OAuthLoginDialogBuilder extends AskeyCloudBasicWebViewDialogBuilder {

    public static final String OAUTH_USER_LOGIN = "oauth_user_login";

    private String appScheme;
    private ServiceCallback serviceCallback;
    private String serviceTag;

    public OAuthLoginDialogBuilder(final Context context) {
        super(context);
        setAuthWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Uri uri = Uri.parse(url);
                Log.d(DebugConst.DEBUG_TAG, "uri: "+uri.toString());
                Log.d(DebugConst.DEBUG_TAG, "scheme: "+uri.getScheme());
                if(appScheme != null && uri.getScheme().equals(appScheme)){
                    OAuthLoginResultModel loginResult = genLoginResult(uri);
                    if(serviceCallback != null){
                        if(loginResult != null){
                            Log.d(DebugConst.DEBUG_TAG, "token type: "+loginResult.getTokenType());

                            ServicePreference.updateAuthV3UserData(context, loginResult);
                            serviceCallback.success(genServiceTag(), loginResult);

                            dismissDialog();
                        }
                        else {
                            serviceCallback.error(genServiceTag(), "OAuth user login error.");
                        }
                    }
                }
            }
        });
    }

    public String genServiceTag(){
        return serviceTag == null?OAUTH_USER_LOGIN:serviceTag;
    }

    private OAuthLoginResultModel genLoginResult(Uri uri){
        OAuthLoginResultModel loginResult = new OAuthLoginResultModel(uri);
        return loginResult;
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public ServiceCallback getServiceCallback() {
        return serviceCallback;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public String getAppScheme() {
        return appScheme;
    }

    public void setAppScheme(String appScheme) {
        this.appScheme = appScheme;
    }
}
