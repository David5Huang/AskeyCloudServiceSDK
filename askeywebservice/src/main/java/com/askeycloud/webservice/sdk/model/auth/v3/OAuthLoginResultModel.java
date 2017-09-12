package com.askeycloud.webservice.sdk.model.auth.v3;

import android.net.Uri;

import java.io.Serializable;

/**
 * User OAuth data model when authorized success.
 */

public class OAuthLoginResultModel implements Serializable {

    public static final String TAG_TOKEN_TYPE = "token_type";
    public static final String TAG_ACCESS_TOKEN = "access_token";
    public static final String TAG_REFRESH_TOKEN = "refresh_token";
    public static final String TAG_EXPIRE = "expire";

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String expire;

    public OAuthLoginResultModel(){}

    public OAuthLoginResultModel(Uri uri){
        String tokenType = uri.getQueryParameter(TAG_TOKEN_TYPE);
        this.setTokenType(tokenType);

        String accessToken = uri.getQueryParameter(TAG_ACCESS_TOKEN);
        this.setAccessToken(accessToken);

        String refreshToken = uri.getQueryParameter(TAG_REFRESH_TOKEN);
        this.setRefreshToken(refreshToken);

        String expire = uri.getQueryParameter(TAG_EXPIRE);
        this.setExpire(expire);
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * @return Access token is the must parameter when using AskeyCloud service.
     */
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return Via refresh token to get new access token when old access token expire.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return Access token expire time.
     */
    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
