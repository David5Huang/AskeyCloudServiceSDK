package com.askeycloud.webservice.sdk.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.askeycloud.sdk.auth.AskeyCloudAuthApiUtils;
import com.askeycloud.sdk.auth.response.DeviceOAuthCodeResponse;
import com.askeycloud.sdk.auth.response.OAuthProvider;
import com.askeycloud.sdk.core.api.ApiStatus;
import com.askeycloud.sdk.core.api.response.BasicResponse;
import com.askeycloud.webservice.R;
import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.model.auth.v3.DeviceProvidersQueryOptions;

/**
 * Created by david5_huang on 2017/8/24.
 */

public class QueryOAuthProviderUriTask extends AsyncTask<DeviceProvidersQueryOptions, Integer, BasicResponse[]> {

    protected final Context context;

    private String apiUrl;
    private String apiKey;
    private ServiceCallback serviceCallback;
    private String tag;

    private ProgressDialog progressDialog;

    public QueryOAuthProviderUriTask(Context context, String apiUrl, String apiKey){
        this.context = context;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Override
    protected BasicResponse[] doInBackground(DeviceProvidersQueryOptions... deviceProvidersQueryOptionses) {
        publishProgress(0);
        DeviceProvidersQueryOptions options = deviceProvidersQueryOptionses[0];

        DeviceOAuthCodeResponse codeResponse = AskeyCloudAuthApiUtils.getInstance(apiUrl).getDeviceOAuthCodeUri(
                apiKey,
                options.getDeviceAuthAppId(),
                options.getDeviceAuthUniqueId(),
                options.getDeviceModel()
        );

        Log.d(DebugConst.DEBUG_TAG, "confirm uri: "+codeResponse.getRestConfirmUri());

        if(codeResponse != null && codeResponse.getCode() == ApiStatus.API_SUCCESS){
            OAuthProvider[] providers = AskeyCloudAuthApiUtils.getInstance(apiUrl)
                    .getDeviceOAuthProviders(
                            codeResponse.getRestConfirmUri(),
                            apiKey,
                            options.getDeviceAuthAppId());

            Log.d(DebugConst.DEBUG_TAG, "auth uri: "+providers[0].getAuthorizeUri());

            publishProgress(100);
            return providers;
        }
        else{
            BasicResponse errResponse = codeResponse;
            if(errResponse == null){
                errResponse = new BasicResponse();
                errResponse.setCode(ApiStatus.API_UNKNOW_ERROR);
            }
            publishProgress(100);
            return new BasicResponse[]{errResponse};
        }
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
    protected void onPostExecute(BasicResponse[] oAuthProviders) {
        super.onPostExecute(oAuthProviders);
        if(tag != null && serviceCallback != null){
            if(oAuthProviders == null){
                BasicResponse errResponse = new BasicResponse();
                errResponse.setCode(ApiStatus.API_UNKNOW_ERROR);
                serviceCallback.error(tag, errResponse);
            }
            else{
                if(oAuthProviders[0].getCode() == 200){
                    serviceCallback.success(tag, oAuthProviders);
                }
                else{
                    serviceCallback.error(tag, oAuthProviders);
                }
            }
        }
    }

    public void setServiceCallback(String tag, ServiceCallback serviceCallback){
        this.tag = tag;
        this.serviceCallback = serviceCallback;
    }
}
