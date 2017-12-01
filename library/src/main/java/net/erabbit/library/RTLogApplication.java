package net.erabbit.library;

import android.app.Application;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import io.yunba.android.manager.YunBaManager;

/**
 * Created by ziv on 2017/12/1.
 */

public class RTLogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YunBaManager.start(getApplicationContext());


        //订阅主题消息
        YunBaManager.subscribe(getApplicationContext(), new String[]{"t1"}, new IMqttActionListener() {

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
}
