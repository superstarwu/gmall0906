package com.atguigu.gmall.bean;

import java.io.Serializable;

public class AttrValueCrumb implements Serializable{
    private static final long serialVersionUID = 5083278251834214506L;

    private String valueName;
    private String urlParam;

    public AttrValueCrumb() {
    }

    public AttrValueCrumb(String valueName, String urlParam) {
        this.valueName = valueName;
        this.urlParam = urlParam;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }
}
