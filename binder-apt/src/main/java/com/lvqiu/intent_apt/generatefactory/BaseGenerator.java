package com.lvqiu.intent_apt.generatefactory;

import com.lvqiu.intent_apt.bean.InjectDesc;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;

public abstract class BaseGenerator {
    public Filer mFiler;
    public Messager messager;

    public BaseGenerator(Filer mFiler, Messager messager) {
        this.mFiler = mFiler;
        this.messager = messager;
    }

    public abstract void generate(Class<?> clazz,List<InjectDesc> injectDescs);

    public ClassName getClassName(String className){
        //parse classname to ClassName
        String packageD = className.substring(0, className.lastIndexOf('.'));
        String name = className.substring(className.lastIndexOf('.') + 1);
        return ClassName.get(packageD, name);
    }

    private TypeName typeName(String className) {
        return getClassName(className).withoutAnnotations();
    }

}
