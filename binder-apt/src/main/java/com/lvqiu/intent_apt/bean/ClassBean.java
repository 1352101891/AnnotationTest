package com.lvqiu.intent_apt.bean;

public class ClassBean {
    private String className;
    private int modify;

    public ClassBean(String className, int modify) {
        this.className = className;
        this.modify = modify;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }
}
