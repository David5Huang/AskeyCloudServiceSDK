package com.askeycloud.webservice.sdk.exception;

/**
 * Created by david5_huang on 2016/7/8.
 */
public class NotDefineException extends ErrorHandleException {

    public static int ERR_CODE_MISSION_PARAMS = 0;
    public static int ERR_CODE_NOT_CONFIG = 1;

    protected int errorCode;

    public NotDefineException(int errorCode, String errorMessage) {
        super(errorCode, "Error code: " + errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
