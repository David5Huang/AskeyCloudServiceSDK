package com.askeycloud.webservice.sdk.iot.callback;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;

/**
 * Created by david5_huang on 2016/7/25.<br/>
 * <br/>
 * Mqtt Manager 進行 connection 時所使用的 callback<br/>
 * 基本上是簡化 AWS SDK 的內容<br/>
 * connected 以外的狀態都會歸在 onMqttServiceConnectedError
 */
public interface MqttConnectionCallback {
    abstract public void onConnected();
    abstract public void unConnected(AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus status);
}
