package com.lvqiu.intent_apt.generatefactory.imp;

import com.lvqiu.intent_apt.bean.InjectDesc;
import com.lvqiu.intent_apt.generatefactory.BaseGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;


public class GeneratorForStatic extends BaseGenerator{

    private TypeName activityClassName = ClassName.get("android.app", "Activity").withoutAnnotations();

    public GeneratorForStatic(Filer mFiler, Messager messager) {
       super(mFiler,messager);
    }

    public  void generate(Class<?> clazz,List<InjectDesc> injectDescs) {

        ArrayList<InjectDesc> binderActivities=new ArrayList<>();

        for (InjectDesc injectDesc: injectDescs) {
            generateViewBinder(injectDesc);
            binderActivities.add(injectDesc);
        }


        generateFactory(binderActivities,activityClassName);
    }

    private   void generateFactory(ArrayList<InjectDesc> classes ,TypeName base ){
        MethodSpec method = null;

        //create function
        MethodSpec.Builder main = MethodSpec.methodBuilder("binderViews")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(base , "activity");

        //create function body
        for (InjectDesc ac:  classes) {
            ClassName activity= getClassName(ac.className) ;
            ClassName viewbinder = ClassName.get("com.apt.viewbinder", activity.simpleName()+"ViewBinder");

            main.addCode("if(activity  instanceof $T) {\n",activity );
            main.addStatement("\t$T.InitViews(($T) activity)",viewbinder,activity  );
            main.addCode("\treturn;  \n}\n");
        }
        method=main.build();


        //create java file
        TypeSpec classname = TypeSpec.classBuilder("ViewBinderFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(method)
                .build();

        JavaFile javaFile = JavaFile.builder("com.apt.binderfactory", classname)
                .addFileComment(" This codes are generated automatically. Do not modify!")
                .build();
       // File outputFile = new File("app/src/main/java");
        messager.printMessage(Diagnostic.Kind.NOTE, "create java file is:"+javaFile.packageName+"."+classname.name);
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private   void generateViewBinder(InjectDesc injectDesc )  {
        MethodSpec method = null;

        ClassName activity= getClassName(injectDesc.className);

        MethodSpec.Builder main = MethodSpec.methodBuilder("InitViews")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(activity, "activity");

        for (int i=0;i<injectDesc.fieldTypeNames.length;i++){
            ClassName fileClassName= getClassName(injectDesc.fieldTypeNames[i]);
            main.addStatement("activity.$N = ($T) activity.findViewById("+injectDesc.fieldValues[i]+")",
                    injectDesc.fieldNames[i]  , fileClassName );
        }

        /**
         *
         */
//        for (Field field : activity.getDeclaredFields()) {
//            if (field.isAnnotationPresent(StaticIntentKey.class)) {
//                StaticIntentKey annotation = field.getAnnotation(StaticIntentKey.class);
//                Integer resourceId = annotation.value();
//                main.addStatement("activity.$N = ($T) activity.findViewById("+resourceId+")",field.getName()  ,field.getType() );//使用format
//            }
//        }
        method=main.build();


        TypeSpec classname = TypeSpec.classBuilder(activity.simpleName()+"ViewBinder")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(method)
                .build();

        JavaFile javaFile = JavaFile.builder("com.apt.viewbinder", classname)
                .addFileComment(" This codes are generated automatically. Do not modify!")
                .build();
       // File outputFile = new File("app/src/main/java");
        messager.printMessage(Diagnostic.Kind.NOTE, "create java file is:"+javaFile.packageName+"."+classname.name);
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
