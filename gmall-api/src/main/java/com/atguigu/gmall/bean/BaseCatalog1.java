package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class BaseCatalog1 implements Serializable {
    private static final long serialVersionUID = -7169992838161566139L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String name;

    public BaseCatalog1() {
    }

    public BaseCatalog1(String id, String name) {
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
