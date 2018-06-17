package com.qiufeng.javacompiler;

public class StackTraceHelper
{
	public static String meanify(StackTraceElement[] ste){
		StringBuilder sb=new StringBuilder();
		for(StackTraceElement i : ste){
			sb
				.append("class "+i.getClassName()+" "+(
					i.isNativeMethod()?"native ":"")+"method "+i.getMethodName())
				.append("("+i.getFileName()+" #"+i.getLineNumber()+")")
				.append('\n');
		}
		return sb.toString();
	}
}
