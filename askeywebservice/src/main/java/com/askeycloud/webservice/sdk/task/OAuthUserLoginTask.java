package com.askeycloud.webservice.sdk.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.askeycloud.sdk.auth.response.OAuthProviderResponse;
import com.askeycloud.webservice.R;
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.model.auth.v3.ProviderQueryOptions;
import com.askeycloud.webservice.sdk.service.web.AskeyWebService;
import com.askeycloud.webservice.sdk.view.OAuthLoginDialogBuilder;

/**
 * Created by david5_huang on 2017/6/21.
 */

public class OAuthUserLoginTask extends AsyncTask<ProviderQueryOptions, Integer, OAuthProviderResponse> {

    private final Context context;
    private final String authUrl;

    private ServiceCallback serviceCallback;
    private String serviceTag;
    private String appScheme;

    private ProgressDialog progressDialog;

    public OAuthUserLoginTask(Context context, String authUrl){
        this.context = context;
        this.authUrl = authUrl;
    }

    @Override
    protected OAuthProviderResponse doInBackground(ProviderQueryOptions... providerQueryOptionses) {
        publishProgress(0);
        ProviderQueryOptions model = providerQueryOptionses[0];
        this.appScheme = model.getAppScheme();

        OAuthProviderResponse response = AskeyWebService.getInstance(context).login2AuthProvider(authUrl, model);

        publishProgress(100);

        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(values[0] == 0){
            if(progressDialog == null){
                progressDialog =
                        ProgressDialog.show(
                                context,
                                context.getString(R.string.sdk_dialog_title),
                                context.getString(R.string.sdk_dialog_progress));
            }
        }
        else{
            if(progressDialog != null){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                progressDialog = null;
            }
        }
    }

    @Override
    protected void onPostExecute(OAuthProviderResponse response) {
        super.onPostExecute(response);
        if(response != null && response.getCode() == 200){
            OAuthLoginDialogBuilder dialogBuilder = new OAuthLoginDialogBuilder(context);
            dialogBuilder.setServiceCallback(serviceCallback);
            dialogBuilder.setServiceTag(serviceTag);
            dialogBuilder.setAppScheme(appScheme);
            dialogBuilder.buildOAuthDialog(response.getUri());
        }
        else{
            Log.e(DebugConst.DEBUG_TAG, "api url: "+authUrl);
            Log.e(DebugConst.DEBUG_TAG, response.getCode()+": "+response.getMessage());
        }
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }
}
