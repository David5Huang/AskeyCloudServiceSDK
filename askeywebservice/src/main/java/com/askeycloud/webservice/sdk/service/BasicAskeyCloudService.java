package com.askeycloud.webservice.sdk.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.askeycloud.webservice.debug.DebugConst;
import com.askeycloud.webservice.sdk.ServiceConst;
import com.askeycloud.webservice.sdk.api.ApiStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by david5_huang on 2017/3/15.
 */

public abstract class BasicAskeyCloudService {

    protected String apiKey;

    protected final Context context;
    protected String useApiUrl;

    public BasicAskeyCloudService(Context context){
        this.context = context;
        this.settingApiKey();
    }

    public String getUseApiUrl() {
        return useApiUrl;
    }

    public void setUseApiUrl(String useApiUrl) {
        this.useApiUrl = useApiUrl;
    }

    /**
     * @param metaName Change API url via android manifest metadata.
     * @param initUseUrl New API url.
     */
    public void settingInitApiUrl(String metaName, String initUseUrl){
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            useApiUrl = bundle.getString(metaName);
            if (useApiUrl == null) {
                useApiUrl = initUseUrl;
            }
        }catch (Exception e){
            useApiUrl = initUseUrl;
        }
    }

    public void settingApiKey(){
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            apiKey = bundle.getString(ServiceConst.API_KEY_META_NAME);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> T getAuthParameterErrResponse(Class<T> object){
        try {

            Log.e(DebugConst.DEBUG_TAG, ApiStatus.ERR_MESSAGE_LOGIN_VIA_V3);

            T instance = object.newInstance();

            Method settingCode = instance.getClass().getMethod("setCode", Integer.class);
            Method settingMsg = instance.getClass().getMethod("setMessage", String.class);

            settingCode.invoke(instance, ApiStatus.API_UNKNOW_ERROR);
            settingMsg.invoke(instance, ApiStatus.ERR_MESSAGE_LOGIN_VIA_V3);

            return instance;
        } catch (InstantiationException e) {
            Log.e(DebugConst.DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(DebugConst.DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.e(DebugConst.DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(DebugConst.DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
