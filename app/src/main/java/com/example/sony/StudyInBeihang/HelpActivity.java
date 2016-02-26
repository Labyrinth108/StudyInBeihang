package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.feedback);

		TitleView tv=(TitleView)findViewById(R.id.title);
		tv.setTitle("反馈");
	}

}
