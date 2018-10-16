package com.example.intent_apt;

import android.app.Activity;

import com.example.intent_apt.annotations.StaticIntentKey;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.lang.model.element.Modifier;


public class createJavaFile {


    private static void generate(Class<?>   activity)   {
        MethodSpec method = null;
        if (activity.isAssignableFrom(Activity.class)){

            MethodSpec.Builder main = MethodSpec.methodBuilder("InitViews")
                    .addModifiers(javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(Activity.class, "activity");

            for (Field field : activity.getDeclaredFields()) {
                if (field.isAnnotationPresent(StaticIntentKey.class)) {
                    StaticIntentKey annotation = field.getAnnotation(StaticIntentKey.class);
                    Integer resourceId = annotation.value();
                    main.addStatement("activity.$N = ($T) activity.findViewById("+resourceId+")",field.getName()  ,field.getType() );
                }
            }
            method=main.build();
        }


        TypeSpec helloWorld = TypeSpec.classBuilder("ViewBinder")
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(method)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.viewbinder", helloWorld)
                .build();
        File outputFile = new File("javapoettest/src/main/java");

        try {
            javaFile.writeTo(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
