package com.service;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.database.DB;
import com.database.StudyInBeihangOpenHelper;
import com.example.sony.StudyInBeihang.MainActivity;
import com.example.sony.StudyInBeihang.R;
import com.example.sony.StudyInBeihang.SearchRoomFragment;
import com.example.sony.StudyInBeihang.details;
import com.examples.sony.util.HttpCallbackListener;
import com.examples.sony.util.HttpUtil;

import java.net.URLEncoder;

/**
 * Created by SONY on 2016/2/21.
 */
public class LongRunningService extends Service {
    private static DB db;
    private boolean first=true;
    private String[] buildings={"新主楼","教三","教四","教五","主M"};
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        Bundle bundle=intent.getExtras();
        first=bundle.getBoolean("First");
        if(first){
           db=new DB(this);
        }
        else db=DB.getInstance(LongRunningService.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String s : buildings)
                    getRealData(s, first);
                if (first) {
                    final String buildings[] = {"j3", "j4", "j5"};
                    for (int i = 0; i < 3; i++) {
                        String address = "http://vpn.iliana.wang/getcourseinfo/?location=" + buildings[i];
                        final int finalI = i;
                        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                            @Override
                            public void onFinish(final String response) {
                                boolean result = false;
                                result = HttpUtil.GetCourseinfoWithJSONObject(db, response);
                                if (result) {
                                    Log.d("LongRunning", buildings[finalI]+"课表ok");
                                } else {
                                }
                            }

                            @Override
                            public void onError(Exception e) {
                                //Toast.makeText(LongRunningService.this,"请连网后使用",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }
        }).start();


        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int aMinute=60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+10*aMinute;
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
    private void getRealData(final String location,final boolean First){
            String address= "http://vpn.iliana.wang/query/?location="+ URLEncoder.encode(location);
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {
                    boolean result = false;
                    result = HttpUtil.parseJSONWithJSONObject(db, response, First);
                    if (result) {
                        Log.d("LongRunning", location + "ok");
                    } else {

                    }
                }
            @Override
            public void onError(Exception e) {
               // Toast.makeText(LongRunningService.this,"请连网后使用",Toast.LENGTH_LONG).show();
            }
        });
    }
}
