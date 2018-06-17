package com.qiufeng.javacompiler;
import java.io.*;
import java.util.regex.*;
public class FileTypes{
	public static final String CREATOR="QiuFeng";
	public static String TYPE_IMAGE="IMAGE";
	public static String TYPE_TEXT="TEXT";
	public static String TYPE_MEDIA="MEDIA";
	public static String TYPE_WEB="WEB";
	public static String TYPE_OTHER="OTHER";
	public static String TYPE_NONE="NONE";
	public static String TYPE_COMPRESS="COMPRESS";
	public static String TYPE_EXECUTABLE="EXECUTABLE";
	public static String[] IMAGE={"PNG","JPG","JPEG","BMP","GIF"};
	public static String[] TEXT={"TXT","MD","XML","PROPERTIES","DB","DAT"};
	public static String[] MEDIA={"MP3","MP4","M4A","OGG","WAV"};
	public static String[] WEB={"HTM","HTML","CSS"};
	public static String[] EXECUTABLE={"JS","JAVA","PY","CPP","C","H"};
	public static String[] COMPRESS={"ZIP","JAR","GZIP","7Z"};
	public static String getType(String NAME){
		NAME=NAME.toUpperCase();
		for(int i=0;i<IMAGE.length;i++){
			if(NAME.equals(IMAGE[i])){
				return TYPE_IMAGE;
			}
		}
		for(int i2=0;i2< TEXT.length;i2++){
			if(NAME.equals(TEXT[i2])){
				return TYPE_TEXT;
			}
		}
		for(int i3=0;i3<MEDIA.length;i3++){
			if(NAME.equals(MEDIA[i3])){
				return TYPE_MEDIA;
			}
		}
		for(int i4=0;i4<EXECUTABLE.length;i4++){
			if(NAME.equals(EXECUTABLE[i4])){
				return TYPE_EXECUTABLE;
			}
		}
		for(int i5=0;i5<WEB.length;i5++){
			if(NAME.equals(WEB[i5])){
				return TYPE_WEB;
			}
		}
		for(int i6=0;i6<COMPRESS.length;i6++){
			if(NAME.equals(COMPRESS[i6])){
				return TYPE_COMPRESS;
			}
		}
		if(NAME.equals("")){
			return TYPE_NONE;
		}
		return TYPE_OTHER;
	}
	public static String getFileMatch(QFile file){
		String FileSimpleName=file.getName();
		String[] res=FileSimpleName.split("\\.");
		if(res.length>1){
			return res[res.length-1].toUpperCase();
		}else{
			return "";
		}
	}
}
