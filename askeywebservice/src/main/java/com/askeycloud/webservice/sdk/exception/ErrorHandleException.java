package com.askeycloud.webservice.sdk.exception;

/**
 * Created by david5_huang on 2016/7/8.
 */
public class ErrorHandleException extends Exception {

    protected int errorCode;
    protected String errorMessage;

    public ErrorHandleException(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
