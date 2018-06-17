package com.qiufeng.javacompiler;

import android.text.*;
import android.view.*;
import android.widget.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.io.File;
import java.util.*;
import java.io.*;

import static com.qiufeng.javacompiler.Translator.*;
public class MainActivity extends Activity 
{
	public static Activity cx;
	LayoutInflater li;
	LinearLayout ll;
	//title
	TextView title;
	//input
	String InputPath;
	EditText input;
	ImageButton inputPath;
	FileChooserDialog inputd;
	//lib
	String LibPath;
	EditText lib;
	ImageButton libPath;
	FileChooserDialog libd;
	//output
	String OutputPath;
	EditText output;
	ImageButton outputPath;
	FileChooserDialog outputd;
	//compile
	Button compile;
	//ccc
	TextView ccc;
	File langF;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		//Locale.setDefault(Locale.ENGLISH);
		cx=this;
		langF=new File(getExternalCacheDir(), "lang");
		if(!getExternalCacheDir().exists())
			getExternalCacheDir().mkdirs();
		try
		{
			if (!langF.exists())
			{
				langF.createNewFile();
				FileWriter fw=new FileWriter(langF);
				fw.write(isChinese()?"CHINESE":"ENGLISH");
				fw.close();
			}else{
				FileReader fr=new FileReader(langF);
				StringBuilder sbbb=new StringBuilder();
				int bufff;
				for(;(bufff=fr.read())!=-1;){
					sbbb.append((char)bufff);
				}
				if(sbbb.toString().toLowerCase().contains("chinese")){
					lang=Locale.CHINESE;
				}else{
					lang=Locale.ENGLISH;
				}
			}
		}
		catch (IOException e)
		{}
		setTitle(translateTitle());
		li = LayoutInflater.from(this);
		ll=(LinearLayout)li.inflate(R.layout.main,null);
        setContentView(ll);
		configViews();
    }
	public void configViews(){
		initViews();
		reinitDialogs();
		initListeners();
	}
	public void initViews(){
		title=(TextView)ll.findViewById(R.id.title);
		title.setText(translateTitle());
		input=(EditText)ll.findViewById(R.id.input);
		input.setHint(translateInputText());
		inputPath=(ImageButton)ll.findViewById(R.id.inputPath);
		lib=(EditText)ll.findViewById(R.id.lib);
		lib.setHint(translateLib());
		libPath=(ImageButton)ll.findViewById(R.id.libPath);
		output=(EditText)ll.findViewById(R.id.output);
		output.setHint(translateOutput());
		outputPath=(ImageButton)ll.findViewById(R.id.outputPath);
		compile=(Button)ll.findViewById(R.id.compile);
		compile.setText(translateC());
		ccc=(TextView)ll.findViewById(R.id.ccc);
		ccc.setText(translateCCC());
	}
	public void initListeners(){
		input.addTextChangedListener(new TextWatcher() {             
			@Override    
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override    
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override    
			public void afterTextChanged(Editable s) {     
				InputPath=s.toString();
			}    
		});  
		output.addTextChangedListener(new TextWatcher() {             
			@Override    
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override    
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override    
			public void afterTextChanged(Editable s) {     
				OutputPath=s.toString();
			}    
		});  
		lib.addTextChangedListener(new TextWatcher() {             
			@Override    
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override    
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override    
			public void afterTextChanged(Editable s) {     
				LibPath=s.toString();
			}    
		});  
		inputPath.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				reinitDialogs();
				inputd.setOnConfirmListener(new FileChooserDialog.OnConfirmListener(){
					public void onConfirm(String path,String text){
						InputPath=path;
						input.setText(InputPath);
					}
				});
				inputd.setOnFileSelectedListener(new FileChooserDialog.OnFileSelectedListener(){
					public void onFileSelected(File f){
						InputPath=f.getPath();
						input.setText(InputPath);
					}
				});
				inputd.show();
			}
		});
		outputPath.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				reinitDialogs();
				outputd.setOnConfirmListener(new FileChooserDialog.OnConfirmListener(){
					public void onConfirm(String path,String text){
						OutputPath=path;
						output.setText(OutputPath);
					}
				});
				outputd.setOnFileSelectedListener(new FileChooserDialog.OnFileSelectedListener(){
						public void onFileSelected(File f){
							OutputPath=f.getPath();
							output.setText(OutputPath);
						}
					});
				outputd.show();
			}
		});
		libPath.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					reinitDialogs();
					libd.setOnConfirmListener(new FileChooserDialog.OnConfirmListener(){
							public void onConfirm(String path,String text){
								LibPath=path;
								lib.setText(LibPath);
							}
						});
					libd.setOnFileSelectedListener(new FileChooserDialog.OnFileSelectedListener(){
							public void onFileSelected(File f){
								LibPath=f.getPath();
								lib.setText(LibPath);
							}
						});
					libd.show();
				}
			});
		compile.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				Intent i=new Intent(MainActivity.this,CompileActivity.class);
				i.putExtra("input",InputPath);
				i.putExtra("output",OutputPath);
				i.putExtra("lib",LibPath);
				startActivityForResult(i,0);
			}
		});
	}
	public void reinitDialogs(){
		inputd=new FileChooserDialog(cx,"/sdcard",false,true);
		outputd=new FileChooserDialog(cx,"/sdcard",false,true);
		libd=new FileChooserDialog(cx,"/sdcard",false,true);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		/*switch(resultCode){
			case -1:
				ui.alert("编译失败!","\t错误:\nCause:"+(String)data.getCharSequenceExtra("cause")+"\nMessage:"+(String)data.getCharSequenceExtra("message"),true);
				break;
			case 0:
				ui.alert("编译失败!","",true);
				break;
			case 1:
				ui.alert("编译成功!","恭喜!",true);
				break;
			default:
				ui.alert("未知结果","code="+resultCode,true);
				break;
		}*/
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {  
		MenuInflater inflater = getMenuInflater();  
		inflater.inflate(R.menu.main, menu);  
		menu.findItem(R.id.info).setIcon(R.drawable.ic_information);
		menu.findItem(R.id.helpI).setIcon(R.drawable.ic_web);
		return super.onCreateOptionsMenu(menu);  
	}  

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		// TODO: Implement this method
		if(item.getTitle().toString().equals("关于")){
			startActivity(new Intent(MainActivity.this,AboutActivity.class));
		}else if(item.getTitle().toString().equals("切换语言")){
			if(isChinese()){
				Locale.setDefault(Locale.ENGLISH);
				ui.toast(this,"Language changed to English.Please reboot this app.");
			}else{
				Locale.setDefault(Locale.CHINESE);
				ui.toast(this,"语言已经更改成中文。请重启应用。");
			}
			lang=Locale.getDefault();
			try
			{
				FileWriter fw=new FileWriter(langF);
				fw.write(isChinese()?"CHINESE":"ENGLISH");
				fw.close();
			}
			catch (IOException e)
			{}
			recreate();
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}
