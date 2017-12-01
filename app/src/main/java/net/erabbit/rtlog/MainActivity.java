package net.erabbit.rtlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.erabbit.library.RtLog;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RtLog rtLog = new RtLog(this);
        rtLog.i(TAG, "success");
    }
}
