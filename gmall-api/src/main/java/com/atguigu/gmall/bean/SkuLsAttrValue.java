package com.atguigu.gmall.bean;

import java.io.Serializable;

public class SkuLsAttrValue implements Serializable{
    private static final long serialVersionUID = -3232206501758882786L;

    private String valueId;

    public SkuLsAttrValue() {
    }

    public SkuLsAttrValue(String valueId) {
        this.valueId = valueId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }
}
