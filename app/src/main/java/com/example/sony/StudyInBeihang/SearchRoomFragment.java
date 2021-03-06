package com.example.sony.StudyInBeihang;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.database.DB;
import com.example.sony.StudyInBeihang.TitleView.OnLeftButtonClickListener;
import com.example.sony.StudyInBeihang.TitleView.OnRightButtonClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

//自习fragment
public class SearchRoomFragment extends Fragment{
    private View mParent;
    SimpleAdapter adapter;
    ListView lv;
    PtrClassicFrameLayout ptrFrame;
    private FragmentActivity mActivity;
    private TitleView mTitle;
    private DB db;
    /**
     * Create a new instance of DetailsFragment, initialized to show the text at
     * 'index'.
     */
    public static SearchRoomFragment newInstance(int index) {
        SearchRoomFragment f = new SearchRoomFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchroom, container, false);

        db=DB.getInstance(getActivity());

        lv=(ListView)view.findViewById(R.id.lv);
        adapter = new SimpleAdapter(this.getActivity(),getData(),R.layout.vlist,new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.useratio,R.id.img});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(getActivity(), details.class);
                Bundle bundle=new Bundle();
                switch (arg2)
                {
                    //building对应的字段是数据库中对应的location的字段。
                    case 0:bundle.putString("building", "NMB");break;
                    case 1:bundle.putString("building", "ZhuM");break;
                    case 2:bundle.putString("building", "XYLib");break;
                    case 3:bundle.putString("building","J3");break;
                    case 4:bundle.putString("building","J4");break;
                    case 5:bundle.putString("building","J5");break;
                    case 6:bundle.putString("building","ShaheLib");break;
                    default:break;
                }
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        ptrFrame=(PtrClassicFrameLayout)view.findViewById(R.id.ptr_frame);
        ptrFrame.setPtrHandler(new in.srain.cube.views.ptr.PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SimpleAdapter(getContext(), getData(), R.layout.vlist, new String[]{"title", "info", "img"},
                                new int[]{R.id.title, R.id.useratio, R.id.img});
                        lv.setAdapter(adapter);
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }
        });
        return view;
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "新主楼");
        map.put("info","整体使用率："+db.loadUserRatio("NMB"));
        map.put("img", R.drawable.newmainbuilding);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "主M");
        map.put("info","整体使用率："+db.loadUserRatio("ZhuM"));
        map.put("img", R.drawable.zm);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "学院路图书馆");
        map.put("info","整体使用率："+db.loadUserRatio("XYLib"));
        map.put("img", R.drawable.xueyuanlib);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "沙河校区教3");
        map.put("info","整体使用率："+db.loadUserRatio("J3"));
        map.put("img", R.drawable.j3);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "沙河校区教4");
        map.put("info","整体使用率："+db.loadUserRatio("J4"));
        map.put("img", R.drawable.j4);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "沙河校区教5");
        map.put("info","整体使用率："+db.loadUserRatio("J5"));
        map.put("img", R.drawable.j5);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "沙河校区图书馆");
        map.put("info","整体使用率："+db.loadUserRatio("ShaheLib"));
        map.put("img", R.drawable.shahelibrary);
        list.add(map);
        return list;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mParent = getView();

        mTitle = (TitleView) mParent.findViewById(R.id.title);
        mTitle.setTitle(R.string.title_searchroom);

        mTitle.setLeftButton("", new OnLeftButtonClickListener() {
            @Override
            public void onClick(View button) {
                mActivity.finish();
            }
        });
        mTitle.setRightButton("反馈", new OnRightButtonClickListener() {

            @Override
            public void onClick(View button) {
                goHelpActivity();
            }
        });
    }

    private void goHelpActivity() {
        Intent intent = new Intent(mActivity, HelpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
