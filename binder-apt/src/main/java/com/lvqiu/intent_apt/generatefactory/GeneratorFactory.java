package com.lvqiu.intent_apt.generatefactory;

import com.lvqiu.intent_apt.annotations.StaticBindKey;
import com.lvqiu.intent_apt.annotations.XAutowaired;
import com.lvqiu.intent_apt.annotations.XService;
import com.lvqiu.intent_apt.generatefactory.imp.GeneratorForService;
import com.lvqiu.intent_apt.generatefactory.imp.GeneratorForStatic;
import com.lvqiu.intent_apt.generatefactory.imp.GeneratorForXAuto;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;

public class GeneratorFactory {


    public static BaseGenerator createByAnnotation(Class<?> clazz,ProcessingEnvironment processingEnv){
        BaseGenerator generator=null;

        Messager messager=processingEnv.getMessager();
        Filer mFiler = processingEnv.getFiler();
        if (clazz.equals(StaticBindKey.class)){
            generator=new GeneratorForStatic(mFiler,messager);
        }else if (clazz.equals(XAutowaired.class)){
            generator=new GeneratorForXAuto(mFiler,messager);
        }else if (clazz.equals(XService.class)){
            generator=new GeneratorForService(mFiler,messager);
        }

        return generator;
    }
}
