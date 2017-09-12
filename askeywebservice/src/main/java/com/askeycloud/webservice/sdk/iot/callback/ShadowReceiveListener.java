package com.askeycloud.webservice.sdk.iot.callback;

/**
 * Created by david5_huang on 2017/2/6.
 */

public interface ShadowReceiveListener {
    abstract public void receiveShadowDocument(String tag, String data);
}
