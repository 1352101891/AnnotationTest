package com.example.myapplication.processor;

import android.app.Activity;
import com.example.intent_apt.annotations.DynamicIntentKey;
import java.lang.reflect.Field;

//使用反射，运行时解析注解，然后对注解进行处理，效率比静态注解，编译时更低。
public class DynamicIntentProcessor {

    public static void Init(Activity activity){

        // 反射
        for (Field field : activity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DynamicIntentKey.class)) {

                // 获取注解
                DynamicIntentKey annotation = field.getAnnotation(DynamicIntentKey.class);
                int resourceId = annotation.value();
                try {
                    // 插入值
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(resourceId));
                    field.setAccessible(accessible);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
