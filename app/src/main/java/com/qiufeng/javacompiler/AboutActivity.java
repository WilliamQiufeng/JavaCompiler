package com.qiufeng.javacompiler;
import android.app.*;
import android.os.*;
import android.widget.*;
public class AboutActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		getActionBar().setTitle("关于");
		setContentView(R.layout.about);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		String about="作者:秋枫\nCOPYRIGHT 2018\nALL RIGHTS RESERVED";
		TextView tv=(TextView)findViewById(R.id.aboutTextView);
		tv.setText(about);
		
		super.onCreate(savedInstanceState);
	}

}
