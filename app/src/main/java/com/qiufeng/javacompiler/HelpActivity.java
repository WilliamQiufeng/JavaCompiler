package com.qiufeng.javacompiler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tv=(TextView)findViewById(R.id.helpMessage);
		tv.setText(helpText);
	}
	public static String helpText=(
		"    这是一个可以合并txt文档的应用。"+
		"\n"+
		"第一个输入框:\n"+
		"    文件路径。可以选择文件夹或文件，可多选。\n"+
		"第二,三个输入框:\n"+
		"    选择每个txt文本前后需要增加的文本。\n\n"+
		"    确定后将会生成文本。选择路径并且在输入框内输入文件名即可。"
	);
}
