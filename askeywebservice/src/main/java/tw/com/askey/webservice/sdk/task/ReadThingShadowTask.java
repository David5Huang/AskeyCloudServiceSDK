package tw.com.askey.webservice.sdk.task;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.iotdata.model.GetThingShadowRequest;
import com.amazonaws.services.iotdata.model.GetThingShadowResult;
import com.amazonaws.services.iotdata.model.ResourceNotFoundException;

import tw.com.askey.webservice.sdk.model.CognitoDataModel;
import tw.com.askey.webservice.sdk.model.ServicePreference;
import tw.com.askey.webservice.sdk.iot.callback.ReadThingShadowCallback;

/**
 * Created by david5_huang on 2016/7/29.
 */
public class ReadThingShadowTask extends AsyncTask<String, Integer, String> {

    private final Context context;
    private ReadThingShadowCallback readThingShadowCallback;

    public ReadThingShadowTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String endpoint = strings[0];
        String thingName = strings[1];

        CognitoDataModel model = ServicePreference.getCognitoDataFromPreference(context);

        AWSIotDataClient dataClient = new AWSIotDataClient(new BasicSessionCredentials(
                model.getAccessKey(),
                model.getSecretKey(),
                model.getSessionToken()));

        dataClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        dataClient.setEndpoint(endpoint);

        return readShadow(thingName, dataClient);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(readThingShadowCallback != null){
            readThingShadowCallback.getThingShadow(s);
        }
    }

    private String readShadow(String thingName, AWSIotDataClient dataClient){
        GetThingShadowRequest request = new GetThingShadowRequest();
        request.setThingName(thingName);
        try {
            GetThingShadowResult result = dataClient.getThingShadow(request);
            byte[] bytes = new byte[result.getPayload().remaining()];
            result.getPayload().get(bytes);
            String resultString = new String(bytes);
            return resultString;
        }catch(ResourceNotFoundException e){
            return "";
        }
    }

    public void setReadThingShadowCallback(ReadThingShadowCallback readThingShadowCallback) {
        this.readThingShadowCallback = readThingShadowCallback;
    }
}
