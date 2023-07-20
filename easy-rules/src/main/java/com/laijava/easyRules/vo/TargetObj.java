package com.laijava.easyRules.vo;

import java.util.List;

public class TargetObj {

    private String value;

    private  String description;

    private String gender;

    private List<TargetObjItem> body;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TargetObjItem> getBody() {
        return body;
    }

    public void setBody(List<TargetObjItem> body) {
        this.body = body;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
