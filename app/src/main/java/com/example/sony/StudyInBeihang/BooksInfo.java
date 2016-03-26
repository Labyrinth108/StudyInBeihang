package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.database.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SONY on 2016/3/18.
 */
public class BooksInfo extends Activity {
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinfo);
        Bundle bundle=getIntent().getExtras();
        username=bundle.getString("username");
        password=bundle.getString("password");

        TitleView tv=(TitleView)findViewById(R.id.title);
        tv.setTitle(username+"的推荐信息");
        tv.setLeftButton("", new TitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                finish();
            }
        });
        ListView lv=(ListView)findViewById(R.id.lv);
        List<Map<String, Object>> s=getData(username);
        if(s==null)
            Toast.makeText(this, "请联网后使用！", Toast.LENGTH_LONG).show();
        else
        {
            SimpleAdapter adapter = new SimpleAdapter(this,getData(username),R.layout.vbook,new String[]{"code","title","author"},
                    new int[]{R.id.code,R.id.title,R.id.author});
            lv.setAdapter(adapter);
        }
    }
    private List<Map<String, Object>> getData(String username) {
        DB db=DB.getInstance(this);
        Cursor cursor=db.loadBooks(username);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(cursor==null){
            return null;
        }

        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", cursor.getString(cursor.getColumnIndex("code")));
            map.put("title",cursor.getString(cursor.getColumnIndex("name")));
            map.put("author",cursor.getString(cursor.getColumnIndex("author")));
            list.add(map);
            Log.d("Long","Add"+cursor.getString(cursor.getColumnIndex("code")));
        }
        return list;
    }
}
