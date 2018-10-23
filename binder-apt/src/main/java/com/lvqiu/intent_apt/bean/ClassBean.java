package com.lvqiu.intent_apt.bean;

import java.lang.reflect.Modifier;

public class ClassBean {
    private String className;
    private Modifier modify;

    public ClassBean(String className, Modifier modify) {
        this.className = className;
        this.modify = modify;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Modifier getModify() {
        return modify;
    }

    public void setModify(Modifier modify) {
        this.modify = modify;
    }
}
