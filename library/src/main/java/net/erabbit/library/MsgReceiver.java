package net.erabbit.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.json.JSONException;
import org.json.JSONObject;

import io.yunba.android.manager.YunBaManager;

/**
 * Created by ziv on 2017/11/30.
 */

public class MsgReceiver extends BroadcastReceiver {

    final String TAG = RtLog.TAG;
    private final static String REPORT_MSG_SHOW_NOTIFICARION = "1000";
    private final static String REPORT_MSG_SHOW_NOTIFICARION_FAILED = "1001";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

            final String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            final String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

            Log.i(RtLog.TAG, "topic=" + topic + ",msg=" + msg);
            Toast.makeText(context, "msg=" + msg, Toast.LENGTH_SHORT).show();
            StringBuilder showMsg = new StringBuilder();
            showMsg.append("Received message from server: ").append(YunBaManager.MQTT_TOPIC)
                    .append(" = ").append(topic).append(" ")
                    .append(YunBaManager.MQTT_MSG).append(" = ").append(msg);

            try {

                int versionCode = 0;
                String verName = "";

                //获取软件版本号，对应AndroidManifest.xml下android:versionCode

                PackageInfo packageInfo = context.getPackageManager().
                        getPackageInfo(context.getPackageName(), 0);

                versionCode = packageInfo.versionCode;
                verName = packageInfo.versionName;


                JSONObject jsonObject = new JSONObject(msg);
                final String alias = jsonObject.optString("from");
                final JSONObject sendObject = new JSONObject();
                sendObject.put("from", RtLog.alias);
                sendObject.put("os", "Android");
                sendObject.put("os_version", android.os.Build.VERSION.RELEASE);
                sendObject.put("phone_model", android.os.Build.BRAND + "," + android.os.Build.MODEL);
                sendObject.put("app_id", context.getPackageName());
                sendObject.put("app_version", verName);
                sendObject.put("app_build", versionCode);

                YunBaManager.publishToAlias(context, alias, sendObject.toString(), new IMqttActionListener() {
                    public void onSuccess(IMqttToken asyncActionToken) {

                        String msgLog = "Publish succeed : " + alias;
                        StringBuilder showMsg = new StringBuilder();
                        showMsg.append("[Demo] publish msg")
                                .append(" = ").append(msg).append(" to ")
                                .append(YunBaManager.MQTT_TOPIC).append(" = ").append(topic).append(" succeed");
                        Log.i(TAG, showMsg.toString());
                        Log.i(TAG, "msg=" + sendObject.toString());
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        String msg = "[Demo] Publish alias = " + alias + " failed : " + exception.getMessage();
                        Log.i(TAG, msg);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } else if (YunBaManager.PRESENCE_RECEIVED_ACTION.equals(intent.getAction())) {
            //msg from presence.
            String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            String payload = intent.getStringExtra(YunBaManager.MQTT_MSG);
            StringBuilder showMsg = new StringBuilder();
            showMsg.append("Received message presence: ").append(YunBaManager.MQTT_TOPIC)
                    .append(" = ").append(topic).append(" ")
                    .append(YunBaManager.MQTT_MSG).append(" = ").append(payload);
            Log.d("DemoReceiver", showMsg.toString());

        }
    }
}
