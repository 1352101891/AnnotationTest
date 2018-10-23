package com.lvqiu;

import android.app.Activity;


import com.lvqiu.intent_apt.annotations.DynamicBindKey;

import java.lang.reflect.Field;

public class DynamicIntentProcessor {

    public static void Init(Activity activity){

        for (Field field : activity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DynamicBindKey.class)) {

                DynamicBindKey annotation = field.getAnnotation(DynamicBindKey.class);
                int resourceId = annotation.value();
                try {
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
