package com.qiufeng.javacompiler;
import java.io.*;
public class QFile extends File{
	public static final String CREATOR="QiuFeng";
	public String fileName;
	public QFile(String s){
		super(s);
		fileName=s;
	}
	public FileInputStream getInputStream() throws FileNotFoundException{
		return new FileInputStream(this);
	}
	public FileOutputStream getOutputStream() throws FileNotFoundException{
		return new FileOutputStream(this);
	}
	public boolean mkdirIfNotExists() throws IOException{
		if(!exists()){
			return mkdir();
		}else{
			return false;
		}
	}
	public boolean mkdirsIfNotExists() throws IOException{
		if(!exists()){
			return mkdirs();
		}else{
			return false;
		}
	}
	public String getFileMatch(){
		return FileTypes.getFileMatch(this);
	}

	public int getLineNumber() throws IOException{
		long fileLength = this.length(); 
		LineNumberReader rf = null; 
		int res=0;
		try { 
			rf = new LineNumberReader(new FileReader(this)); 
			if (rf != null) { 
				int lines = 0; 
				rf.skip(fileLength); 
				lines = rf.getLineNumber();
				rf.close();  
				res=lines;
			} 
		} catch (IOException e) { 
			if (rf != null) { 
				try { 
					rf.close(); 
				} catch (IOException ee) { 
				} 
			} 
			res=0;
		}
		return res+1;
	}
	public String getFileSimpleName(){
		String[] FileSimpleName000=fileName.split(separator);
		String FileSimpleName=FileSimpleName000[FileSimpleName000.length-1];
		return FileSimpleName;
	}
	@Override
	public String toString(){
		return "[QFile "+fileName+"]";
	}
}
