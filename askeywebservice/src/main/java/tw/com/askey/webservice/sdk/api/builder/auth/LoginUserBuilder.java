package tw.com.askey.webservice.sdk.api.builder.auth;

import tw.com.askey.webservice.sdk.api.request.auth.LoginUserRequest;

/**
 * Created by david5_huang on 2016/11/8.
 */
public class LoginUserBuilder extends BaseAuthBuilder {
    private String email;
    private String password;
    private String passcode;
    private String OTP;

    public LoginUserBuilder(String email, String password) {
        this.email = email;
        this.password = password;
        this.passcode = convertPwd2Passcode(password);
    }

    public LoginUserBuilder(String email, String password, String OTP){
        this.email = email;
        this.password = password;
        this.passcode = convertPwd2Passcode(password);
        this.OTP = OTP;
    }

    public LoginUserRequest genLoginUserRequest(){
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail(email);
        request.setPasscode(passcode);
        if(OTP != null && !OTP.isEmpty()){
            request.setOTP(OTP);
        }
        return request;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
