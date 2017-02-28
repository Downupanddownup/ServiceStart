package com.example.hopjs.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileDescriptor;

public class MyService extends Service {

    private MyBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }

    public MyService() {
       showMsg("MyService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        showMsg("onBind");
        return binder;
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showMsg("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        showMsg("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        showMsg("onCreate");
        super.onCreate();
    }

    public String getMsg(){
        return "This is MyService.";
    }

    private void showMsg(String msg){
        Log.i("Service111","MyService:"+msg+" ThreadId:"+android.os.Process.myTid());
    }
}
