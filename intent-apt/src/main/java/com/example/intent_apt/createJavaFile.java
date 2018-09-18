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
            // 定义方法
            MethodSpec.Builder main = MethodSpec.methodBuilder("InitViews")
                    .addModifiers(javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC)//修饰符
                    .returns(void.class)//返回值
                    .addParameter(Activity.class, "activity");//参数合参数类型
            // 反射
            for (Field field : activity.getDeclaredFields()) {
                if (field.isAnnotationPresent(StaticIntentKey.class)) {
                    StaticIntentKey annotation = field.getAnnotation(StaticIntentKey.class);
                    Integer resourceId = annotation.value();
                    main.addStatement("activity.$N = ($T) activity.findViewById("+resourceId+");",field.getName()  ,field.getType() );//使用format
                }
            }
            method=main.build();
        }

        //生成类
        TypeSpec helloWorld = TypeSpec.classBuilder("ViewBinder")//类名字
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC, Modifier.FINAL)//修饰符
                .addMethod(method)//添加方法
                .build();
        //生成一个顶级的java文件描述对象
        JavaFile javaFile = JavaFile.builder("com.example.viewbinder", helloWorld)
                .build();
        File outputFile = new File("javapoettest/src/main/java");
        //输出文件
        try {
            javaFile.writeTo(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
