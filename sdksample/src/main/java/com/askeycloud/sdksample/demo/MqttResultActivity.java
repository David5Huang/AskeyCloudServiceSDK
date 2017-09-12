package com.askeycloud.sdksample.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askeycloud.sdksample.R;

/**
 * Created by david5_huang on 2016/8/4.
 */
public class MqttResultActivity extends Activity {

    TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mqtt_result);

        resultTxt = (TextView) findViewById(R.id.mqtt_result_txt);

        String msg = getIntent().getStringExtra("mqtt");

        resultTxt.setText(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
