package com.askeycloud.sdksample.extra;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by david5_huang on 2016/9/5.
 */
public class CameraLaunchHelper {
    public enum CameraAction{
        live,
        plackback,
        list
    }

    public enum AuthType{
        Facebook,
        eamil
    }

    public static void launchCameraApp(
            Context context,
            CameraAction cameraAction,
            AuthType authType,
            String deviceId,
            String accountId,
            String accessToken
    ) throws ActivityNotFoundException{
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setData(Uri.parse(getLaunchScheme(cameraAction, authType, deviceId, accountId, accessToken)));

        context.startActivity(it);
    }

    public static String getLaunchScheme(
            CameraAction cameraAction,
            AuthType authType,
            String deviceId,
            String accountId,
            String accessToken
    ){
        StringBuilder builder = new StringBuilder();
        builder.append("askey"+"://")
                .append("smacam.cameraList"+"/")
                .append(cameraAction+"?")
                .append("camID="+deviceId+"&")
                .append("accountID="+accountId+"&")
                .append("accessToken="+accessToken+"&")
                .append("authType="+authType);
        return builder.toString();
    }
}
