package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;

public class BaseAttrValue implements Serializable {

    private static final long serialVersionUID = -3073708504838706788L;

    @Id
    @Column
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;
//    @Column
//    private String isEnabled;
    @Transient
    private String urlParam;

    public BaseAttrValue() {
    }

    public BaseAttrValue(String id, String valueName, String attrId, String urlParam) {
        this.id = id;
        this.valueName = valueName;
        this.attrId = attrId;
        this.urlParam = urlParam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }
}
