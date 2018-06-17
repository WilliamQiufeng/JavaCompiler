package com.qiufeng.javacompiler;
import java.io.*;
public class QTextOutputStream extends FileOutputStream{
	public static final String CREATOR="QiuFeng";
	public String fileName;
	public File fileObject;
	public QTextOutputStream(File file) throws FileNotFoundException{
		super(file);
		fileName=file.getPath();
		fileObject=file;
	}
	public void simpleWrite(String str) throws IOException{
        write(str.getBytes(), 0, str.getBytes().length);
	}
}
