package com.askeycloud.webservice.sdk.task;

import android.os.AsyncTask;

import com.askeycloud.sdk.auth.AskeyCloudAuthApiUtils;
import com.askeycloud.sdk.auth.response.OAuthProvider;


/**
 * Created by david5_huang on 2017/6/20.
 */
@Deprecated
public class GetOAuthProviderTask extends AsyncTask<String, Integer, OAuthProvider[]> {

    protected ServiceCallback serviceCallback;
    protected String tag;

    @Override
    protected OAuthProvider[] doInBackground(String... strings) {
        String restConfirmUrl = strings[0];

        OAuthProvider[] oAuthProviders = AskeyCloudAuthApiUtils
                .getInstance("https://localhost.com/").getDeviceOAuthProviders(restConfirmUrl, null, null);

        if(oAuthProviders != null){
            return oAuthProviders;
        }
        else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(OAuthProvider[] oAuthProviders) {
        super.onPostExecute(oAuthProviders);
        if(serviceCallback != null){
            if(oAuthProviders != null){
                serviceCallback.success(useTag(), oAuthProviders);
            }
            else{
                serviceCallback.error(useTag(), "Get user OAuth provider error.");
            }
        }
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    private String useTag(){
        return tag == null?this.getClass().getName():tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
