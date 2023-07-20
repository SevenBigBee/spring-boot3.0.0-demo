package com.laijava.easyRules.vo;

import java.util.List;

public class SrcObj {
    private String name;

    private String remark;

    private String sex;

    private List<SrcObjItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SrcObjItem> getItems() {
        return items;
    }

    public void setItems(List<SrcObjItem> items) {
        this.items = items;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
