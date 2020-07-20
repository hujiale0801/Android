package com.example.bishe5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class WifiActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private EditText editWifiName;
    private EditText editWifiPwd;
    private TcpClientConnector connector=null;
    private String link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connector=TcpClientConnector.getInstance();
        setContentView(R.layout.activity_wifi);
        initUI();
        initData();
    }

    private void initUI() {
        editWifiName=findViewById(R.id.wifi_name);
        editWifiPwd=findViewById(R.id.wifi_pwd);
    }

    private void initData() {
        findViewById(R.id.link_wifi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.link_wifi){
            link="LINKWIFI"+"-"+editWifiName.getText().toString()+"-"+editWifiPwd.getText().toString();
            try {
                connector.send(link);
                Log.d("连接WIFI"+editWifiName.getText().toString(),"成功");
            } catch (IOException e) {
                Log.d("连接WIFI"+editWifiName.getText().toString(),"失败");
                intent.setClass(getApplicationContext(), ErrorActivity.class);
                startActivity(intent);
            }
        }
    }
}
