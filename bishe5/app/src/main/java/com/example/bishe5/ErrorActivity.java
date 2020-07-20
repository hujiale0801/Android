package com.example.bishe5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btn_error).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        if(v.getId()==R.id.btn_error) {
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
