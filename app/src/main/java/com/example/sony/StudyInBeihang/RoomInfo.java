package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coursetable.TimeTableModel;
import com.coursetable.TimeTableView;
import com.database.DB;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 2016/2/23.
 */
public class RoomInfo extends Activity {
    private PieChart mChart;
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    private String building;
    private int room;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roominfo);
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        float percent = bundle.getFloat("Percent");
        room=bundle.getInt("Classroom");
        building=bundle.getString("Building");

        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        addList();
        mTimaTableView.setTimeTable(mList);


        String buildingtext="";
            switch (building){
            case "NMB": buildingtext="新主楼";break;
            case "ZhuM":buildingtext="主M";break;
            case "J3":buildingtext="教三";break;
            case "J4":buildingtext="教四";break;
            case "J5":buildingtext="教五";break;
        }
        TitleView tv=(TitleView)findViewById(R.id.title);
        tv.setTitle(buildingtext+room+"室");
        tv.setLeftButton("", new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                finish();
            }
        });
        mChart = (PieChart) findViewById(R.id.pie_chart);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(40f);  //半径
        mChart.setTransparentCircleRadius(43f); // 半透明圈
        mChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(90); // 初始旋转角度
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true); // 可以手动旋转
        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // display percentage values
        mChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setDrawUnitsInChart(true);
//      mChart.setOnAnimationListener(this);
        mChart.setCenterText(room+"室");  //饼状图中间的文字
        mChart.setDescription("");

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        yVals.add(new Entry(percent,0));
        xVals.add("已占用");
        yVals.add(new Entry(1-percent,1));
        xVals.add("未占用");

        PieDataSet pieDataSet = new PieDataSet(yVals, "使用情况"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(ColorTemplate.VORDIPLOM_COLORS[2]);
        colors.add(ColorTemplate.VORDIPLOM_COLORS[4]);
//        colors.add(ColorTemplate.JOYFUL_COLORS[1]);
//        colors.add(ColorTemplate.JOYFUL_COLORS[4]);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xVals, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        mChart.setData(pieData);
        mChart.animateXY(1000, 1000);  //设置动画
        mChart.setCenterTextSize(18f);
        mChart.invalidate();
    }
    private void addList() {
        DB db=DB.getInstance(RoomInfo.this);
        Cursor c=db.loadCourseInfo(building+"-"+room);
        int k=0;
        while(c.moveToNext()){
            int week=c.getInt(c.getColumnIndex("week"));
            int startnum=c.getInt(c.getColumnIndex("startnum"));
            int endnum=c.getInt(c.getColumnIndex("endnum"));
            k++;
            mList.add(new TimeTableModel(k,startnum ,endnum ,week,building+"-"+room));
        }
        if(c!=null)
            c.close();
    }

    protected void onDestroy() {
        super.onDestroy();
         finish();
    }

}
