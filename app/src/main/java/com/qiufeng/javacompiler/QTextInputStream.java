package com.qiufeng.javacompiler;
import java.io.*;
public class QTextInputStream extends FileInputStream{
	public static final String CREATOR="QiuFeng";
	public String fileName;
	public File fileObject;
	public QTextInputStream(File file) throws FileNotFoundException{
		super(file);
		fileName=file.getPath();
		fileObject=file;
	}
	public int getLineNumber() throws IOException,UnsupportedEncodingException{
		InputStreamReader isr = new FileReader(fileObject);
		BufferedReader br = new BufferedReader(isr);
		String lineTxt;
		int Lines=0;
		while ((lineTxt = br.readLine()) != null) {
			Lines++;
		}
		return Lines;
	}
	public String readFullText() throws UnsupportedEncodingException,IOException{
		StringBuilder res=new StringBuilder();
		InputStreamReader isr = new FileReader(fileObject);
		BufferedReader br = new BufferedReader(isr);
		String lineTxt;
		int currentLine=0;
		while ((lineTxt = br.readLine())!=null) {
			res.append(lineTxt).append("\n");
			currentLine++;
		}
		return res.toString();
	}
	public String[] readFullTextArray() throws Exception{
		String[] res=new String[getLineNumber()];
		InputStreamReader isr = new FileReader(fileObject);
		BufferedReader br = new BufferedReader(isr);
		String lineTxt;
		int currentLine=0;
		while ((lineTxt = br.readLine())!=null) {
			res[currentLine]=lineTxt;
			currentLine++;
		}
		return res;
	}
	public void copyTo(FileOutputStream f) throws IOException{
		int c;
		while((c=read())!=-1)
			f.write(c);
	}
	public void copyTo(String f) throws IOException{
		copyTo(new FileOutputStream(f));
	}
	public static int getTextLineNumber(InputStream stream) throws IOException{
		BufferedReader b=new BufferedReader(new InputStreamReader(stream));
		String c;
		int res=0;
        while((c=b.readLine())!=null){
			res++;
		}
		return res;
	}
	public static String getText(InputStream stream) throws IOException{
		InputStream in = new BufferedInputStream(stream);
		InputStreamReader rd = new InputStreamReader(in);
		int c = 0;
		StringBuffer temp = new StringBuffer();
		while((c = rd.read())!= -1){
			temp.append((char)c);
		}
		in.close();
		return temp.toString();
	}
}
