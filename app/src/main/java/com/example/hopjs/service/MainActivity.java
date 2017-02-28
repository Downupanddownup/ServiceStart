package com.example.hopjs.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
/*目的：了解servcie的使用方法
* 结果：
* 1，创建自定义的service
* 2，在service中创建自定义的binder可以在本应用中被其他activity调用
* 3，通过startService或bindService启动service，通过后者启动service需要先创建
* serviceConnection，可以在其中获得service的引用
* 4，在onStart或onStop中进行绑定或解除绑定*/
public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myService = ((MyService.MyBinder)iBinder).getService();
            showMsg("onServiceConnected");
        }

        /*Android系统在同service的连接意外丢失时调用这个．
        比如当service崩溃了或被强杀了．当客户端解除绑定时，这个方法不会被调用．*/
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMsg("onCreate");
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("111");
                intent.setPackage(getPackageName());
               // startService(new Intent(MainActivity.this,MyService.class));
                bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myService != null){
                    ((TextView)findViewById(R.id.text))
                            .setText(myService.getMsg());
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("111");
                intent.setPackage(getPackageName());
                unbindService(serviceConnection);
            }
        });
    }

    private void showMsg(String msg){
        Log.i("Service111","MainActivity:"+msg+" ThreadId:"+android.os.Process.myTid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction("111");
        intent.setPackage(getPackageName());
        unbindService(serviceConnection);
    }
}
