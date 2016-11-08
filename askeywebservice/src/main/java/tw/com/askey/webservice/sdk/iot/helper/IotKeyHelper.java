package tw.com.askey.webservice.sdk.iot.helper;

import android.util.Log;

import com.amazonaws.mobileconnectors.iot.AWSIotKeystoreHelper;

import java.security.KeyStore;

/**
 * Created by david5_huang on 2016/8/29.
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
