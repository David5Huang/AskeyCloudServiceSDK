package com.askeycloud.webservice.sdk.model.auth;

/**
 * User and device authorize will transfer to use OAuth version 3.
 */
@Deprecated
// If authorize can use Cognito, this class will use again.
public class CognitoDataModel extends BasicUserDataModel {
    private String identityID;
    private String accessKey;
    private String secretKey;
    private String sessionToken;

    public boolean isCanUseDataModel(){
        return identityID != null && accessKey != null && secretKey != null && sessionToken != null;
    }

    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

}
