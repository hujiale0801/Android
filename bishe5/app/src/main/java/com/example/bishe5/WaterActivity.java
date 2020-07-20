package com.example.bishe5;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class WaterActivity extends AppCompatActivity implements View.OnClickListener {

    private TcpClientConnector connector=null;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btn_now).setOnClickListener(this);
        findViewById(R.id.set_time).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        connector=TcpClientConnector.getInstance();
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btn_now:
                //现在浇水
                try {
                    connector.send("NOW");
                    Log.d("发送浇水开始命令：","成功");
                } catch (IOException e) {
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                    Log.d("发送浇水开始命令：","失败");
                }
                break;
            case R.id.set_time:
                //设置浇水时间
               intent.setClass(getApplicationContext(),TimeActivity.class);
               startActivity(intent);
                break;
            case R.id.btn_stop:
                try {
                    connector.send("STOP");
                    Log.d("发送浇水停止命令：","成功");
                } catch (IOException e) {
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                    Log.d("发送浇水停止命令：","失败");
                }
                break;
        }
    }
}
