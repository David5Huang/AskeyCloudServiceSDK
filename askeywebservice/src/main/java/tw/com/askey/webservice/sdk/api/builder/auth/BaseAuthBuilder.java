package tw.com.askey.webservice.sdk.api.builder.auth;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by david5_huang on 2016/11/8.
 */
abstract public class BaseAuthBuilder {

    protected String convertPwd2Passcode(String pwd){
        String passcode = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] codeBytes = digest.digest(pwd.getBytes("UTF8"));

            return byte2Hex(codeBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passcode;
    }

    private String byte2Hex(byte[] data) {
        String hexString = "";
        String stmp = "";

        for(int i = 0; i < data.length; i++) {
            stmp = Integer.toHexString(data[i] & 0XFF);

            if(stmp.length() == 1) {
                hexString = hexString + "0" + stmp;
            }
            else {
                hexString = hexString + stmp;
            }
        }
        return hexString;
    }
}
