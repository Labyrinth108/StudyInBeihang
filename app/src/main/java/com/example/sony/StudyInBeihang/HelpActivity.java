package com.example.sony.StudyInBeihang;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.DB;
import com.examples.sony.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.feedback);

		final DB db=DB.getInstance(this);
		final TitleView tv=(TitleView)findViewById(R.id.title);
		tv.setTitle("反馈");
		tv.setLeftButton("", new TitleView.OnLeftButtonClickListener() {
			@Override
			public void onClick(View button) {
				finish();
			}
		});

		Button bt=(Button)findViewById(R.id.submit_button);
		final EditText info=(EditText)findViewById(R.id.feedback_content_edit);
		final EditText contact=(EditText)findViewById(R.id.feedback_contact_edit);
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("content", info.getText().toString());
				params.put("contact", contact.getText().toString());
				String url="http://chaopengz.nat123.net:19870/feedback/";
				HttpUtil.submitPostData("",db,params, url,"utf-8", false);
				info.setText("");
				contact.setText("");
				Toast.makeText(HelpActivity.this,"感谢您的建议！",Toast.LENGTH_SHORT).show();
			}
		});
	}
}
