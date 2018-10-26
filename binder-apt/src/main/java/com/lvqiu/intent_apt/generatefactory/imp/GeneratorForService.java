package com.lvqiu.intent_apt.generatefactory.imp;

import com.lvqiu.intent_apt.bean.ClassBean;
import com.lvqiu.intent_apt.bean.FieldBean;
import com.lvqiu.intent_apt.bean.InjectDesc;
import com.lvqiu.intent_apt.generatefactory.BaseGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;


public class GeneratorForService extends BaseGenerator{

    ArrayList<InjectDesc> autoList=new ArrayList<>();
    ArrayList<InjectDesc> serviceList=new ArrayList<>();

    public GeneratorForService(Filer mFiler, Messager messager) {
       super(mFiler,messager);
    }

    public  void generate(Class<?> annotationClazz,List<InjectDesc> injectDescs) {
        if (injectDescs==null || injectDescs.size()==0){
            return;
        }

        Map<String,Map<FieldBean,ClassBean>> map=new HashMap<>();

        for (InjectDesc injectDesc: injectDescs) {
            if (injectDesc.annotationType== ElementType.FIELD){
                autoList.add(injectDesc);
            }else if (injectDesc.annotationType==ElementType.TYPE){
                serviceList.add(injectDesc);
            }
        }


        if (autoList.size()>0)
        for (InjectDesc interf: autoList) {
            String[] fields= interf.fieldTypeNames;
            if (fields!=null && fields.length>0){
                for (int i=0;i<fields.length;i++) {
                    if (isImp(fields[i],serviceList.get(i).interfaces)){
                        if (map.get(interf.className)==null){
                            Map hashMap=new HashMap<FieldBean, ClassBean>();
                            hashMap.put(fields[i],serviceList.get(i).className);
                            map.put(interf.className,hashMap);
                        }else {
                            Map hashMap=map.get(interf.className);
                            hashMap.put(fields[i],serviceList.get(i).className);
                        }
                    }
                }
            }
        }
        generateFactory(map);
    }

    private void generateFactory(Map<String,Map<FieldBean,ClassBean>> map){

        MethodSpec method = null;

        for (Map.Entry entry: map.entrySet()) {

            String clazz=(String) entry.getKey();
            Map<FieldBean,ClassBean> fieldmap=(Map<FieldBean,ClassBean>)entry.getValue();


            ClassName objectCN= getClassName("java.lang.Object");
            ClassName initClass= getClassName(clazz);

            //create function
            MethodSpec.Builder main = MethodSpec.methodBuilder("autoInitField")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(objectCN , "clazz");

            main.addCode("if(clazz  instanceof $T) {\n", initClass);
            //create function body
            for (Map.Entry ac:  fieldmap.entrySet()) {
                ClassName filedInterFace= getClassName(((FieldBean)ac.getKey()).getFieldType());
                ClassName filedImp= getClassName(((ClassBean)ac.getValue()).getClassName());
                main.addStatement("\t ($T(clazz)).$S= new $T",initClass, ((FieldBean) ac.getKey()).getFieldNaame(),filedImp );
            }
            main.addCode("\treturn;  \n}\n");
            method=main.build();
        }

        //create java file
        TypeSpec classname = TypeSpec.classBuilder("FieldInitFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(method)
                .build();

        JavaFile javaFile = JavaFile.builder("com.apt.Initfactory", classname)
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


    public Class<?> getClass(String fullName){
        try {
            Class<?> clazz=getClass().getClassLoader().loadClass(fullName);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断后面是否实现了前面的接口
     * @param fullname
     * @param fullname_Imp
     * @return
     */
    public boolean isImp(String fullname,String[] fullname_inters){
       if (fullname_inters!=null && fullname_inters.length>0){
           for (String inter: fullname_inters) {
               if (inter.equals(fullname)){
                   return true;
               }
           }
       }
       return false;
    }
}
