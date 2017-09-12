package com.askeycloud.webservice.sdk.iot.callback;

/**
 * Created by david5_huang on 2016/7/25.<br/>
 * <br/>
 * MqttService connection 後所使用的 callback<br/>
 */
public interface MqttServiceConnectedCallback {
    abstract public void onMqttServiceConnectedSuccess();
    abstract public void onMqttServiceConnectedError();
}
