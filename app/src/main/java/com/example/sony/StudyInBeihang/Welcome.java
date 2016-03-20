package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.service.LongRunningService;

/**
 * Created by SONY on 2016/2/22.
 */
public class Welcome extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Welcome.this,LongRunningService.class);
                startService(intent);

                Intent i = new Intent(Welcome.this, MainActivity.class);
                startActivity(i);
                //启动主Activity后销毁自身
                finish();
            }
        }, 5000);
    }
}
