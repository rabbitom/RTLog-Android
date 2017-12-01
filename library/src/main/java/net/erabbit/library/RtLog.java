package net.erabbit.library;

import android.content.Context;
import android.content.IntentFilter;
import android.provider.Settings;
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
    private static Context context;
    public static String alias;

    public static void init(Context context) {
        RtLog.context = context;
        YunBaManager.start(context);
        subscribe(context, new String[]{"clients"});
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        alias = (context.getPackageName() + "." + ANDROID_ID).replace(".", "_");
        setAlias(context, alias);

        MsgReceiver mMessageReceiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(YunBaManager.MESSAGE_RECEIVED_ACTION);
        filter.addCategory(context.getPackageName());
        context.registerReceiver(mMessageReceiver, filter);
    }

    public static void init(Context context, String appkey) {
        RtLog.context = context;
        YunBaManager.start(context, appkey);
        subscribe(context, new String[]{"clients"});
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        alias = (context.getPackageName() + "." + ANDROID_ID).replace(".", "_");
        setAlias(context, alias);


        MsgReceiver mMessageReceiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(YunBaManager.MESSAGE_RECEIVED_ACTION);
        filter.addCategory(context.getPackageName());
        context.registerReceiver(mMessageReceiver, filter);


    }

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
            if (isPublish) {
                publish(context, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
            if (isPublish) {
                publish(context, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
            if (isPublish) {
                publish(context, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
            if (isPublish) {
                publish(context, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);

            if (isPublish) {
                publish(context, msg);
            }
        }
    }


    //发布消息
    public static void publish(Context context, final String msg) {
        final String topic = alias;

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


    //发布消息
    public static void publish(Context context, final String topic, final String msg) {
        //final String topic = "test";
        //final String msg = "hello yunba!";

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


    public static void subscribe(Context context, String[] alias) {

        //订阅主题消息
        YunBaManager.subscribe(context, alias, new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken arg0) {
                Log.d(RtLog.TAG, "Subscribe topic succeed");
            }

            @Override
            public void onFailure(IMqttToken arg0, Throwable arg1) {
                Log.d(RtLog.TAG, "Subscribe topic failed");
            }
        });

    }


    //设置别名
    private static void setAlias(Context context, final String alias) {
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
