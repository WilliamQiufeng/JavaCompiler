package com.qiufeng.javacompiler;
import compiler.*;
import java.io.*;
import java.util.*;
import javax.tools.*;

import android.app.Activity;
import android.util.Log;
import com.android.dx.command.Main;
import com.sun.tools.javac.jvm.Target;
import java.lang.reflect.Field;

import static com.qiufeng.javacompiler.Translator.*;
public class JavaCompiler
 {
	private static boolean error=false;
	private static StringBuffer ec;
	private static boolean init=false;
	public static final String AIDE_LIB_PATH = "/sdcard/.aide/android.jar";
	public static final String AIDE_PATH = "/sdcard/AppProjects/";
	public static synchronized boolean compile(String name,File lib, File output, Activity ctx) /*throws Exception*/{
		if (!init) {
			try {
				Target jdkn=EnumHelper.makeEnum(Target.class, "JDK1_8", new Class[] {String.class,int.class,int.class}, new Object[]{"1.8", 52, 0});
				if(jdkn==null)return false;
				Field f=Target.class.getDeclaredField("MAX");
				f.setAccessible(true);
				f.set(null, jdkn);
				init = true;
			} catch (Exception e) {
				Log.e("CompileError",e.getMessage());
			}
		}
		error = false;
		ec = new StringBuffer();
		DiagnosticListener<JavaFileObject> listener = new DiagnosticListener<JavaFileObject>() {
			@Override
			public boolean report(Diagnostic<? extends JavaFileObject> r) {
				if (r.getKind().equals(Diagnostic.Kind.ERROR)) {
					error = true;
					ec.append("=========\n").append(Translator.translateCError2()).append("\n").append(r.getSource().getName()).append("\n")
						//.append(r.getCode()).append("\n")
						.append(r.getKind().name()).append(" ").append('\n')
						.append(r.getMessage(Locale.getDefault())).append("\n")
						.append(/*"第").append(r.getLineNumber()).append("行")
						.append("第").append(r.getColumnNumber()).append("个字符\n")*/
						translatePos(r.getLineNumber(),r.getColumnNumber())).append('\n');
				}
				return false;
			}
		};
		File source=new File(name);
		if (!source.exists()) {
			ec.append(translateCLostDir());
			ec.append("\n");
			error = true;
			setC(translateCFail(),ec.toString());
			return false;
		} else if (!source.isDirectory()) {
			ec.append(translateCSyntax());
			ec.append("\n");
			error = true;
			setC(translateCFail(),ec.toString());
			return false;
		}
		if (!output.exists()) {
			output.mkdirs();
		} else if (output.isFile()) {
			output.delete();
			output.mkdirs();
		}
		new File(output,"out").mkdirs();
		Jc4a jc4a = new Jc4a();
		jc4a.setOutPutPath(Collections.singletonList(new File(output,"out")));
		jc4a.setDiagnosticListener(listener);
		try {
			StringWriter out=new StringWriter();
			jc4a.setOut(new PrintWriter(out));
			ArrayList<JavaFile> javaFiles = getJavaFiles(source);
			if (javaFiles.size() != 0) {
				File[] libs=lib.listFiles(new FileFilter() {
						@Override
						public boolean accept(File f) {
							return (f.getName().substring(f.getName().lastIndexOf(".") + 1).equalsIgnoreCase("jar"));
						}
					});
				List<File> libfiles = Arrays.asList(libs);
				jc4a.setClassPath(libfiles);
				
				jc4a.compile(ctx.getClassLoader(), new File(AIDE_LIB_PATH), javaFiles, null);
				if (error) {
					setC(translateCError(),ec.toString());
					return false;
				}
				String args[] = {
					"--dex", "--output="+new File(output,"output.dex").getPath(), new File(output,"out").getPath()//+"/classes.dex"
					//, output.toString()
				};
				Main.main(args);
				//com.android.dx.command.findusages.Main.main
			} else {
				setC(translateCWarn(),translateCFileLost());
				return false;
			}
		} catch (Exception e) {
			setC(translateCError2(),e.getMessage()+"\n\n"+StackTraceHelper.meanify(e.getStackTrace()));
			return false;
		}
		return true;
	}

	private static ArrayList<JavaFile> getJavaFiles(File dir) {
		ArrayList<JavaFile> co=new ArrayList<>();
		parse(dir, co);
		return co;
	}

	private static void parse(File dir, ArrayList<JavaFile> collect) {
		if (dir.isDirectory()) {
			File[] a=dir.listFiles();
			if (a == null) return;
			for (File one : a) parse(one, collect);
		} else {
			if (dir.getName().endsWith(".java")) collect.add(new JavaFile(dir.toURI()));
		}
	}
	public static void setC(String t,String i){
		CompileActivity.title.setText(t);
		CompileActivity.info.setText(i);
	}
}
