package com.lvqiu.intent_apt.collect;


import com.lvqiu.intent_apt.bean.InjectDesc;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class BaseAnnotationCollector {
    public ProcessingEnvironment processingEnv;

    public BaseAnnotationCollector(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract List<InjectDesc> findInjectDesc( Set<? extends Element> elements );

    public ElementKind getKind(ElementType elementType){
    if (elementType==ElementType.TYPE){
        return ElementKind.CLASS;
    }else if (elementType==ElementType.FIELD){
        return ElementKind.FIELD;
    }else if (elementType==ElementType.METHOD){
        return ElementKind.METHOD;
    }
    return null;
    }
}
