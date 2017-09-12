package com.askeycloud.webservice.debug;

import android.util.Log;

/**
 * Created by david5_huang on 2016/12/16.
 */
public class SDKLog {

    private static boolean isLog = false;

    public static void e(String tag, String msg){
        if(isLog){
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(isLog){
            Log.d(tag, msg);
        }
    }

    
}
