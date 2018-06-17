package com.qiufeng.javacompiler;
import java.util.*;


public class Translator
{
	public static Locale lang=Locale.getDefault();
	public static String translatePos(long line,long col){
		if(isChinese()){
			return "第"+line+"行第"+col+"个字符";
		}else if(isEnglish()){
			return "Line "+line+",column "+col;
		}else{
			return "#"+line+",column "+col;
		}
	}
	public static String translateInputText(){
		if(isChinese()){
			return "请输入java文件夹";
		}else{
			return "Please type in java source directory.";
		}
	}
	public static String translateTitle(){
		if(isChinese()){
			return "Java编译器";
		}else{
			return "Java Compiler";
		}
	}
	public static String translateLib(){
		if(isChinese()){
			return "请输入lib文件夹,可以为空";
		}else{
			return "Please type in lib directory.It can be empty.";
		}
	}
	public static String translateOutput(){
		if(isChinese()){
			return "请输入dex存放文件夹";
		}else{
			return "Please type in directory to save the dex file.";
		}
	}
	public static String translateC(){
		if(isChinese()){
			return "编译";
		}else{
			return "Compile";
		}
	}
	public static String translateCCC(){
		if(isChinese()){
			return "编译大文件时会卡顿或黑屏，等待即可，不会造成影响。";
		}else{
			return "The Screen may be blanked if compiling Huge-Memory-File.Please wait for it.It will not affect anything.";
		}
	}
	public static String translateCFail(){
		if(isChinese()){
			return "编译失败";
		}else{
			return "Compile Failed";
		}
	}
	public static String translateCError(){
		if(isChinese()){
			return "编译出错";
		}else{
			return "Compile Error";
		}
	}
	public static String translateCError2(){
		if(isChinese()){
			return "编译错误";
		}else{
			return "Compile Error";
		}
	}
	public static String translateCWarn(){
		if(isChinese()){
			return "警告";
		}else{
			return "Warning";
		}
	}
	public static String translateCFail2(){
		if(isChinese()){
			return "失败";
		}else{
			return "Failed";
		}
	}
	public static String translateCFileLost(){
		if(isChinese()){
			return "找不到Java文件";
		}else{
			return "Cannot find Java Files";
		}
	}
	public static String translateCSuccess(){
		if(isChinese()){
			return "编译成功！";
		}else{
			return "Compiled Successfully!";
		}
	}
	public static String translateCCongratulations(){
		if(isChinese()){
			return "恭喜！";
		}else{
			return "Congratulations!";
		}
	}
	public static String translateCSorry(){
		if(isChinese()){
			return "抱歉！";
		}else{
			return "Sorry!";
		}
	}
	public static String translateCNeedDir(){
		if(isChinese()){
			return "请输入路径！";
		}else{
			return "Please type in directory path!";
		}
	}
	public static String translateCSyntax(){
		if(isChinese()){
			return "源码文件夹格式错误";
		}else{
			return "Wrong Syntax of Source Directory";
		}
	}
	public static String translateCLostDir(){
		if(isChinese()){
			return "找不到文件夹，请检查构建";
		}else{
			return "Cannot Find Directory.Please Check The File.";
		}
	}
	public static String translateCC(){
		if(isChinese()){
			return "确定";
		}else{
			return "OK";
		}
	}
	static boolean isChinese(){
		return lang.equals(Locale.CHINA)||lang.equals(Locale.CHINESE)||lang.equals(Locale.SIMPLIFIED_CHINESE)||lang.equals(Locale.TRADITIONAL_CHINESE)||lang.equals(Locale.TAIWAN);
	}
	static boolean isEnglish(){
		return lang.equals(Locale.ENGLISH)||lang.equals(Locale.UK)||lang.equals(Locale.US);
	}
}
