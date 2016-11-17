package tw.com.askey.webservice.sdk.model.auth;

/**
 * Created by david5_huang on 2016/11/16.
 */
public class BasicUserDataModel {
    private String userID;

    public boolean isCanUseDataModel(){
        return userID != null;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
