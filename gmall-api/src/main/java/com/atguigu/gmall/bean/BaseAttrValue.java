package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

public class BaseAttrValue implements Serializable {

    private static final long serialVersionUID = -3073708504838706788L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String valueName;
    private String attrId;
    private String isEnabled;
    @Transient
    private String urlParam;

    public BaseAttrValue() {
    }

    public BaseAttrValue(String id, String valueName, String attrId, String isEnabled, String urlParam) {
        this.id = id;
        this.valueName = valueName;
        this.attrId = attrId;
        this.isEnabled = isEnabled;
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

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }
}
