package com.example.bishe5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ComeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_come);
            initUI();

    }

    private void initUI() {
        findViewById(R.id.btn_light).setOnClickListener(this);
        findViewById(R.id.btn_water).setOnClickListener(this);
        findViewById(R.id.btn_wifi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btn_light:
                intent.setClass(getApplicationContext(),LightActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_water:
                intent.setClass(getApplicationContext(),WaterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_wifi:
                intent.setClass(getApplicationContext(),WifiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
