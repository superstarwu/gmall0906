package com.atguigu.gmall.bean;

import java.io.Serializable;

public class CartInfo implements Serializable {
    private static final long serialVersionUID = -5520878168671886523L;

    private String id;
    private String name;

    public CartInfo() {
    }

    public CartInfo(String id, String name) {
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
