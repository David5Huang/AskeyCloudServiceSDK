package tw.com.askey.webservice.sdk.exception;

/**
 * Created by david5_huang on 2016/7/8.
 */
public class BasicSDKException extends Exception {

    protected String errorMessage;

    public BasicSDKException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}
