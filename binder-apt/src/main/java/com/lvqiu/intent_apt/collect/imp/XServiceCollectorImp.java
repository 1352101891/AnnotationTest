package com.lvqiu.intent_apt.collect.imp;


import com.lvqiu.intent_apt.bean.InjectDesc;
import com.lvqiu.intent_apt.collect.BaseAnnotationCollector;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

public class XServiceCollectorImp extends BaseAnnotationCollector {


    public XServiceCollectorImp(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public List<InjectDesc> findInjectDesc( Set<? extends Element> elements) {
        if (elements==null || elements.size()==0){
            return null;
        }

        Map<TypeElement, List<Object[]>> targetClassMap = new HashMap<>();

        for (Element element : elements) {

            Set<Modifier> modifiers= element.getModifiers();
            //如果不是public类型，不能类外部初始化，跳过
            if(!modifiers.contains(Modifier.PUBLIC)){
                continue;
            }
            if (element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support CLASS!" );
                continue;
            }

            TypeElement classType = (TypeElement) element;
            List<? extends TypeMirror> list= ((TypeElement) element).getInterfaces();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "current classType is:"+classType);

            List<Object[]> nameList = targetClassMap.get(classType);
            if (nameList == null) {
                nameList = new ArrayList<>();
                targetClassMap.put(classType, nameList);
            }

            if (list!=null && list.size()>0)
                for (TypeMirror t:list) {
                    nameList.add(new Object[]{t.toString()});
                }
        }

        List<InjectDesc> injectDescList = new ArrayList<>(targetClassMap.size());
        for (Map.Entry<TypeElement, List<Object[]>> entry : targetClassMap.entrySet()) {
            String className = entry.getKey().getQualifiedName().toString();
            System.out.println(className);

            InjectDesc injectDesc = new InjectDesc();
            injectDesc.className = className;
            injectDesc.annotationType= ElementType.TYPE;

            List<Object[]> list= entry.getValue();
            if (list.size()>0){
                injectDesc.interfaces=new String[list.size()];
                for (int i=0;i<list.size();i++) {
                    injectDesc.interfaces[i]= (String) list.get(i)[0];
                }
            }

            injectDescList.add(injectDesc);
        }

        return injectDescList;
    }
}
