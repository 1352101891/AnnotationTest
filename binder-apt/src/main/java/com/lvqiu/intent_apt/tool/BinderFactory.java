package com.lvqiu.intent_apt.tool;

import android.app.Activity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BinderFactory {
    public static void init(Object object){
        ClassLoader classLoader=object.getClass().getClassLoader();
        try {
            //初始化id控件
            Class<?> classz1=classLoader.loadClass("com.apt.binderfactory.ViewBinderFactory");
            Method binderViews = classz1.getMethod("binderViews",  Activity.class );
            binderViews.invoke(null,object);
            //初始化service接口变量
            Class<?> classz2=classLoader.loadClass("com.apt.Initfactory.FieldInitFactory");
            Method initField = classz2.getMethod("autoInitField",  Object.class );
            initField.invoke(null,object);
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
