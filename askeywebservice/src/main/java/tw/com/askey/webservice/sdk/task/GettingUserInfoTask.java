package tw.com.askey.webservice.sdk.task;

import android.content.Context;
import android.os.AsyncTask;

import tw.com.askey.webservice.sdk.helper.LoginHelper;
import tw.com.askey.webservice.sdk.model.auth.BasicUserDataModel;
import tw.com.askey.webservice.sdk.model.auth.CognitoDataModel;

/**
 * Created by david5_huang on 2016/7/5.
 */
public class GettingUserInfoTask extends AsyncTask<Void, Integer, BasicUserDataModel> {

    private final Context context;

    private ServiceCallback callback;
    private String tag;

    protected LoginHelper loginHelper;

    public GettingUserInfoTask(Context context, LoginHelper loginHelper) {
        this.context = context;
        this.loginHelper = loginHelper;
    }

    @Override
    protected BasicUserDataModel doInBackground(Void... params) {
//        CognitoDataModel dataModel = (CognitoDataModel) loginHelper.activeUserDataModel();
        BasicUserDataModel dataModel = loginHelper.activeUserDataModel();
        return dataModel;
    }

    @Override
    protected void onPostExecute(BasicUserDataModel cognitoDataModel) {
        super.onPostExecute(cognitoDataModel);
        if(callback == null){
            return;
        }
        if(cognitoDataModel != null){
            if(cognitoDataModel.isCanUseDataModel()){
                callback.success(useTag(), cognitoDataModel);
            }
            else{
                callback.problem(useTag(), null);
            }
        }
        else{
            callback.error(useTag(), null);
        }
    }



    private String useTag(){
        return tag == null?this.getClass().getName():tag;
    }

    public void setCallback(ServiceCallback callback) {
        this.callback = callback;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
