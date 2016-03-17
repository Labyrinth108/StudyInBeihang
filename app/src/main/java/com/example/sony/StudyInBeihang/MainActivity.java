package com.example.sony.StudyInBeihang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.database.DB;
import com.example.sony.StudyInBeihang.FragmentIndicator.OnIndicateListener;

public class MainActivity extends FragmentActivity {
    public static Fragment[] mFragments;
    private DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragmentIndicator(0);
        db=DB.getInstance(this);
    }

    /**
     * 初始化fragment
     */
    private void setFragmentIndicator(int whichIsDefault) {
        mFragments = new Fragment[3];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_searchroom);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_diningroom);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_recommend);
        getSupportFragmentManager().beginTransaction().hide(mFragments[0])
                .hide(mFragments[1]).hide(mFragments[2]).show(mFragments[whichIsDefault]).commit();

        FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
        FragmentIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(new OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]).show(mFragments[which]).commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent intent=new Intent(this,LongRunningService.class);
//        stopService(intent);
    }
}