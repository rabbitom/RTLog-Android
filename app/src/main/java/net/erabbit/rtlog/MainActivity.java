package net.erabbit.rtlog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.erabbit.library.MsgReceiver;
import net.erabbit.library.RtLog;

import io.yunba.android.manager.YunBaManager;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    EditText pubTopic;
    TextView message;
    EditText subTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pubTopic = (EditText) findViewById(R.id.pubTopic);
        message = (TextView) findViewById(R.id.message);
        subTopic = (EditText) findViewById(R.id.subTopic);

        RtLog.i(TAG, "success");
    }

    public void publish(View view) {
        String topic = pubTopic.getText().toString();
        String msg = message.getText().toString();
        if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(msg)) {
            RtLog.publish(this, topic, msg);
        }
    }

    public void subscribe(View view) {
        String topic = subTopic.getText().toString();
        if (!TextUtils.isEmpty(topic)) {
            RtLog.subscribe(this, new String[]{topic});
        }
    }
}
