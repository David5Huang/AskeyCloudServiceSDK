package com.askeycloud.webservice.sdk.task;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.cognitosync.AmazonCognitoSync;
import com.amazonaws.services.cognitosync.AmazonCognitoSyncClient;

import com.askeycloud.webservice.sdk.model.auth.CognitoDataModel;
import com.askeycloud.webservice.sdk.model.ServicePreference;


/**
 * Created by david5_huang on 2016/8/16.
 */
abstract public class SyncPreferenceTask extends AsyncTask<Void, Integer, String> {

    protected final Context context;
    protected AmazonCognitoSync syncClient;
    private ServiceCallback serviceCallback;

    public SyncPreferenceTask(Context context){
        this.context = context;
        this.syncClient = createCognitoSyncClient();
    }

    protected AmazonCognitoSync createCognitoSyncClient(){
        if(ServicePreference.isCredentialsParamsExist(context)){
            return null;
        }
        else{
            CognitoDataModel dataModel = ServicePreference.getCognitoDataFromPreference(context);
            AmazonCognitoSync syncClient = new AmazonCognitoSyncClient(new BasicSessionCredentials(
                    dataModel.getAccessKey(),
                    dataModel.getSecretKey(),
                    dataModel.getSessionToken()));
            return syncClient;
        }
    }

    public ServiceCallback getServiceCallback() {
        return serviceCallback;
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }
}
