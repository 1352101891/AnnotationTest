package com.example.intent_apt;


import com.example.intent_apt.annotations.StaticIntentKey;
import com.example.intent_apt.bean.InjectDesc;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;



@SupportedAnnotationTypes(value = {"com.example.intent_apt.annotations.StaticIntentKey"})
@SupportedOptions(value = {"IntentAptIndex", "verbose"})
@AutoService(Processor.class)
public class StaticIntentProcessor extends AbstractProcessor {
    private TypeName activityClassName = ClassName.get("android.app", "Activity").withoutAnnotations();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // StaticMapper的bind方法
        MethodSpec.Builder method = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(activityClassName, "activity");


        return false;
    }

    private List<InjectDesc> findInjectDesc(Set<? extends TypeElement> set, RoundEnvironment re) {

        Map<TypeElement, List<Object[]>> targetClassMap = new HashMap<>();


        Set<? extends Element> elements = re.getElementsAnnotatedWith(StaticIntentKey.class);
        for (Element element : elements) {

            if (element.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support field");
                continue;
            }

            TypeElement classType = (TypeElement) element.getEnclosingElement();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "current classType is:"+classType);

            List<Object[]> nameList = targetClassMap.get(classType);
            if (nameList == null) {
                nameList = new ArrayList<>();
                targetClassMap.put(classType, nameList);
            }

            String fieldName = element.getSimpleName().toString();

            String fieldTypeName = element.asType().toString();

            Integer value = element.getAnnotation(StaticIntentKey.class).value();

            Object[] names = new Object[]{fieldName, fieldTypeName, value};
            nameList.add(names);
        }

        List<InjectDesc> injectDescList = new ArrayList<>(targetClassMap.size());
        for (Map.Entry<TypeElement, List<Object[]>> entry : targetClassMap.entrySet()) {
            String className = entry.getKey().getQualifiedName().toString();
            System.out.println(className);

            InjectDesc injectDesc = new InjectDesc();
            injectDesc.activityName = className;
            List<Object[]> value = entry.getValue();
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


    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 支持java1.8
        return SourceVersion.RELEASE_7;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return super.getCompletions(element, annotation, member, userText);
    }
}
