package com.example.justloginregistertest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.Calendar;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper_report mDBOpenHelper_report;
    private RelativeLayout mRlMainactivityTop;
    private Button mBtMainactivityReport;
    private EditText mEtMainactivityLocation;
    private EditText mEtMainactivityTemperature;
    private LinearLayout mLlMainactivityone;
    private RadioGroup mRgMainactivityOneself;
    private RadioGroup mRgMainactivityParents;
    private RadioButton mRbMainactivityOneself_yes;
    private RadioButton mRbMainactivityOneself_no;
    private RadioButton mRbMainactivityParents_yes;
    private RadioButton mRbMainactivityParents_no;
    private ImageView mIvMainactivityBack;
    private TextView mTvMainactivityTime;
    private String oneself;
    private String parents;
    private String temperature;
    private String location;
    String time="";
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        GetLocation();

        mRgMainactivityOneself.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_mainactivity_oneself_yes :
                        oneself = "是";
                        break;
                    case R.id.rb_mainactivity_oneself_no:
                        oneself = "否";
                        break;
                }
            }
        });
        mRgMainactivityParents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_mainactivity_parents_yes :
                        parents = "是";
                        break;
                    case R.id.rb_mainactivity_parents_no:
                        parents = "否";
                        break;
                }
            }
        });
    }

    private void initView() {
        // 初始化控件对象

        mRlMainactivityTop = findViewById(R.id.rl_mainactivity_top);
        mBtMainactivityReport = findViewById(R.id.bt_mainactivity_report);
        mEtMainactivityLocation = findViewById(R.id.et_mainactivity_location);
        mEtMainactivityTemperature = findViewById(R.id.et_mainactivity_temperature);
        mLlMainactivityone = findViewById(R.id.ll_mainactivityone);
        mRgMainactivityOneself = findViewById(R.id.oneself);
        mRgMainactivityParents = findViewById(R.id.parents);
        mRbMainactivityOneself_yes = findViewById(R.id.rb_mainactivity_oneself_yes);
        mRbMainactivityOneself_no = findViewById(R.id.rb_mainactivity_oneself_no);
        mRbMainactivityParents_yes = findViewById(R.id.rb_mainactivity_parents_yes);
        mRbMainactivityParents_no = findViewById(R.id.rb_mainactivity_parents_no);
        mIvMainactivityBack = findViewById(R.id.iv_mainactivity_back);
        mTvMainactivityTime = findViewById(R.id.tv_mainactivity_time);
        // 绑定点击监听器
        mBtMainactivityReport.setOnClickListener(this);
        mIvMainactivityBack.setOnClickListener(this);

        oneself = "否";
        parents = "否";

        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        // 年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        time = time+year+"/"+month+"/"+day;
        mTvMainactivityTime.setText(time);
        mDBOpenHelper_report = new DBOpenHelper_report(this);

        Intent nameintent = getIntent();
        name = nameintent.getStringExtra("name");

    }

    public void onClick(View view) {

        if (view.getId() == R.id.iv_mainactivity_back) {
            Intent intent1 = new Intent(this, loginActivity.class);
            startActivity(intent1);
            finish();
        }
        if (view.getId() == R.id.bt_mainactivity_report) {
            location = mEtMainactivityLocation.getText().toString();
            temperature = mEtMainactivityTemperature.getText().toString();
            if(!TextUtils.isEmpty(location) && !TextUtils.isEmpty(temperature) && !TextUtils.isEmpty(time)){

                Log.i("time","name:"+name);
                mDBOpenHelper_report.add(name,time,temperature,oneself,parents,location);

                Double temper = Double.parseDouble(temperature);
                if(temper>= 37.3){
                    Intent Bintent = new Intent("com.danger");
                    LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(Bintent);
                    Log.i("MyBroadcast","发出广播");
                }

                Toast.makeText(this,  "上报成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ReportoverActivity.class);
                startActivity(intent);
                finish();//销毁此Activity
            }
            else {
                Toast.makeText(this,  "信息未填写完全，请重新填写！", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void GetLocation(){
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
//初始化定位
        mLocationClient = new AMapLocationClient(this);
//设置定位回调监听

//异步获取定位结果
        AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                Log.i("position", amapLocation.getErrorInfo());
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //解析定位结果
//                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        amapLocation.getLatitude();//获取纬度
//                        amapLocation.getLongitude ;//获取经度
//                        amapLocation.getAccuracy();//获取精度信息
//                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        amapLocation.getCountry();//国家信息
//                        amapLocation.getProvince();//省信息
//                        amapLocation.getCity();//城市信息
//                        amapLocation.getDistrict();//城区信息
//                        amapLocation.getStreet();//街道信息
//                        amapLocation.getStreetNum();//街道门牌号信息
//                        amapLocation.getCityCode();//城市编码
//                        amapLocation.getAdCode();//地区编码
//                        amapLocation.getAoiName();//获取当前定位点的AOI信息
//                        amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                        amapLocation.getFloor();//获取当前室内定位的楼层
//                        amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态

                        Log.i("position","position:"+amapLocation.getLocationType());
                        Log.i("position","position:"+amapLocation.getLatitude());
                        Log.i("position","position:"+amapLocation.getLongitude());
                        Log.i("position","position:"+amapLocation.getAccuracy());
                        Log.i("position","position:"+amapLocation.getAddress());
                        Log.i("position","position:"+amapLocation.getCountry());
                        Log.i("position","position:"+amapLocation.getProvince());
                        Log.i("position","position:"+amapLocation.getCity());
                        Log.i("position","position:"+amapLocation.getDistrict());
                        Log.i("position","position:"+amapLocation.getStreet());
                        Log.i("position","position:"+amapLocation.getStreetNum());
                        Log.i("position","position:"+amapLocation.getCityCode());
                        Log.i("position","position:"+amapLocation.getAdCode());
                        Log.i("position","position:"+amapLocation.getBuildingId());
                        Log.i("position","position:"+amapLocation.getFloor());
                        Log.i("position","--------------------------------------------");

                        mEtMainactivityLocation.setText(amapLocation.getAddress());

                    }
                    Log.i("position", amapLocation.getErrorInfo());
                }
            }
        };
        mLocationClient.setLocationListener(mAMapLocationListener);
        AMapLocationClientOption mLocationOption = null;

        mLocationOption = new AMapLocationClientOption();
        mLocationClient.stopLocation();

        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setLocationCacheEnable(false);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setMockEnable(true);


        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }


}
