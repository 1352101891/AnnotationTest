package com.lvqiu.intent_apt.collect;

import com.lvqiu.intent_apt.annotations.StaticBindKey;
import com.lvqiu.intent_apt.annotations.XAutowaired;
import com.lvqiu.intent_apt.annotations.XService;
import com.lvqiu.intent_apt.collect.imp.StaticCollectorImp;
import com.lvqiu.intent_apt.collect.imp.XAutoCollectorImp;
import com.lvqiu.intent_apt.collect.imp.XServiceCollectorImp;

import javax.annotation.processing.ProcessingEnvironment;

public class CollectorFactory {


    public static BaseAnnotationCollector create(Class<?> clazz, ProcessingEnvironment processingEnv){
        BaseAnnotationCollector collector=null;


        if (clazz.equals(StaticBindKey.class)){
            collector=new StaticCollectorImp(processingEnv);
        }else if (clazz.equals(XAutowaired.class)){
            collector=new XAutoCollectorImp(processingEnv);
        }else if (clazz.equals(XService.class)){
            collector=new XServiceCollectorImp(processingEnv);
        }

        return collector;
    }
}
