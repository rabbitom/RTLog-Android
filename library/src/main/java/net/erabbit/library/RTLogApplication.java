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
        RtLog.init(getApplicationContext());
    }
}
