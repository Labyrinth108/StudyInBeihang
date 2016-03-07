package com.example.sony.StudyInBeihang;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.database.DB;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 2016/1/17.
 */
public class details extends FragmentActivity{
    private TitleView mTitle;
    private String building;
    private BarChart chart;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private ViewPager mPageVp;
    /**
     * Tab显示内容TextView
     */
    private TextView mTabChatTv, mTabContactsTv, mTabFriendTv;
    /**
     * Tab的那个引导线
     */
    private ImageView mTabLineIv;
    /**
     * Fragment
     */
    private FirstFloor firstFloorFg;
    private SecondFloor secondFloorFg;
    private ThirdFloor thirdFloorFg;
    //private RoomInfofg roominfo;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newdetail);
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        building = bundle.getString("building");    //获取Bundle里面的字符串

        mTitle = (TitleView) findViewById(R.id.title);
        mTitle.setTitle(R.string.title_details);

        findById();
        init();
        initTabLineWidth();

//        mTitle.setRightButton(R.string.rooms, new TitleView.OnRightButtonClickListener() {
//            @Override
//            public void onClick(View button) {
//
//            }
//        });
//        mTitle.setLeftButton(R.string.back, new TitleView.OnLeftButtonClickListener() {
//            @Override
//            public void onClick(View button) {
//
//            }
//        });
//        chart = (BarChart) findViewById(R.id.chart);
//        BarData data = new BarData(getXAxisValues(), getDataSet());
//        chart.setData(data);
//        chart.setDescription(building);
//        chart.animateXY(2000, 2000);//动画时间
//        chart.setOnChartValueSelectedListener(this);
//        chart.invalidate();
    }

    private void findById() {
        mTabContactsTv = (TextView) this.findViewById(R.id.id_tf_tv);
        mTabChatTv = (TextView) this.findViewById(R.id.id_ff_tv);
        mTabFriendTv = (TextView) this.findViewById(R.id.id_sf_tv);

        mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);
        mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
    }

    private void init() {
        secondFloorFg = new SecondFloor();
        thirdFloorFg = new ThirdFloor();
        firstFloorFg = new FirstFloor();

        mFragmentList.add(firstFloorFg);
        mFragmentList.add(secondFloorFg);
        mFragmentList.add(thirdFloorFg);


        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);

        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabChatTv.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        mTabFriendTv.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        mTabContactsTv.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }
        });

    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabChatTv.setTextColor(Color.BLACK);
        mTabFriendTv.setTextColor(Color.BLACK);
        mTabContactsTv.setTextColor(Color.BLACK);
    }

    private float queryClassroom(String location,String room){
        DB db=new DB(details.this);
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
        for (int index=1;index<=4;index++){
            ArrayList<BarEntry> valueSet = new ArrayList<>();
            int r;
            //计算101室，201室，301室，401室
            for(int i=0;i<4;i++)
            {
                r=(i+1)*100+index;
                float f=queryClassroom(building,r+"");
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
        xAxis.add("1楼");
        xAxis.add("2楼");
        xAxis.add("3楼");
        xAxis.add("4楼");
        return xAxis;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        RectF bounds = chart.getBarBounds((BarEntry) e);
        PointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);
        Intent intent=new Intent(this,RoomInfo.class);
        Bundle b=new Bundle();
        int room=(h.getXIndex()+1)*100+dataSetIndex+1;
        b.putInt("Classroom",room);
        b.putString("Building",building);
        b.putFloat("Percent",e.getVal());
        intent.putExtras(b);
        startActivity(intent);
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
