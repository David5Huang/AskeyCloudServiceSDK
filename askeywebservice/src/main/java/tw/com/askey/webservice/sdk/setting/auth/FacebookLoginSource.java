package tw.com.askey.webservice.sdk.setting.auth;

import java.util.HashMap;
import java.util.Map;

import tw.com.askey.webservice.sdk.api.request.AddUserRequest;
import tw.com.askey.webservice.sdk.api.request.GetUserRequest;

/**
 * Created by david5_huang on 2016/7/5.
 */
public class FacebookLoginSource extends LoginSource {

    public static final String FB_PROVIDER = "graph.facebook.com";

    private String fbToken;

    public FacebookLoginSource(String displayName, String providerUid, String providerMail, String token){
        super(displayName, providerMail, providerUid, providerMail);
        this.fbToken = token;
        this.provider = FB_PROVIDER;
    }

    @Override
    public Map<String, String> getLoginSource() {
        Map<String, String> logins = new HashMap<String, String>();
        logins.put(provider, fbToken);
        return logins;
    }

    @Override
    public AddUserRequest genAddUserRequest() {
        AddUserRequest request = new AddUserRequest();
        request.setDisplayname(displayName);
        request.setEmail(email);

        AddUserRequest.Oauth oauth = new AddUserRequest().new Oauth();
        oauth.setProvider(provider);
        oauth.setProviderUid(providerUid);
        oauth.setProviderEmail(providerMail);

        AddUserRequest.Token token = new AddUserRequest().new Token();
        token.setAdditionalProperty("access", fbToken);

        oauth.setToken(token);

        request.setOauth(oauth);
        return request;
    }

    @Override
    public GetUserRequest genGetUserRequest() {
        GetUserRequest request = new GetUserRequest();
        request.setProvider(FB_PROVIDER);

        GetUserRequest.Token token = new GetUserRequest().new Token();
        token.setAdditionalProperty("access", fbToken);

        request.setToken(token);

        return request;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderMail() {
        return providerMail;
    }

    public void setProviderMail(String providerMail) {
        this.providerMail = providerMail;
    }
}
