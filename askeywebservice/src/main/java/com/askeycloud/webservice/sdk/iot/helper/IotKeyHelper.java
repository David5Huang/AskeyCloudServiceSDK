package com.askeycloud.webservice.sdk.iot.helper;

import android.util.Log;

import com.amazonaws.mobileconnectors.iot.AWSIotKeystoreHelper;

import java.security.KeyStore;

/**
 * Create the keystore file that used on MQTT server connection.<br/>
 * The keystore file name is from developer, if the name exist, this new keystore will not creation.
 */
public class IotKeyHelper {
    public final String KEYSTORE_ALIAS = "askey_iot_service";
    public final String KEYSTORE_PW = "askey_iot_service_pw";
    public final String KEYSTORE_NAME = "askey_iot_service_key_";

    private KeyStore clientKeyStore;
    private String keyStorePath;

    public IotKeyHelper(String keyStorePath){
        this.keyStorePath = keyStorePath;
    }

    public void manageKeyStore(String userName, String cert, String pk){
        if(clientKeyStore == null){
            if(AWSIotKeystoreHelper.isKeystorePresent(keyStorePath, KEYSTORE_NAME+userName)){
                Log.e("ia4test", "key exist");
                clientKeyStore = getAWSIotKeyStore(userName);
            }
            else{
                Log.e("ia4test", "create key");
                AWSIotKeystoreHelper.saveCertificateAndPrivateKey(
                        KEYSTORE_ALIAS,
                        cert,
                        pk,
                        keyStorePath,
                        KEYSTORE_NAME+userName,
                        KEYSTORE_PW
                );
                clientKeyStore = getAWSIotKeyStore(userName);
            }
        }
    }

    private KeyStore getAWSIotKeyStore(String userName){
        KeyStore keyStore = AWSIotKeystoreHelper.getIotKeystore(
                KEYSTORE_ALIAS,
                keyStorePath,
                KEYSTORE_NAME+userName,
                KEYSTORE_PW
        );
        return keyStore;
    }

    public boolean isClientKeystoreExist(){
        return clientKeyStore != null;
    }

    public KeyStore getClientKeyStore() {
        return clientKeyStore;
    }
}
