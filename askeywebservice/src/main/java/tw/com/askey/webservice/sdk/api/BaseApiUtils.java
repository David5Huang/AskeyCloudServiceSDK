package tw.com.askey.webservice.sdk.api;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by david5_huang on 2016/7/27.
 */
class BaseApiUtils {

    protected static String url;

    protected Retrofit retrofit;
    protected AskeyCloudWebService webService;
    protected ObjectMapper mapper;

    BaseApiUtils(String url){
        BaseApiUtils.url = url;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new StringConverter())
                .client(okHttpClient)
                .build();
        webService = retrofit.create(AskeyCloudWebService.class);
        mapper = new ObjectMapper();
    }

    protected String convertRequestToJsonString(Object request){
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(request);
            Log.e("ia4test", "request str: "+jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    protected String doApi(Call<String> response){
        try {
            String result = response.execute().body();
            if(result != null){
                Log.e("ia4test", result);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected <T> T parseJson(String jsonStr, Class<T> object){
        if(jsonStr == null){
            return null;
        }
        try {
            T result = mapper.readValue(jsonStr, object);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
