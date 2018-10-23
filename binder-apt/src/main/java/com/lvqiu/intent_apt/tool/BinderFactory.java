package com.lvqiu.intent_apt.tool;

import android.app.Activity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BinderFactory {
    public static void init(Activity activity){
        try {
            Class<?> classz=activity.getClassLoader().loadClass("com.apt.binderfactory.ViewBinderFactory");
            Method binderViews = classz.getMethod("binderViews",  Activity.class );
            binderViews.invoke(null,activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
