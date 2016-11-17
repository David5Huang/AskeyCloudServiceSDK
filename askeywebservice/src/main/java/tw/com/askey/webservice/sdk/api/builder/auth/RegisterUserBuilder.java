package tw.com.askey.webservice.sdk.api.builder.auth;

import java.util.ArrayList;
import java.util.HashMap;

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

    private HashMap<String, String> customParams;

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
        if(isUseCustomParams()){
            ArrayList<String> keys = new ArrayList<>(customParams.keySet());
            for(int i=0;i<keys.size();i++){
                request.setAdditionalProperty(keys.get(i), customParams.get(keys.get(i)));
            }
        }
        return request;
    }

    public void inputCustomParams(String key, String value){
        if(customParams == null){
            customParams = new HashMap<>();
        }
        customParams.put(key, value);
    }

    public boolean isUseCustomParams(){
        if(customParams == null){
            return false;
        }
        else if(customParams.isEmpty()){
            return false;
        }
        return true;
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
