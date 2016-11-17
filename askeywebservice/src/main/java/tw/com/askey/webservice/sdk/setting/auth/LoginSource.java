package tw.com.askey.webservice.sdk.setting.auth;

import java.util.Map;

import tw.com.askey.webservice.sdk.api.request.AddUserRequest;
import tw.com.askey.webservice.sdk.api.request.GetUserRequest;

/**
 * Created by david5_huang on 2016/7/5.<br/>
 * <br/>
 * 在進行 active 時<br/>
 * 設定登入 & 註冊資訊的資料結構
 */
abstract public class LoginSource {

    protected String displayName;
    protected String email;
    protected String provider;
    protected String providerMail;
    protected String providerUid;

    public LoginSource(String displayName,
                       String email,
                       String providerUid,
                       String providerMail){
        this.displayName = displayName;
        this.email = email;
        this.providerUid = providerUid;
        this.providerMail = providerMail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderMail() {
        return providerMail;
    }

    public void setProviderMail(String providerMail) {
        this.providerMail = providerMail;
    }

    public String getProviderUid() {
        return providerUid;
    }

    public void setProviderUid(String providerUid) {
        this.providerUid = providerUid;
    }

    /**
     * 依照不同的帳號provider, 填入不同的值
     * @return
     */
    abstract public Map<String, String> getLoginSource();

    /**
     * 生成與 server 溝通的 api activeDeviceRequest(addUser api)
     * @return
     */
    abstract public AddUserRequest genAddUserRequest();

    /**
     * 生成與 server 溝通的 api activeDeviceRequest(getUser api)
     * @return
     */
    abstract public GetUserRequest genGetUserRequest();

}
