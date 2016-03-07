package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.service.LongRunningService;

import java.util.ArrayList;

/**
 * Created by SONY on 2016/2/23.
 */
public class RoomInfo extends Activity {
    private PieChart mChart;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roominfo);
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        float percent = bundle.getFloat("Percent");
        int room=bundle.getInt("Classroom");
        String building=bundle.getString("Building");
            switch (building){
            case "NMB": building="新主楼";break;
            case "ZhuM":building="主M";break;
            case "J3":building="教三";break;
            case "J4":building="教四";break;
            case "J5":building="教五";break;
        }
        TitleView tv=(TitleView)findViewById(R.id.title);
        tv.setTitle(building+room+"室");
        tv.setLeftButton("", new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                finish();
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
         finish();
    }

}
