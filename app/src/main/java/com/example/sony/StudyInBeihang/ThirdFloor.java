package com.example.sony.StudyInBeihang;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.database.DB;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ThirdFloor extends Fragment implements OnChartValueSelectedListener {
	private BarChart chart;
	private View view;
	private String building="NMB";

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.viewpager, container,false);

		chart = (BarChart)view.findViewById(R.id.chart);
		BarData data = new BarData(getXAxisValues(), getDataSet());
		chart.setData(data);
		//chart.setDescription(building);
		chart.animateXY(2000, 2000);//动画时间
		chart.setOnChartValueSelectedListener(this);
		chart.invalidate();
		return view;
	}


	private float queryClassroom(String location,String room){
		DB db=new DB(getActivity());
		String p=db.loadClassroom(location,room);
//        if(p=="")
//        {
//            new AlertDialog.Builder(details.this)
//                    .setIcon(R.drawable.internet)
//                    .setTitle("请您连接网络")
//                    .setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//                                    Intent intent=new Intent(details.this,LongRunningService.class);
//                                    startActivity(intent);
//                                    // TODO Auto-generated method stub
//
//                                }
//                            }).setNegativeButton("取消", null).create()
//                    .show();
//        }
		return Float.parseFloat(p);
	}

	private ArrayList<BarDataSet> getDataSet() {

		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		for (int index=1;index<=6;index++){
			ArrayList<BarEntry> valueSet = new ArrayList<>();
			int r=300+index;
			float f=queryClassroom(building,r+"");
			BarEntry bn=new BarEntry(f ,0);
			valueSet.add(bn);

			BarDataSet barDataSet = new BarDataSet(valueSet, "30"+index+"室");
			if(index>5){
				barDataSet.addColor(Color.rgb(148, 212, 212));

			}else
			{
				barDataSet.setColor(ColorTemplate.COLORFUL_COLORS[index - 1]);
			}
			dataSets.add(barDataSet);
		}
		return dataSets;
	}

	private ArrayList<String> getXAxisValues() {
		ArrayList<String> xAxis = new ArrayList<>();
		xAxis.add("3楼");
		return xAxis;
	}
	@Override

	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

		if (e == null)
			return;
		RectF bounds = chart.getBarBounds((BarEntry) e);
		PointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);
		Bundle b=new Bundle();
		int room=(h.getXIndex()+1)*100+dataSetIndex+1;
		b.putInt("Classroom", room);
		b.putString("Building", building);
		b.putFloat("Percent", e.getVal());
		Log.d("Long", room + " " + building + ' ' + e.getVal());
		RoomInfofg RoomFragment=new RoomInfofg();
		RoomFragment.setArguments(b);
		FragmentManager manager=getFragmentManager();
		FragmentTransaction ft;
		ft = manager.beginTransaction();
		ft.replace(R.id.fg, RoomFragment);

		//ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onNothingSelected() {

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
