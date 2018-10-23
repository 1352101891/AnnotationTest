package com.lvqiu.intent_apt.staticprocessor;


import com.lvqiu.intent_apt.annotations.XAutowaired;
import com.lvqiu.intent_apt.annotations.XService;
import com.lvqiu.intent_apt.bean.InjectDesc;
import com.google.auto.service.AutoService;
import com.lvqiu.intent_apt.collect.BaseAnnotationCollector;
import com.lvqiu.intent_apt.collect.CollectorFactory;
import com.lvqiu.intent_apt.generatefactory.BaseGenerator;
import com.lvqiu.intent_apt.generatefactory.GeneratorFactory;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

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


@SupportedAnnotationTypes(value = {"com.lvqiu.intent_apt.annotations.StaticIntentKey"
        ,"com.lvqiu.intent_apt.annotations.XAutowaired"
        ,"com.lvqiu.intent_apt.annotations.XService"})
@SupportedOptions(value = {"IntentAptIndex", "verbose"})
@AutoService(Processor.class)
public class StaticIntentProcessor extends AbstractProcessor {
    private final String OPTION_INDEX="IntentAptIndex";
    private final String OPTION_VERBOSE="verbose";


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        String index = processingEnv.getOptions().get(OPTION_INDEX);
        String verbose = processingEnv.getOptions().get(OPTION_VERBOSE);

        Set<String> annotationSet= getSupportedAnnotationTypes();
        HashMap<Class<?>,List<InjectDesc>> annotationToClassSetMap =findAllDesc( roundEnv,annotationSet);

        //first filed deal
        for (Class<?> clazz: annotationToClassSetMap.keySet()) {
            if (!clazz.getSimpleName().equals(XService.class.getSimpleName())) {
                BaseGenerator generator = GeneratorFactory.createByAnnotation(clazz, processingEnv);
                generator.generate(clazz,annotationToClassSetMap.get(clazz));
            }
        }

        //last deal service class
        List<InjectDesc> list= annotationToClassSetMap.get(XAutowaired.class);
        list.addAll(annotationToClassSetMap.get(XService.class));
        BaseGenerator generator = GeneratorFactory.createByAnnotation(XService.class, processingEnv);
        generator.generate(XService.class,list);

        return false;
    }

    private HashMap<Class<?>,List<InjectDesc>> findAllDesc(RoundEnvironment re,Set<String> anoSet) {
        HashMap<Class<?>,List<InjectDesc>> annotationToClassSetMap=new HashMap<>();
        for (String annoName: anoSet) {
            try {
                Class<? extends Annotation> classz= (Class<? extends Annotation>) getClass().getClassLoader().loadClass(annoName);;
                Target annotation = classz.getAnnotation(Target.class);
                if (annotation==null || annotation.value().length==0) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "not declare the annotation ElementType!");
                    continue;
                }

                Set<? extends Element> elements = re.getElementsAnnotatedWith(classz);
                BaseAnnotationCollector collector= CollectorFactory.create(classz,processingEnv);
                annotationToClassSetMap.put(classz,collector.findInjectDesc(elements));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return annotationToClassSetMap;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 支持java1.7
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
