package com.example.justloginregistertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReportoverActivity extends AppCompatActivity {

    private TextView mTvReportover;
    private MyBroadcast mBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportover);

        mTvReportover = findViewById(R.id.tv_reportover);

        mBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.danger");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcast,intentFilter);
    }

    private class MyBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mTvReportover.setText("您的体温异常！请尽快联系导员并前往医院检查！");
            Log.i("MyBroadcast","收到广播");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcast);
    }
}
