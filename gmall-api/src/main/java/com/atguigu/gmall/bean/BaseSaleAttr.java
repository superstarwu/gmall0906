package com.atguigu.gmall.bean;

import java.io.Serializable;

public class BaseSaleAttr implements Serializable {
    private static final long serialVersionUID = -256806420106063514L;

    private String id;
    private String name;

    public BaseSaleAttr() {
    }

    public BaseSaleAttr(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
