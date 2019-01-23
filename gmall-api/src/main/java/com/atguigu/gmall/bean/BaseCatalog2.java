package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class BaseCatalog2 implements Serializable {
    private static final long serialVersionUID = -880737533281578621L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String name;
    private String catalog1Id;
    private List<BaseCatalog3> baseCatalog3List;

    public BaseCatalog2() {
    }

    public BaseCatalog2(String id, String name, String catalog1Id, List<BaseCatalog3> baseCatalog3List) {
        this.id = id;
        this.name = name;
        this.catalog1Id = catalog1Id;
        this.baseCatalog3List = baseCatalog3List;
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

    public String getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(String catalog1Id) {
        this.catalog1Id = catalog1Id;
    }

    public List<BaseCatalog3> getBaseCatalog3List() {
        return baseCatalog3List;
    }

    public void setBaseCatalog3List(List<BaseCatalog3> baseCatalog3List) {
        this.baseCatalog3List = baseCatalog3List;
    }
}
