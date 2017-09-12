package com.askeycloud.sdksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.askeycloud.iottest.DeviceApiTestActivity;

import askeycloud.com.authv3test.AuthV3TestActivity;

/**
 * Created by david5_huang on 2016/8/31.
 */
public class MainActivity extends Activity {

    private final int AUTH_TEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent it = new Intent(this, AuthV3TestActivity.class);
        this.startActivityForResult(it, AUTH_TEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == AUTH_TEST){
                Log.e("testing", "auth test result.");
                Intent it = new Intent(this, DeviceApiTestActivity.class);
                startActivity(it);

                this.finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.ACTION_DOWN){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
