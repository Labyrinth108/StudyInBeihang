package com.example.sony.StudyInBeihang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.sony.StudyInBeihang.TitleView.OnLeftButtonClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiningroomFragment extends Fragment {

	private View mParent;
	private FragmentActivity mActivity;

	private TitleView mTitle;
	
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static DiningroomFragment newInstance(int index) {
		DiningroomFragment f = new DiningroomFragment();

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
		View view = inflater.inflate(R.layout.fragment_diningroom, container,
				false);

		ListView lv=(ListView)view.findViewById(R.id.lv);
		SimpleAdapter adapter = new SimpleAdapter(this.getActivity(),getData(),R.layout.vlist,new String[]{"title","info","img"},
				new int[]{R.id.title,R.id.info,R.id.img});
		lv.setAdapter(adapter);

		return view;
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "合一食堂二楼");
		map.put("img", R.drawable.newmainbuilding);
		list.add(map);


		map = new HashMap<String, Object>();
		map.put("title", "合一食堂三楼");
		map.put("img", R.drawable.shahelibrary);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "学二食堂");
		map.put("img", R.drawable.seconddininghall);
		list.add(map);


		return list;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.title_diningroom);

	}

	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[1])
				.show(MainActivity.mFragments[0]).commit();
		FragmentIndicator.setIndicator(0);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}
}
