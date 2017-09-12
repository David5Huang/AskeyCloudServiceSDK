package com.askeycloud.webservice.sdk.api;

/**
 * Created by david5_huang on 2017/8/2.
 *
 * For android sdk uses api status.
 */
public interface ApiStatus extends com.askeycloud.sdk.core.api.ApiStatus {
    public static final String ERR_MESSAGE_LOGIN_VIA_V3 = "AskeyCloud SDK Can't find OAuth V3 parameter, please login via AskeyWebService.activeV3 function.";
}
