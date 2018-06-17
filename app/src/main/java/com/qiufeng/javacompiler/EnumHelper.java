package com.qiufeng.javacompiler;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import com.sun.tools.javac.jvm.Target;

public class EnumHelper {
    private Class<? extends Enum> cl;
    public static Target makeEnum(Class cls, String str, Class[] clsArr, Object... objArr) throws Exception {
        int i = 0;
        try {
            if (!Class.forName("java.lang.Enum").isAssignableFrom(cls)) {
                return null;
            }
            Field field;
            Field[] declaredFields = cls.getDeclaredFields();
            Field field2 = (Field) null;
			Field field3;
            for (Field field4 : declaredFields) {
				field3=field4;
                if (field3.getName().contains("$VALUES")) {
                    field2 = field3;
                    break;
                }
            }
            field = field2;
            field.setAccessible(true);
            int length = Array.getLength(field.get(null));
            Class[] clsArr2 = new Class[(objArr.length + 2)];
            try {
                clsArr2[0] = Class.forName("java.lang.String");
                clsArr2[1] = Integer.TYPE;
                Object[] objArr2 = new Object[(objArr.length + 2)];
                objArr2[0] = str;
                objArr2[1] = new Integer(length);
                while (i < objArr.length) {
                    clsArr2[i + 2] = clsArr[i];
                    objArr2[i + 2] = objArr[i];
                    i++;
                }
                Constructor declaredConstructor = cls.getDeclaredConstructor(clsArr2);
                declaredConstructor.setAccessible(true);
                Class[] clsArr3 = (Class[]) null;
                Object[] objArr3 = (Object[]) null;
                return (Target)declaredConstructor.newInstance(objArr2);
            } catch (Throwable e) {
				ui.alert("NoClassDefError",e.getMessage()+"\n"+StackTraceHelper.meanify(e.getStackTrace()),false);
            }
        } catch (Throwable e2) {
            ui.alert("NoClassDefError",e2.getMessage()+"\n"+StackTraceHelper.meanify(e2.getStackTrace()),false);
        }
		return null;
    }

    public static Target makeEnum(Class cls, String str) throws Exception {
        return makeEnum(cls, str, new Class[0], new Object[0]);
    }
}
