package com.askeycloud.webservice.sdk.service.web;

import android.content.Context;

import com.askeycloud.webservice.sdk.ServiceConst;
import com.askeycloud.webservice.sdk.service.BasicAskeyCloudService;

/**
 * Created by david5_huang on 2017/1/25.
 */

public abstract class BasicAskeyWebService extends BasicAskeyCloudService {

    BasicAskeyWebService(Context context){
        super(context);
        settingInitApiUrl(ServiceConst.API_OAUTH_META_NAME, ServiceConst.API_OAUTH_V3_URL);
    }
}
