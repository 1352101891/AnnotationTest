//package com.example.myapplication;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.example.intent_apt.annotations.StaticIntentKey;
//import com.example.myapplication.view.AnnotationActivity;
//import com.example.myapplication.view.MainActivity;
//import com.squareup.javapoet.ClassName;
//import com.squareup.javapoet.JavaFile;
//import com.squareup.javapoet.MethodSpec;
//import com.squareup.javapoet.TypeSpec;
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
//import javax.lang.model.element.Modifier;
//
//
//public class createJavaFile {
//
//    public static void main(String[] args){
//        ArrayList<Class<?>> activities=new ArrayList<>();
//        ArrayList<Class<?>> binderActivities=new ArrayList<>();
//
//        if (AnnotationActivity.class.isAssignableFrom(Activity.class)){
//            Log.e("main()","Annotation is Activity's subClass!");
//        }
//
//        activities.add(AnnotationActivity.class);
//        activities.add(MainActivity.class);
//
//        for (Class<?> c: activities) {
//            generate(c);
//            binderActivities.add(c);
//        }
//        generateFactory(binderActivities,Activity.class);
//    }
//
//    private static void generateFactory(ArrayList<Class<?>> classes ,Class<?> base){
//        MethodSpec method = null;
//        //isAssignableFrom和 instanceof的用法差不多，都是表示是不是子类或者自身类
//        //parent.isAssignableFrom(sub) 和 sub instanceOf parent的效果是一样的
//        if (Activity.class.isAssignableFrom(base)){
//            // 定义方法
//            MethodSpec.Builder main = MethodSpec.methodBuilder("binderViews")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//修饰符
//                    .returns(void.class)//返回值
//                    .addParameter(base, "activity");//参数合参数类型
//
//            for (Class<?> activity:  classes) {
//                ClassName viewbinder = ClassName.get("com.example.viewbinder", activity.getSimpleName()+"ViewBinder");
//
//                main.addCode("if(activity  instanceof $T) {\n",activity);
//                main.addStatement("\t$T.InitViews(($T) activity)",viewbinder,activity );
//                main.addCode("\treturn;  \n}\n");
//            }
//            method=main.build();
//        }
//
//        //生成类
//        TypeSpec helloWorld = TypeSpec.classBuilder("ViewBinderFactory")//类名字
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)//修饰符
//                .addMethod(method)//添加方法
//                .build();
//        //生成一个顶级的java文件描述对象
//        JavaFile javaFile = JavaFile.builder("com.example.viewfactory", helloWorld)
//                .build();
//        File outputFile = new File("app/src/main/java");
//        //输出文件
//        try {
//            javaFile.writeTo(outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void generate(Class<?>   activity)   {
//        MethodSpec method = null;
//        //isAssignableFrom和 instanceof的用法差不多，都是表示是不是子类或者自身类
//        //parent.isAssignableFrom(sub) 和 sub instanceOf parent的效果是一样的
//        if (Activity.class.isAssignableFrom(activity)){
//            // 定义方法
//            MethodSpec.Builder main = MethodSpec.methodBuilder("InitViews")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//修饰符
//                    .returns(void.class)//返回值
//                    .addParameter(activity, "activity");//参数合参数类型
//            // 反射
//            for (Field field : activity.getDeclaredFields()) {
//                if (field.isAnnotationPresent(StaticIntentKey.class)) {
//                    StaticIntentKey annotation = field.getAnnotation(StaticIntentKey.class);
//                    Integer resourceId = annotation.value();
//                    main.addStatement("activity.$N = ($T) activity.findViewById("+resourceId+")",field.getName()  ,field.getType() );//使用format
//                }
//            }
//            method=main.build();
//        }
//
//        //生成类
//        TypeSpec helloWorld = TypeSpec.classBuilder(activity.getSimpleName()+"ViewBinder")//类名字
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)//修饰符
//                .addMethod(method)//添加方法
//                .build();
//        //生成一个顶级的java文件描述对象
//        JavaFile javaFile = JavaFile.builder("com.example.viewbinder", helloWorld)
//                .build();
//        File outputFile = new File("app/src/main/java");
//        //输出文件
//        try {
//            javaFile.writeTo(outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
