package tw.com.askey.webservice.sdk.setting.auth;

import java.util.HashMap;
import java.util.Map;

import tw.com.askey.webservice.sdk.api.request.AddUserRequest;
import tw.com.askey.webservice.sdk.api.request.GetUserRequest;

/**
 * Created by david5_huang on 2016/11/16.
 */
public class EMailLoginSource extends LoginSource {

    public static final String SOURCE_DISPLAY_NAME = "display_name";
    public static final String SOURCE_EMAIL = "email";
    public static final String SOURCE_PWD = "pwd";
    public static final String SOURCE_OTP = "otp";

    private String pwd;
    private String OTP;

    public EMailLoginSource(String displayName, String email, String pwd, String OTP) {
        super(displayName, email, null, null);
        this.pwd = pwd;
        this.OTP = OTP;
    }

    public EMailLoginSource(String email, String pwd, String OTP) {
        super(null, email, null, null);
        this.pwd = pwd;
        this.OTP = OTP;
    }

    public EMailLoginSource(String email, String pwd) {
        super(null, email, null, null);
        this.pwd = pwd;
    }

    @Override
    public Map<String, String> getLoginSource() {
        Map<String, String> logins = new HashMap<String, String>();
        logins.put(SOURCE_DISPLAY_NAME, displayName);
        logins.put(SOURCE_EMAIL, email);
        logins.put(SOURCE_PWD, pwd);
        logins.put(SOURCE_OTP, OTP);
        return logins;
    }

    @Override
    public AddUserRequest genAddUserRequest() {
        return null;
    }

    @Override
    public GetUserRequest genGetUserRequest() {
        return null;
    }
}
