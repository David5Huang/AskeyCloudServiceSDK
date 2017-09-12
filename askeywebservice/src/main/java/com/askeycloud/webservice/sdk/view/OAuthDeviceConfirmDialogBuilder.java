package com.askeycloud.webservice.sdk.view;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.askeycloud.sdk.auth.response.OAuthProvider;
import com.askeycloud.webservice.sdk.ServiceConst;

/**
 * Device binding dialog builder.<br>
 * This Dialog will embed a webview to show login page.
 */

public class OAuthDeviceConfirmDialogBuilder extends AskeyCloudBasicWebViewDialogBuilder {

    private static OAuthDeviceConfirmDialogBuilder instance;

    public static OAuthDeviceConfirmDialogBuilder build(Context context){
        if(instance == null){
            instance = new OAuthDeviceConfirmDialogBuilder(context);
        }
        return instance;
    }

    OAuthDeviceConfirmDialogBuilder(Context context){
        super(context);
        setAuthWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("ia4test", "on page finished url: "+url);
                Uri uri = Uri.parse(url);
                if(uri.getHost().equals(ServiceConst.API_DEVICE_OAUTH_HOST)){
                    dismissDialog();
                }
            }
        });
    }

    public void showDialog(OAuthProvider provider){
        buildOAuthDialog(provider.getAuthorizeUri());
    }

    public void showDialog(String url){
        buildOAuthDialog(url);
    }
}
