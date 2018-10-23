package com.lvqiu.intent_apt.bean;

public class FieldBean {
    private String fieldType;
    private String fieldNaame;
    private String value;

    public FieldBean(String fieldType, String fieldNaame) {
        this.fieldType = fieldType;
        this.fieldNaame = fieldNaame;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldNaame() {
        return fieldNaame;
    }

    public void setFieldNaame(String fieldNaame) {
        this.fieldNaame = fieldNaame;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
