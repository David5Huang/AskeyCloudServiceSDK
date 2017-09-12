package com.askeycloud.webservice.sdk.model.auth.v3;

import java.io.Serializable;

/**
 * Created by david5_huang on 2017/4/12.
 */

public class ProviderQueryOptions implements Serializable {

    private String appScheme;
    private String appId;
    private String provider;
    private String state;

    public ProviderQueryOptions(String appScheme, String appId, String provider) {
        this.appScheme = appScheme;
        this.appId = appId;
        this.provider = provider;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAppScheme() {
        return appScheme;
    }

    public void setAppScheme(String appScheme) {
        this.appScheme = appScheme;
    }
}
