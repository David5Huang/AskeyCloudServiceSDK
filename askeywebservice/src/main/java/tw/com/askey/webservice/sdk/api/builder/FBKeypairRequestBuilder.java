package tw.com.askey.webservice.sdk.api.builder;

import android.util.Log;

import tw.com.askey.webservice.sdk.api.request.GetKeypairRequest;
import tw.com.askey.webservice.sdk.setting.FacebookLoginSource;
import tw.com.askey.webservice.sdk.setting.LoginSource;

/**
 * Created by david5_huang on 2016/8/22.
 */
public class FBKeypairRequestBuilder extends GetKeypairRequestBuilder {

    private String fbToken;

    public FBKeypairRequestBuilder(String userId, String provider, String fbToken) {
        super(userId, provider);
        this.fbToken = fbToken;
    }

    public FBKeypairRequestBuilder(String userId, FacebookLoginSource loginSource) {
        super(userId, loginSource);
        this.fbToken = loginSource.getFbToken();
    }

    @Override
    GetKeypairRequest createKeypairRequest() {
        GetKeypairRequest request = new GetKeypairRequest();
        request.setUserid(getUserId());

        GetKeypairRequest.Oauth oauth = new GetKeypairRequest().new Oauth();
        oauth.setProvider(getProvider());

        GetKeypairRequest.Token token = new GetKeypairRequest().new Token();
        token.setAccess(fbToken);

        oauth.setToken(token);
        request.setOauth(oauth);

        return request;
    }
}
