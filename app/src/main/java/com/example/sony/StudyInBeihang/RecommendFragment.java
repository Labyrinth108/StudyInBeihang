package com.example.sony.StudyInBeihang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.DB;
import com.example.sony.StudyInBeihang.TitleView.OnLeftButtonClickListener;
import com.examples.sony.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

//荐书fragment
public class RecommendFragment extends Fragment {

	private View mParent;
	private FragmentActivity mActivity;
	private EditText name;
	private EditText realname;
	private EditText keyword;
	private Button button;
	private TitleView mTitle;
	
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static RecommendFragment newInstance(int index) {
		RecommendFragment f = new RecommendFragment();

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
		View view = inflater.inflate(R.layout.fragment_recommend, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();
		name=(EditText)mParent.findViewById(R.id.username);
		realname=(EditText)mParent.findViewById(R.id.name);
		keyword=(EditText)mParent.findViewById(R.id.password);
		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.title_recommend);
		final DB db=DB.getInstance(getActivity());
		Button button=(Button)mActivity.findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String username=name.getText().toString();
				final String key=keyword.getText().toString();
				final String name=realname.getText().toString();
				if(username==""|key==""){
					Toast.makeText(getActivity(),"请填写完整",Toast.LENGTH_LONG).show();
					return;
				}
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", username);
				params.put("name",name);
				params.put("password", key);
				String url="http://vpn.iliana.wang/recommend/";
				HttpUtil.submitPostData(username,db,params,url,"utf-8",true);

				Intent intent=new Intent(mActivity,BooksInfo.class);
				Bundle info=new Bundle();
				info.putString("username",username);
				info.putString("name",name);
				info.putString("password",key);
				intent.putExtras(info);
				startActivity(intent);
			}
		});
	}

	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[2])
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
