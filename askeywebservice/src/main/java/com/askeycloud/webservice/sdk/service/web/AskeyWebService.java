package com.askeycloud.webservice.sdk.service.web;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;

import com.askeycloud.sdk.auth.AskeyCloudAuthApiUtils;
import com.askeycloud.sdk.auth.response.OAuthProviderResponse;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.model.auth.v3.DeviceProvidersQueryOptions;
import com.askeycloud.webservice.sdk.model.auth.v3.ProviderQueryOptions;
import com.askeycloud.webservice.sdk.task.OAuthUserLoginTask;
import com.askeycloud.webservice.sdk.task.QueryOAuthProviderUriTask;
import com.askeycloud.webservice.sdk.task.ServiceCallback;
import com.askeycloud.webservice.sdk.view.OAuthLoginDialogBuilder;

/**
 * This service is use to user authorize and device authorize.
 */
public class AskeyWebService extends BasicAskeyWebService {

    private static AskeyWebService instance;
    /**
     * @param context
     * @return
     */
    public static AskeyWebService getInstance(Context context) {
        if(instance == null){
            instance = new AskeyWebService(context);
        }
        else{
            if(!instance.context.equals(context)){
                instance = new AskeyWebService(context);
            }
        }
        return instance;
    }

    AskeyWebService(Context context) {
        super(context);
    }

    /**
     * @param providerQueryOptions The provider options for active this user.
     * @param callback When login task complete, via this callback to return result.
     * @param tag Task tag. Use this tag to get specific task result.
     */
    public void activeV3(ProviderQueryOptions providerQueryOptions, ServiceCallback callback, String tag){
        OAuthUserLoginTask task = new OAuthUserLoginTask(context, useApiUrl);
        task.setServiceCallback(callback);
        task.setServiceTag(tag);
        task.execute(providerQueryOptions);
    }

    /**
     * This function input null tag, and return result will use the tag OAuthLoginDialogBuilder.OAUTH_USER_LOGIN .
     * @param providerQueryOptions The provider options for active this user.
     * @param callback When login task complete, via this callback to return result.
     */
    public void activeV3(ProviderQueryOptions providerQueryOptions, ServiceCallback callback){
        activeV3(providerQueryOptions, callback, null);
    }

    /**
     * @param authUri Provider auth uri, open via SDK.
     * @param appScheme OAuth result catch uri.
     * @param callback Task result callback.
     * @param tag Task callback data tag.
     */
    public void activeDeviceV3(String authUri, String appScheme, ServiceCallback callback, String tag){
        OAuthLoginDialogBuilder dialogBuilder = new OAuthLoginDialogBuilder(context);
        dialogBuilder.setServiceCallback(callback);
        dialogBuilder.setServiceTag(tag);
        dialogBuilder.setAppScheme(appScheme);
        dialogBuilder.buildOAuthDialog(authUri);
    }

    /**
     * @param context Android context.
     * @param queryOptions {@link DeviceProvidersQueryOptions}
     * @param tag Login/Register task callback data tag.
     * @param callback Login/Register task result callback.
     */
    public void queryDeviceProviders(Context context, DeviceProvidersQueryOptions queryOptions, String tag, ServiceCallback callback){
        QueryOAuthProviderUriTask uriTask = new QueryOAuthProviderUriTask(context, useApiUrl, apiKey);
        uriTask.setServiceCallback(tag, callback);
        uriTask.execute(queryOptions);
    }

    /**
     * Remove login cookie and all user data.
     */
    public void revoke(){
        ServicePreference.revokeToken(context);

        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {}
            });
        }
        else{
            cookieManager.removeAllCookie();
        }

    }

    public OAuthProviderResponse login2AuthProvider(String authUrl, ProviderQueryOptions model){
        OAuthProviderResponse response =AskeyCloudAuthApiUtils.getInstance(authUrl).authProvider(
                apiKey,
                model.getAppId(),
                model.getProvider(),
                model.getState());
        return response;
    }

}
