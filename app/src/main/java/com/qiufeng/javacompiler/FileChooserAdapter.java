package com.qiufeng.javacompiler;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.widget.AdapterView.*;
public class FileChooserAdapter extends ArrayAdapter<File>
{
	public String FilePath;
	public String[] paths;
	public Context cx;
	public FileChooserAdapter(Context context,File[] pat) {


		super(context, R.layout.filechooser, pat);
		cx=context;
	}

	private static File[] sortFiles(File file){
		File[] paths=file.listFiles();
		if(paths==null)paths=new File[]{};
		List<File> filess = Arrays.asList(paths);  
		Collections.sort(filess, new Comparator<File>(){  
				@Override  
				public int compare(File o1, File o2) {  
					if(o1.isDirectory() && o2.isFile())  
						return -1;  
					if(o1.isFile() && o2.isDirectory())  
						return 1;  
					return o1.getName().compareTo(o2.getName());  
				}  
			});  
		File[] res=(File[])filess.toArray();
		/*for(File ffff : res){
		 Log.i((ffff.isDirectory()?"dir":"file"),ffff.getPath());
		 }*/
		return res;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater;
		View view;
		inflater = LayoutInflater.from(getContext());
		view = inflater.inflate(R.layout.filechooser, parent, false);

		String text = getItem(position).getName();

		TextView textView = (TextView) view.findViewById(R.id.entryFileName);
		textView.setText(text);
		ImageView imageView = (ImageView) view.findViewById(R.id.entryFileImage);
		if(getItem(position).isDirectory()){
			imageView.setImageResource(R.drawable.ic_action_pick_sdcard);
		}else if(getItem(position).isFile()){
			String type=FileTypes.getType(FileTypes.getFileMatch(new QFile(getItem(position).getPath())));
			if(type.equals(FileTypes.TYPE_TEXT)){
				imageView.setImageResource(R.drawable.ic_file_document_box);
			}else if(type.equals(FileTypes.TYPE_IMAGE)){
				imageView.setImageResource(R.drawable.ic_file_image);
			}else if(type.equals(FileTypes.TYPE_NONE)||type.equals(FileTypes.TYPE_OTHER)){
				imageView.setImageResource(R.drawable.ic_file_outline);
			}else if(type.equals(FileTypes.TYPE_EXECUTABLE)){
				imageView.setImageResource(R.drawable.ic_script);
			}else if(type.equals(FileTypes.TYPE_MEDIA)){
				imageView.setImageResource(R.drawable.ic_beats);
			}else if(type.equals(FileTypes.TYPE_WEB)){
				imageView.setImageResource(R.drawable.ic_web);
			}else if(type.equals(FileTypes.TYPE_COMPRESS)){
				imageView.setImageResource(R.drawable.icon_zip);
			}
		}else{
			imageView.setImageResource(R.drawable.ic_file_delimited);
		}
		if(getItem(position).isHidden()){
			if(getItem(position).isFile())imageView.setImageResource(R.drawable.ic_file_hidden);
			else imageView.setImageResource(R.drawable.ic_action_pick_sdcard_hidden);
		}
		//Log.d("path",new File(FilePath).list()[position]);
		return view;
	}

}
