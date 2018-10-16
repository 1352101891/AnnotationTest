package com.example.intent_apt.processor;

import android.app.Activity;
import com.example.intent_apt.annotations.DynamicIntentKey;
import java.lang.reflect.Field;

public class DynamicIntentProcessor {

    public static void Init(Activity activity){

        for (Field field : activity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DynamicIntentKey.class)) {

                DynamicIntentKey annotation = field.getAnnotation(DynamicIntentKey.class);
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
