package com.askeycloud.webservice.sdk.service.iot;

import android.content.Context;

import com.askeycloud.sdk.device.AskeyCloudDeviceApiUtils;
import com.askeycloud.webservice.sdk.ServiceConst;
import com.askeycloud.webservice.sdk.iot.callback.ShadowReceiveListener;
import com.askeycloud.webservice.sdk.iot.message.builder.MqttMsgBuilder;
import com.askeycloud.webservice.sdk.model.ServicePreference;
import com.askeycloud.webservice.sdk.service.BasicAskeyCloudService;

/**
 * This service is via REST API to control IoT device shadow.
 */

public abstract class AskeyIoTApiService extends BasicAskeyCloudService {

    private ShadowReceiveListener shadowReceiveListener;

    AskeyIoTApiService(Context context){
        super(context);
//        this.useUrl = ServiceConst.API_DEVICE_V3_URL;
        settingInitApiUrl(ServiceConst.API_DEVICE_META_NAME, ServiceConst.API_DEVICE_V3_URL);
    }

    public String publishApiThingShadow(String token, String deviceId, MqttMsgBuilder builder) {
        return AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTThingShadow(
                apiKey,
                token,
                deviceId,
                builder.buildMqttMessage());
    }

    /**
     * @param deviceId The device register ID in AskeyCloud service.
     * @param builder MQTT shadow message.
     * @return
     */
    public String publishApiThingShadow(String deviceId, MqttMsgBuilder builder) {
        return AskeyCloudDeviceApiUtils.getInstance(useApiUrl).updateIoTThingShadow(
                apiKey,
                ServicePreference.getAuthV3UserDataModel(context).getAccessToken(),
                deviceId,
                builder.buildMqttMessage());
    }

    public String readApiThingShadow(String token, String deviceId) {
        return AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTThingShadow(apiKey, token, deviceId);
    }

    /**
     * @param deviceId
     * @return
     * Shadow document string.
     */
    public String readApiThingShadow(String deviceId) {
        return AskeyCloudDeviceApiUtils.getInstance(useApiUrl).getIoTThingShadow(
                apiKey,
                ServicePreference.getAuthV3UserDataModel(context).getAccessToken(),
                deviceId);
    }

    public void setShadowReceiverListener(ShadowReceiveListener shadowReceiverListener){
        this.shadowReceiveListener = shadowReceiverListener;
    }

    public ShadowReceiveListener getShadowReceiverListener(){
        return shadowReceiveListener;
    }

}
