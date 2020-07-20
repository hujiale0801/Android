package com.example.bishe5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class LightActivity extends AppCompatActivity implements View.OnClickListener {

    private TcpClientConnector connector=null;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btn_open).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        connector=TcpClientConnector.getInstance();
        switch (v.getId()){
            case R.id.btn_open:
                //开灯
                try {
                    connector.send("OPEN");
                    Log.d("发送开灯命令","成功");
                } catch (IOException e) {
                    Log.d("发送开灯命令","失败");
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_close:
                //关灯
                try {
                    connector.send("CLOSE");
                    Log.d("发送关灯命令","成功");
                } catch (IOException e) {
                    Log.d("发送关灯命令","失败");
                    intent.setClass(getApplicationContext(), ErrorActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
