package tw.com.askey.webservice.sdk.api.builder.auth;

import tw.com.askey.webservice.sdk.api.request.auth.RegisterUserRequest;

/**
 * Created by david5_huang on 2016/11/8.
 */
public class RegisterUserBuilder extends BaseAuthBuilder {
    private String email;
    private String password;
    private String passcode;
    private String displayName;
    private String OTP;

    public RegisterUserBuilder(String email, String password, String displayName){
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.passcode = convertPwd2Passcode(password);
    }

    public RegisterUserBuilder(String email, String password, String displayName, String OTP){
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.passcode = convertPwd2Passcode(password);
        this.OTP = OTP;
    }

    public RegisterUserRequest genRegisterUserRequest(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail(email);
        request.setPasscode(passcode);
        request.setDisplayname(displayName);
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
