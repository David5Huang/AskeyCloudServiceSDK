package com.askeycloud.webservice.sdk.model.auth.v3;

import java.io.Serializable;


/**
 * Get project support OAuth provider via this option.<br>
 */
public class DeviceProvidersQueryOptions implements Serializable {

    protected String deviceAuthAppId;
    protected String deviceAuthUniqueId;
    protected String deviceModel;

    public String getDeviceAuthAppId() {
        return deviceAuthAppId;
    }

    /**
     * @param deviceAuthAppId OAuth application ID, apply from AskeyCloud team.
     */
    public void setDeviceAuthAppId(String deviceAuthAppId) {
        this.deviceAuthAppId = deviceAuthAppId;
    }

    public String getDeviceAuthUniqueId() {
        return deviceAuthUniqueId;
    }

    /**
     * @param deviceAuthUniqueId Device unique ID, like IMEI, UUID, MAC etc..
     */
    public void setDeviceAuthUniqueId(String deviceAuthUniqueId) {
        this.deviceAuthUniqueId = deviceAuthUniqueId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * @param deviceModel Device model use in ASTS(Askey Smart Things Service).
     */
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}
