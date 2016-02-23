package com.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.database.DB;
import com.database.StudyInBeihangOpenHelper;
import com.example.sony.StudyInBeihang.MainActivity;
import com.example.sony.StudyInBeihang.SearchRoomFragment;
import com.example.sony.StudyInBeihang.details;
import com.examples.sony.util.HttpCallbackListener;
import com.examples.sony.util.HttpUtil;

import java.net.URLEncoder;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by SONY on 2016/2/21.
 */
public class LongRunningService extends Service {
    private DB db;
    private boolean first=true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Bundle bundle=intent.getExtras();
        first=bundle.getBoolean("First");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] buildings={"新主楼","教三","教四","教五","主M"};
                for (String s : buildings)
                    getRealData(s,first);
            }
        }).start();

        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int aMinute=60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+5*aMinute;
        Intent i=new Intent(this, AlarmReceiver.class);
        Bundle b=new Bundle();
        b.putBoolean("First", false);
        i.putExtras(b);
        PendingIntent pi=PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("LongRunning","onDestroy executed.");
    }
    private void getRealData(final String location,final boolean First){
        String address= "http://chaopengz.nat123.net:19870/query/?location="+ URLEncoder.encode(location);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                boolean result = false;
                db=new DB(LongRunningService.this);
                result = HttpUtil.parseJSONWithJSONObject(db, response, First);
                if (result) {
                    Log.d("LongRunning", location+"ok");
                }
                else{
                    Log.d("LongRunning",location+"getRealData not ok!");
                }
            }

            @Override
            public void onError(Exception e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(details.this, "加载失败！", Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        });
    }
}