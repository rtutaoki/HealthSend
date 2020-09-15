package com.example.justloginregistertest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper_user mDBOpenHelper_user;
    private DBOpenHelper_report mDBOpenHelper_report;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mDBOpenHelper_report = new DBOpenHelper_report(this);
        mDBOpenHelper_user = new DBOpenHelper_user(this);

    }

    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.bt_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);

        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.bt_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<Time> times = mDBOpenHelper_report.getAllData();
                    ArrayList<User> data = mDBOpenHelper_user.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Calendar calendar = Calendar.getInstance();
                        //获取系统的日期
                        // 年
                        int year = calendar.get(Calendar.YEAR);
                        //月
                        int month = calendar.get(Calendar.MONTH)+1;
                        //日
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        String localtime="";
                        localtime = localtime+year+"/"+month+"/"+day;
                        Log.i("time","localtime:"+localtime);
                        boolean time_flag = false;
                        for (int i = 0; i < data.size(); i++){
                            Time time = times.get(i);
                            Log.i("time","time:"+time.toString());
                            if(localtime.equals(time.getTime()) && name.equals(time.getName())){
                                time_flag = true;
                                break;
                            }
                        }
                        Log.i("time","time_flag:"+time_flag);
                        if(time_flag){
                            Intent intent = new Intent(this, ReportoverActivity.class);
                            startActivity(intent);
                            finish();//销毁此Activity
                        }
                        else
                        {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("name",name);
                            startActivity(intent);
                            finish();//销毁此Activity
                        }
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}



