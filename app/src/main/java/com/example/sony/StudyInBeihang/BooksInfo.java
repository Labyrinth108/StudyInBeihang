package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SONY on 2016/3/18.
 */
public class BooksInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinfo);

        TitleView tv=(TitleView)findViewById(R.id.title);
        tv.setTitle("推荐信息");
        tv.setLeftButton("", new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                finish();
            }
        });
        ListView lv=(ListView)findViewById(R.id.lv);
        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.vbook,new String[]{"title","author","img"},
                new int[]{R.id.title,R.id.author,R.id.img});
        lv.setAdapter(adapter);
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "三国演义");
        map.put("author","罗贯中");
        map.put("img", R.drawable.three_nations);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "算法导论");
        map.put("author","Thomas H.Cormen等");
        map.put("img", R.drawable.suandao);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "机器学习实战");
        map.put("author","Peter Harrington");
        map.put("img", R.drawable.machine_learning_action);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "人月神话");
        map.put("author","FrederickP.Brooks.Jr.");
        map.put("img", R.drawable.renyue_mystery);
        list.add(map);
        return list;
    }
}
