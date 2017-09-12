package com.askeycloud.webservice.sdk.model.auth;

/**
 * Created by david5_huang on 2016/11/16.
 */
public class BasicUserDataModel {
    private int statusCode;
    private String userID;
    private String token;

    public boolean isCanUseDataModel() {
        return userID != null;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
