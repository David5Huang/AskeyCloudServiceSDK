package tw.com.askey.ia4test;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import tw.com.askey.webservice.sdk.iot.MqttActionConst;

/**
 * Created by david5_huang on 2016/7/25.
 */
public class MqttChangedReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ia4test", "mqtt receive");
        if(intent != null){
            if(intent.getAction().equals(MqttActionConst.MQTT_RECEIVER_MESSAGE_ACTION)){
                String msg = intent.getStringExtra(MqttActionConst.MQTT_RECEIVER_MESSAGE_DATA_TAG);
                sendNotifi(context, msg);
            }
        }
    }

    private void sendNotifi(Context context, String msg){
        Intent it = new Intent(context, MqttResultActivity.class);
        it.putExtra("mqtt", msg);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Mqtt title")
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
