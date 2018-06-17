package com.qiufeng.javacompiler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHelper
{
	public static boolean copy(File f1,File f2){
		try{
			QTextInputStream fis=new QTextInputStream(f1);
			FileOutputStream fos=new FileOutputStream(f2);
			fis.copyTo(fos);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
