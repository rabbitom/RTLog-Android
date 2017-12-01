package net.erabbit.library;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.ArrayList;
import java.util.List;

import io.yunba.android.manager.YunBaManager;

/**
 * Created by ziv on 2017/12/1.
 */

public class RtLog {

    public static final String TAG = "rtLog";
    private List<String> topics = new ArrayList<String>();

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;
    public static final boolean isPublish = true;
    Context context;

    public RtLog(Context context) {
        this.context = context;
    }

    public void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
            if (isPublish) {
                publish(context);
            }
        }
    }

    public void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
            if (isPublish) {
                publish(context);
            }
        }
    }

    public void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
            if (isPublish) {
                publish(context);
            }
        }
    }

    public void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
            if (isPublish) {
                publish(context);
            }
        }
    }

    public void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);

            if (isPublish) {
                publish(context);
            }
        }
    }

    //发布消息
    private void publish(Context context) {
        final String topic = "test";
        final String msg = "hello yunba!";

        Log.i(TAG, "Publish msg = " + msg + " to topic = " + topic);
        YunBaManager.publish(context, topic, msg, new IMqttActionListener() {
            public void onSuccess(IMqttToken asyncActionToken) {

                String msgLog = "Publish succeed : " + topic;
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("[Demo] publish msg")
                        .append(" = ").append(msg).append(" to ")
                        .append(YunBaManager.MQTT_TOPIC).append(" = ").append(topic).append(" succeed");
                Log.i(TAG, showMsg.toString());
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                String msg = "[Demo] Publish topic = " + topic + " failed : " + exception.getMessage();
                Log.i(TAG, msg);
            }
        });
    }


    //设置别名
    private void setAlias(Context context, final String alias) {
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        Log.i(TAG, "set alias = " + alias);
        YunBaManager.setAlias(context, alias, new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken arg) {
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("setAlias alias ")
                        .append(" = ").append(alias).append(" succeed");
                Log.i(TAG, showMsg.toString());
            }

            @Override
            public void onFailure(IMqttToken arg0, Throwable arg) {
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("setAlias alias ")
                        .append(" = ").append(alias).append(" failed");
                Log.i(TAG, showMsg.toString());
            }
        });
    }


}
