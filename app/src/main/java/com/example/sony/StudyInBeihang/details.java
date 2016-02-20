package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by SONY on 2016/1/17.
 */
public class details extends Activity {
    private TitleView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.details);

        mTitle = (TitleView) findViewById(R.id.title);
        mTitle.setTitle(R.string.title_details);
        mTitle.setRightButton(R.string.rooms, new TitleView.OnRightButtonClickListener() {
            @Override
            public void onClick(View button) {

            }
        });
        mTitle.setLeftButton(R.string.back, new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {

            }
        });
        BarChart chart = (BarChart) findViewById(R.id.chart);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("J3");
        chart.animateXY(2000, 2000);//动画时间
        chart.invalidate();
        //chart.setDrawValueAboveBar(true);
    }

        private ArrayList<BarDataSet> getDataSet() {
            ArrayList<BarDataSet> dataSets = null;

            ArrayList<BarEntry> valueSet1 = new ArrayList<>();
            BarEntry v1e1 = new BarEntry(0.26f, 0); //J3-101
            valueSet1.add(v1e1);
            BarEntry v1e2 = new BarEntry(0.64f, 1); //
            valueSet1.add(v1e2);
            BarEntry v1e3 = new BarEntry(0.50f, 2); //
            valueSet1.add(v1e3);
            BarEntry v1e4 = new BarEntry(0.45f, 3); //
            valueSet1.add(v1e4);

            ArrayList<BarEntry> valueSet2 = new ArrayList<>();
            BarEntry v2e1 = new BarEntry(0.24f, 0); //
            valueSet2.add(v2e1);
            BarEntry v2e2 = new BarEntry(0.41f, 1); //
            valueSet2.add(v2e2);
            BarEntry v2e3 = new BarEntry(0.22f, 2); //
            valueSet2.add(v2e3);
            BarEntry v2e4 = new BarEntry(0.33f, 3); //
            valueSet2.add(v2e4);

            ArrayList<BarEntry> valueSet3 = new ArrayList<>();
            BarEntry v3e1 = new BarEntry(0.44f, 0); //
            valueSet3.add(v3e1);
            BarEntry v3e2 = new BarEntry(0.31f, 1); //
            valueSet3.add(v3e2);
            BarEntry v3e3 = new BarEntry(0.02f, 2); //
            valueSet3.add(v3e3);
            BarEntry v3e4 = new BarEntry(0.23f, 3); //
            valueSet3.add(v3e4);

            ArrayList<BarEntry> valueSet4 = new ArrayList<>();
            BarEntry v4e1 = new BarEntry(0.12f, 0); //
            valueSet4.add(v4e1);
            BarEntry v4e2 = new BarEntry(0.31f, 1); //
            valueSet4.add(v4e2);
            BarEntry v4e3 = new BarEntry(0.12f, 2); //
            valueSet4.add(v4e3);
            BarEntry v4e4 = new BarEntry(0.03f, 3); //
            valueSet4.add(v4e4);

            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "x01室");
            barDataSet1.setColor(ColorTemplate.COLORFUL_COLORS[0]);

            // barDataSet1.setColor(Color.rgb(0, 155, 0));
            BarDataSet barDataSet2 = new BarDataSet(valueSet2, "x02室");
            barDataSet2.setColor(ColorTemplate.COLORFUL_COLORS[1]);

            BarDataSet barDataSet3 = new BarDataSet(valueSet3, "x10室");
            barDataSet3.setColor(ColorTemplate.COLORFUL_COLORS[2]);

            BarDataSet barDataSet4 = new BarDataSet(valueSet4, "x11室");
            barDataSet4.setColor(ColorTemplate.COLORFUL_COLORS[3]);

            dataSets = new ArrayList<BarDataSet>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            dataSets.add(barDataSet3);
            dataSets.add(barDataSet4);
            return dataSets;
        }

        private ArrayList<String> getXAxisValues() {
            ArrayList<String> xAxis = new ArrayList<>();
            xAxis.add("J3-1楼");
            xAxis.add("J3-2楼");
            xAxis.add("J3-3楼");
            xAxis.add("J3-4楼");
            return xAxis;
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
