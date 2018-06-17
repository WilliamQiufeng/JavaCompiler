package com.qiufeng.javacompiler;
import android.app.*;
import android.content.*;
import java.io.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.widget.TableRow.*;
import android.graphics.*;
import android.util.*;
public class FileChooserDialog extends Dialog
{
	public interface OnFileSelectedListener{
		public void onFileSelected(File file);
	}
	public HashSet<OnFileSelectedListener> listener=new HashSet();
	public interface OnDirectoryClickedListener{
		public void OnDirectoryClicked(File file);
	}
	public HashSet<OnDirectoryClickedListener> odcl=new HashSet();
	public interface OnConfirmListener{
		public void onConfirm(String path,String text)
	}
	public HashSet<OnConfirmListener> ocl=new HashSet<OnConfirmListener>();
	File[] files;
	String currentPath;
	public File fileChosen;
	String[] filepaths;
	String[] filenames;
	FileChooserAdapter adapter;
	public Context cx;
	ListView listView;
	LinearLayout ll;
	LinearLayout layout;
	EditText et;
	public FileChooserDialog(final Context cx,String path,boolean isSave,boolean isChoosingDir){
		super(cx);
		this.cx=cx;
		initViews(isSave,isChoosingDir);
		updateList(path);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
				public void onItemClick(AdapterView av,View view,int index,long nothing){
					if(files[index].isDirectory()){
						notifyODC(files[index]);
						updateList(files[index].getPath());
						setTitle(currentPath);
					}else if(files[index].isFile()){
						fileChosen=files[index];
						notifyFS(files[index]);
						dismiss();
					}
				}
			});
		setTitle(currentPath);
	}
	public FileChooserDialog(final Context cx,String path){
		this(cx,path,false,false);
	}
	private void initViews(boolean isSave,boolean isChoosingDir){
		listView=new ListView(cx);
		ll=new LinearLayout(cx);
		ll.setOrientation(ll.VERTICAL);
		ll.setGravity(Gravity.CENTER);
		et=new EditText(cx);
		if(isSave){
			ll.addView(et,new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,100));
		}
		ll.addView(listView,new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,700));
		layout=(LinearLayout)LayoutInflater.from(cx).inflate(R.layout.fcdbtns,null);
		Button lf=(Button)layout.findViewById(R.id.fcdbtnlf);
		Button cancelB=(Button)layout.findViewById(R.id.fcdbtnc);
		if(isSave||isChoosingDir){
			cancelB.setText("确定");
		}
		lf.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view){
					if(!(currentPath.equals("/sdcard")||currentPath.equals("/storage"))){
						updateList(new File(currentPath).getParent());
						setTitle(currentPath);
					}else{
						ui.toast(cx,"无法访问子文件夹!");
					}
				}
			});
		cancelB.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view){
					if(et==null)notifyOC(currentPath,"");
					else notifyOC(currentPath,getEditText());
					dismiss();
				}
			});
		ll.addView(layout);
		setContentView(ll);
	}
	public String getEditText(){
		return et.getText().toString();
	}
	public void setEditHint(String str){
		et.setHint(str);
	}
	private File[] sortFiles(File file){
		File[] paths=file.listFiles();
		if(paths==null)paths=new File[]{};
		Arrays.sort(paths, new Comparator<File>(){  
				@Override  
				public int compare(File o1, File o2) {  
					if(o1.isDirectory() && o2.isFile())  
						return -1;  
					if(o1.isFile() && o2.isDirectory())  
						return 1;  
					return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());  
				}  
			});  
		return paths;
	}
	public FileChooserDialog setOnFileSelectedListener(OnFileSelectedListener li){
		listener=new HashSet<OnFileSelectedListener>();
		listener.add(li);
		return this;
	}
	private void notifyFS(File file){
		Iterator iter = listener.iterator();  
		while (iter.hasNext()) {  
			OnFileSelectedListener listener = (OnFileSelectedListener) iter.next();  
			listener.onFileSelected(file);  
		}
	}
	public FileChooserDialog setOnDirectoryClickedListener(OnDirectoryClickedListener li){
		odcl=new HashSet<OnDirectoryClickedListener>();
		odcl.add(li);
		return this;
	}
	private void notifyODC(File file){
		Iterator iter = odcl.iterator();  
		while (iter.hasNext()) {  
			OnDirectoryClickedListener listener = (OnDirectoryClickedListener) iter.next();  
			listener.OnDirectoryClicked(file);  
		}
	}
	public FileChooserDialog setOnConfirmListener(OnConfirmListener li){
		ocl=new HashSet<OnConfirmListener>();
		ocl.add(li);
		return this;
	}
	private void notifyOC(String file,String text){
		Iterator iter = ocl.iterator();  
		while (iter.hasNext()) {  
			OnConfirmListener listener = (OnConfirmListener) iter.next();  
			listener.onConfirm(file,text);  
		}
	}
	private void updateList(String path){
		currentPath=path;
		files= sortFiles(new File(path));
		adapter = new FileChooserAdapter(cx, sortFiles(new File(path)));

		listView.setAdapter(adapter);
	}
}
