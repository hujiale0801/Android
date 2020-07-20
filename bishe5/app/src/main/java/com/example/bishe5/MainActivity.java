package com.example.bishe5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editIP1;
    private EditText editIP2;
    private EditText editIP3;
    private EditText editIP4;
    private EditText editPort;
    private CheckBox RIP;
    private String TAG="MainActivity";
    private String SERVER_IP1="ip1";
    private String SERVER_IP2="ip2";
    private String SERVER_IP3="ip3";
    private String SERVER_IP4="ip4";
    private String SERVER_PORT="port";
    private String RIP_REMEMBER="rip_remember";
    private SharedPreferences config;
    private boolean mIsChecked=false;
    private String ip;

    //网络
    private TcpClientConnector connector=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initUI();
        //初始化数据
        initData();
        //连接服务端
        linkServer();

    }



    private void initData() {
        if(config==null){
            config=getApplicationContext().getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        //回显数据
        editIP1.setText(config.getString(SERVER_IP1, ""));
        editIP2.setText(config.getString(SERVER_IP2, ""));
        editIP3.setText(config.getString(SERVER_IP3, ""));
        editIP4.setText(config.getString(SERVER_IP4, ""));
        editPort.setText(config.getString(SERVER_PORT, ""));
        mIsChecked = config.getBoolean(RIP_REMEMBER, true);
        RIP.setChecked(mIsChecked);

    }

    private void initUI() {

        editIP1=findViewById(R.id.tx_ip1);
        editIP1.addTextChangedListener(new TextWatcher() { //添加监听
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                accountChange(SERVER_IP1,editIP1);
            }
        });

        editIP2=findViewById(R.id.tx_ip2);
        editIP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                accountChange(SERVER_IP2,editIP2);
            }
        });

        editIP3=findViewById(R.id.tx_ip3);
        editIP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                accountChange(SERVER_IP3,editIP3);
            }
        });

        editIP4=findViewById(R.id.tx_ip4);
        editIP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                accountChange(SERVER_IP4,editIP4);
            }
        });


        editPort=findViewById(R.id.tx_port);
        editPort.addTextChangedListener(new TextWatcher() { //添加监听
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                accountChange(SERVER_PORT,editPort);
            }
        });

        //获取多选按钮
        RIP=findViewById(R.id.remember_IP);//多选框的处理方式，记住IP和PORT并保存在文件中
        RIP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsChecked=isChecked;
                if(isChecked){
                    //实例化SharedPreferences对象，创建一个config文件
                    if(config==null) {
                        config = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                    }
                    //实例化SharedPreferences的编辑对象
                    SharedPreferences.Editor editor=config.edit();
                    //存放数据
                    editor.putString(SERVER_IP1,editIP1.getText().toString());
                    editor.putString(SERVER_IP2,editIP2.getText().toString());
                    editor.putString(SERVER_IP3,editIP3.getText().toString());
                    editor.putString(SERVER_IP4,editIP4.getText().toString());
                    editor.putString(SERVER_PORT,editPort.getText().toString());
                    editor.putBoolean(RIP_REMEMBER, true);
                    editor.commit();
                }else {
                    if(config==null) {
                        config = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                    }
                    //实例化SharedPreferences的编辑对象
                    SharedPreferences.Editor editor=config.edit();
                    editor.putBoolean(RIP_REMEMBER, false);
                    editor.commit();
                }
            }
        });
    }


    private void accountChange(String information,EditText editText){
        //文本改变之后记录变化

        if(mIsChecked) {
            if (config == null) {
                config = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
            }
            SharedPreferences.Editor editor = config.edit();
            editor.putString(information, editText.getText().toString());
            editor.commit();
        }
    }



    private void linkServer() {
        findViewById(R.id.btn_link).setOnClickListener(this);
    }

    //验证连接服务端是否成功
    private void checkLink(){
        ip=editIP1.getText().toString()+"."+editIP2.getText().toString()+"."+editIP3.getText().toString()+"."+editIP4.getText().toString();
        String port=editPort.getText().toString();
        connector=TcpClientConnector.getInstance();
        connector.creatConnect(ip,Integer.parseInt(port));
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        if(R.id.btn_link==v.getId()){//判断按钮是否被点击
            try {
                checkLink();
                Thread.sleep(500);//主线程等待，
                if(connector.flag) {//验证是否连接到服务端
                    connector.send("SET_TIME-"+obtainNoeTime());
                    intent.setClass(getApplicationContext(), ComeActivity.class);
                    startActivity(intent);
                    Log.d("连接到："+ip+"服务器","成功");
                }
            } catch (Exception e) {
                intent.setClass(getApplicationContext(), ErrorActivity.class);
                startActivity(intent);
                Log.d("连接到："+ip+"服务器","失败");
            }
        }
    }

    //获取当前时间
    private String  obtainNoeTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//设置日期格式
        return df.format(new Date());
    }
}
