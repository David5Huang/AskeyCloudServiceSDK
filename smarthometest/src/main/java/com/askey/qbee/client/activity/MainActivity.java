package com.askey.qbee.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by david5_huang on 2016/8/31.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent it = new Intent(this, MasterTestActivity.class);
//        Intent it = new Intent(this, AuthTestActivity.class);
        this.startActivity(it);

        finish();

    }
}
