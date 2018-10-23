package com.lvqiu.intent_apt.collect.imp;

import com.lvqiu.intent_apt.annotations.StaticBindKey;
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
import javax.tools.Diagnostic;

public class StaticCollectorImp extends BaseAnnotationCollector {


    public StaticCollectorImp(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public List<InjectDesc> findInjectDesc( Set<? extends Element> elements) {
        if (elements==null){
            return null;
        }

        Map<TypeElement, List<Object[]>> targetClassMap = new HashMap<>();

        for (Element element : elements) {

            if (element.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support FIELD!" );
                continue;
            }

            TypeElement classType = (TypeElement) element.getEnclosingElement();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "current classType is:"+classType);

            List<Object[]> nameList = targetClassMap.get(classType);
            if (nameList == null) {
                nameList = new ArrayList<>();
                targetClassMap.put(classType, nameList);
            }

            Set<Modifier> modifiers= element.getModifiers();
            //如果不是public类型，不能类外部初始化，跳过
            if(!modifiers.contains(Modifier.PUBLIC)){
                continue;
            }

            String fieldName = element.getSimpleName().toString();

            String fieldTypeName = element.asType().toString();

            Integer value = element.getAnnotation(StaticBindKey.class).value();

            Object[] names = new Object[]{fieldName, fieldTypeName, value};
            nameList.add(names);
        }

        List<InjectDesc> injectDescList = new ArrayList<>(targetClassMap.size());
        for (Map.Entry<TypeElement, List<Object[]>> entry : targetClassMap.entrySet()) {
            String className = entry.getKey().getQualifiedName().toString();
            System.out.println(className);

            InjectDesc injectDesc = new InjectDesc();
            injectDesc.className = className;
            List<Object[]> value = entry.getValue();
            injectDesc.annotationType= ElementType.FIELD;
            injectDesc.fieldTypeNames = new String[value.size()];
            injectDesc.fieldNames = new String[value.size()];
            injectDesc.fieldValues = new Integer[value.size()];
            for (int i = 0; i < value.size(); i++) {
                Object[] names = value.get(i);
                injectDesc.fieldNames[i] = (String) names[0];
                injectDesc.fieldTypeNames[i] = (String) names[1];
                injectDesc.fieldValues[i] = (Integer) names[2];
            }
            injectDescList.add(injectDesc);
        }

        return injectDescList;
    }
}
