package tw.com.askey.webservice.sdk.model;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * Created by david5_huang on 2016/7/15.
 */
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