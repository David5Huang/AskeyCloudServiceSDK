package com.askeycloud.webservice.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.askeycloud.webservice.R;

/**
 * Created by david5_huang on 2017/6/21.
 */

abstract public class AskeyCloudBasicWebViewDialogBuilder {
    private final Context context;
    protected Dialog authDialog;

    protected WebViewClient authWebViewClient;

    public AskeyCloudBasicWebViewDialogBuilder(Context context){
        this.context = context;
    }

    public void buildOAuthDialog(String authUrl){
        if(authDialog == null || !authDialog.isShowing()){
            authDialog = new Dialog(context);

            authDialog.setContentView(R.layout.askeycloud_oauth_web_layout);

            WebView webView = (WebView) authDialog.findViewById(R.id.oauth_webview);
            settingAuthWebView(webView);
            webView.loadUrl(authUrl);
//            webView.loadData(authUrl, "text/html; charset=utf-8", "base64");

//            webView.loadUrl("https://auis.askeycloudapi.com/v1/user/authorize?access_type=offline&scope=id%2Cdisplayname%2Cemail&response_type=code&redirect_uri=https%3A%2F%2Faoas.askeycloudapi.com%2Fv1%2Foauth%2Fauthprovider%2Fcallback&state=MTkyMzQ3Ny4zODA2Mzg2NDAxOmFza2V5OmFza2V5LmFza2V5Y2xvdWQuZGF2aWQuYXBwOmQuZWExYmExM2RlNDVhMTAwYzRjMDcyMjBmNjUxMzE5YTA&client_id=VN2CB4FIFPB3VGNEFFI32TR5CUAAAAK6CMGGFSAAAHUXMQUP7ONQ.auis.askeycloud.com");

            authDialog.show();

            Window window = authDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public WebViewClient getAuthWebViewClient() {
        return authWebViewClient;
    }

    public void setAuthWebViewClient(WebViewClient authWebViewClient) {
        this.authWebViewClient = authWebViewClient;
    }

    protected void settingAuthWebView(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        if(authWebViewClient != null){
            webView.setWebViewClient(authWebViewClient);
        }
    }

    protected void dismissDialog(){
        if(authDialog != null && authDialog.isShowing()){
            authDialog.dismiss();
        }
    }
}
