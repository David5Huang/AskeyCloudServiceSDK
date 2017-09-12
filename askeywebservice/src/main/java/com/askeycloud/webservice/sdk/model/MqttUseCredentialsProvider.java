package com.askeycloud.webservice.sdk.model;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * MQTT now use certificate to connection.<br>
 */
// If authorize can use Cognito, this class will use again.
@Deprecated
public class MqttUseCredentialsProvider implements AWSCredentialsProvider {

    AWSCredentials credentials;

    public MqttUseCredentialsProvider(AWSCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public AWSCredentials getCredentials() {
        return credentials;
    }

    @Override
    public void refresh() {

    }
}