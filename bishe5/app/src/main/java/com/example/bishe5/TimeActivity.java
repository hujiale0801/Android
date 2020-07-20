package com.example.bishe5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editStartTHour;//开始时间
    private EditText editStartMin;
    private EditText editEndHour;//结束时间
    private EditText editEndMin;
    private String time_slot;//发送的数据

    private EditText editInterval;//浇水间隔时间
    private EditText editDuration;//浇水时长
    private EditText editFrequency;//浇水次数
    private String time_frequency;


    private TcpClientConnector connector=null;

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        connector=TcpClientConnector.getInstance();
        initUI();
        setTime();
    }


    private void initUI() {
        editStartTHour=findViewById(R.id.start_hour);
        editStartMin=findViewById(R.id.start_min);
        editEndHour=findViewById(R.id.end_hour);
        editEndMin=findViewById(R.id.end_min);
        editInterval=findViewById(R.id.time_interval);
        editDuration=findViewById(R.id.time_duration);
        editFrequency=findViewById(R.id.time_frequency);

    }

    private void setTime() {
        findViewById(R.id.btn_slot).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        time_slot="SET_WORK";
        time_slot+="-"+editStartTHour.getText().toString();
        time_slot+="-"+editStartMin.getText().toString();
        time_slot+="-"+editEndHour.getText().toString();
        time_slot+="-"+editEndMin.getText().toString();

        time_frequency="SET_LOT";
        time_frequency+="-"+obtainNoeTime();
        time_frequency+="-"+editInterval.getText().toString();
        time_frequency+="-"+editDuration.getText().toString();
        time_frequency+="-"+editFrequency.getText().toString();
        switch (v.getId()){
            case R.id.btn_slot:
                try {
                    connector.send(time_slot);
                    Log.d("发送设置浇水时间段命令：","成功");
                } catch (IOException e) {
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                    Log.d("发送设置浇水时间段命令：","失败");
                }
                break;
            case R.id.btn_submit:
                try {
                connector.send(time_frequency);
                    Log.d("发送设置浇水时间频率命令：","成功");
                } catch (IOException e) {
                    Log.d("发送设置浇水时间频率命令：","失败");
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
    //获取当前时间
    private String  obtainNoeTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH-mm");//设置日期格式
        return df.format(new Date());
    }
}
