package tw.com.askey.webservice.sdk.iot;

/**
 * Created by david5_huang on 2016/8/9.
 */
public class AskeyIoTUtils {
    public static String translatMqttUseEndpoint(String endpoint){
        if(!endpoint.contains("https://") && !endpoint.contains("/thing")){
            return endpoint;
        }
        else{
            try {
                endpoint = endpoint.replace("https://", "");
                endpoint = endpoint.substring(0, endpoint.indexOf("/thing"));
                return endpoint;
            }catch (Exception e){
                return endpoint;
            }
        }
    }
}
