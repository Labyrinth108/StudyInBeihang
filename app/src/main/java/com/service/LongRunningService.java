package com.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
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

import java.util.Date;

/**
 * Created by SONY on 2016/2/21.
 */
public class LongRunningService extends Service {
    private DB db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService","executed at "+new Date().toString());
                getRealData("", 11);
            }
        }).start();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int aMinute=60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+5*aMinute;
        Intent i=new Intent(this, AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("LongRunning","onDestroy executed.");
    }
    private void getRealData(final String location, final int room){
        String address= null;
        address = "http://chaopengz.nat123.net:19870/query/?location=%E6%96%B0%E4%B8%BB%E6%A5%BC&room=101";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                boolean result = false;
                db=new DB(LongRunningService.this);
                result = HttpUtil.parseJSONWithJSONObject(db, response);
                if (result) {
                    Log.d("LongRunning", "ok");
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
