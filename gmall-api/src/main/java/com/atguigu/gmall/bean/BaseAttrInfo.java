package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class BaseAttrInfo implements Serializable{

    private static final long serialVersionUID = 2730965886840089042L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String attrName;
    private String catalog3Id;
    private String isEnabled;
    private List<BaseAttrValue> attrValueList;

    public BaseAttrInfo() {
    }

    public BaseAttrInfo(String id, String attrName, String catalog3Id, String isEnabled, List<BaseAttrValue> attrValueList) {
        this.id = id;
        this.attrName = attrName;
        this.catalog3Id = catalog3Id;
        this.isEnabled = isEnabled;
        this.attrValueList = attrValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<BaseAttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<BaseAttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }
}
