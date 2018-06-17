package com.qiufeng.javacompiler;

import android.os.*;
import android.view.*;
import android.widget.*;

import android.app.Activity;
import android.os.Process;
import android.util.Log;
import com.wanjian.cockroach.Cockroach;
import java.io.File;
import com.android.dx.command.Main;

import static com.qiufeng.javacompiler.Translator.*;

public class CompileActivity extends Activity
{
	public static Activity cx;
	public static String InputPath,LibPath,OutputPath;
	public static File i,l,o,m;
	public static LayoutInflater li;
	public static ScrollView sl;
	public static LinearLayout ll;
	public static TextView title,info;
	public static Button res;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setTitle(translateTitle());
		cx=this;
		
		
		install();
		li=LayoutInflater.from(this);
		sl=(ScrollView)li.inflate(R.layout.compile,null);
		ll=(LinearLayout)sl.findViewById(R.id.compileL);
		setContentView(sl);
		readViews();
		res.setText(translateCC());
		res.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
		InputPath=(String)getIntent().getCharSequenceExtra("input");
		OutputPath=(String)getIntent().getCharSequenceExtra("output");
		LibPath=(String)getIntent().getCharSequenceExtra("lib");
		if(LibPath==null){
			LibPath="";
		}
		/*try
		{*/
		if(InputPath==null||OutputPath==null){
			setResult(-1);
			title.setText(translateCFail2());
			info.setText(translateCNeedDir());
		}else if(new File(InputPath).getName().toLowerCase().endsWith("jar")){
			i=new File(InputPath);
			o=new File(OutputPath);
			o.mkdirs();
			Main.main(new String[]{
				"--dex",
				"--output="+new File(o,"output.dex").getPath(),
				i.getPath()
			});
			if(new File(o,"output.dex").exists()){
				setResult(1);
				title.setText(translateCCongratulations());
				info.setText(translateCSuccess());
			}else{
				setResult(0);
				title.setText(translateCSorry());
				info.setText(translateCFail());
			}
		}else{
			i=new File(InputPath);
			o=new File(OutputPath);
			l=new File(LibPath);
			
			o.mkdirs();
			if(LibPath.equals("")||LibPath==null){
				l=new File(o,"libs");
				l.mkdirs();
			}
			/*if(l.isFile()){
				FileHelper.copy(l,new File(l,l.getName()));
			}else if(l.isDirectory()){
				for(File file : l.listFiles()){
					FileHelper.copy(file,new File(m,file.getName()));
				}
			}*/
			if(JavaCompiler.compile(InputPath,l, o, cx)){
				setResult(1);
				title.setText(translateCCongratulations());
				info.setText(translateCSuccess());
			}/*else{
			setResult(0);
			title.setText("抱歉!");
		 	info.setText("编译失败!");
		 	}*/
		}
		res.setEnabled(true);
			
		/*}
		catch (Exception e)
		{
			Intent i=new Intent();
			i.putExtra("cause",e.getCause());
			i.putExtra("message",e.getMessage());
			setResult(-1,i);
			title.setText("报错!");
			info.setText("错误:\nCause:"+e.getCause()+"\nMessage:"+e.getMessage());
		}*/
	}
	public void install(){
		Cockroach.install(new Cockroach.ExceptionHandler() {
			@Override
			public void handlerException(final Thread thread, final Throwable throwable) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						try {
							Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
							ui.alert(cx,"Exception Happend",thread + "\n" + throwable.toString(),true);
						} catch (Throwable e) {
						}
					}
				});
			}
		});
	}
	public void readViews(){
		title=(TextView)ll.findViewById(R.id.compileWait);
		info=(TextView)ll.findViewById(R.id.compileInfo);
		res=(Button)ll.findViewById(R.id.compileRes);
	}
}
