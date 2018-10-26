package com.lvqiu.intent_apt.bean;


import java.lang.annotation.ElementType;

public class InjectDesc {
    public ElementType annotationType;
    public String[] interfaces;
    public String superClass;
    public String className;

    public Integer[] fieldValues;
    public String[] fieldTypeNames;
    public String[] fieldNames;

    public String[] methodNames;
}
