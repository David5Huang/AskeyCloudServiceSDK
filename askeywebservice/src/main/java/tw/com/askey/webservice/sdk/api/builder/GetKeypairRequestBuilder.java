package tw.com.askey.webservice.sdk.api.builder;

import tw.com.askey.webservice.sdk.api.request.GetKeypairRequest;
import tw.com.askey.webservice.sdk.setting.auth.LoginSource;

/**
 * Created by david5_huang on 2016/8/22.
 */
abstract public class GetKeypairRequestBuilder {
    private String userId;
    private String provider;

    protected GetKeypairRequest keypairRequest;

    public GetKeypairRequestBuilder(String userId, String provider){
        this.userId = userId;
        this.provider = provider;
    }

    public GetKeypairRequestBuilder(String userId, LoginSource loginSource){
        this.userId = userId;
        this.provider = loginSource.getProvider();
    }

    abstract GetKeypairRequest createKeypairRequest();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public GetKeypairRequest getKeypairRequest() {
        if(keypairRequest == null){
            keypairRequest = createKeypairRequest();
        }
        return keypairRequest;
    }

    public void setKeypairRequest(GetKeypairRequest keypairRequest) {
        this.keypairRequest = keypairRequest;
    }
}
