package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class BaseCatalog3 implements Serializable {
    private static final long serialVersionUID = -4927640216542712007L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String name;
    private String catalog2Id;

    public BaseCatalog3() {
    }

    public BaseCatalog3(String id, String name, String catalog2Id) {
        this.id = id;
        this.name = name;
        this.catalog2Id = catalog2Id;
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

    public String getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(String catalog2Id) {
        this.catalog2Id = catalog2Id;
    }
}
