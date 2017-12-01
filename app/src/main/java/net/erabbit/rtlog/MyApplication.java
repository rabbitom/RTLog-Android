package net.erabbit.rtlog;

import android.app.Application;

import net.erabbit.library.RtLog;

/**
 * Created by ziv on 2017/12/1.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RtLog.init(this, "5a20cb7b2a27c9d728f534f0");
        //RtLog. subscribe(this);
    }
}
