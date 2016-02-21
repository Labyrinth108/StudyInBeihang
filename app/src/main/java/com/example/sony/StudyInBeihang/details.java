package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.database.DB;
import com.examples.sony.util.HttpCallbackListener;
import com.examples.sony.util.HttpUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SONY on 2016/1/17.
 */
public class details extends Activity {
    private TitleView mTitle;
    private DB db;

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
    }

    private float queryClassroom(String location,String room){
        DB db=new DB(details.this);
        String p=db.loadClassroom(location,room);
        return Float.parseFloat(p);
    }

        private ArrayList<BarDataSet> getDataSet() {

            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            for (int index=1;index<=4;index++){
                ArrayList<BarEntry> valueSet = new ArrayList<>();

                int r;
                //计算101室，201室，301室，401室
                for(int i=0;i<4;i++)
                {
                    r=(i+1)*100+index;
                    float f=queryClassroom("NMB",101+"");
                    //float f=queryClassroom("NMB",r+"");
                    BarEntry bn=new BarEntry(f ,i);
                    valueSet.add(bn);
                }
                BarDataSet barDataSet = new BarDataSet(valueSet, "x0"+index+"室");
                barDataSet.setColor(ColorTemplate.COLORFUL_COLORS[index-1]);
                dataSets.add(barDataSet);
            }
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
