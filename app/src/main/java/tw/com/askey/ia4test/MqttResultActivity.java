package tw.com.askey.ia4test;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * Created by david5_huang on 2016/8/4.
 */
public class MqttResultActivity extends Activity {

    TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(tw.com.askey.ia4test.R.layout.mqtt_result);

        resultTxt = (TextView) findViewById(tw.com.askey.ia4test.R.id.mqtt_result_txt);

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
