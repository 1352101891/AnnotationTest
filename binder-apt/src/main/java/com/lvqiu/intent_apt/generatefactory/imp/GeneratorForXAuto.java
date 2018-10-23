package com.lvqiu.intent_apt.generatefactory.imp;

import com.lvqiu.intent_apt.bean.InjectDesc;
import com.lvqiu.intent_apt.generatefactory.BaseGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;


public class GeneratorForXAuto extends BaseGenerator{


    public GeneratorForXAuto(Filer mFiler, Messager messager) {
       super(mFiler,messager);
    }

    public  void generate(Class<?> clazz,List<InjectDesc> injectDescs) {

    }

}
