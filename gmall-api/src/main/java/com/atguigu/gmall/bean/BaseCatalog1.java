package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

public class BaseCatalog1 implements Serializable {
    private static final long serialVersionUID = -7169992838161566139L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String name;
    private List<BaseCatalog2> baseCatalog2List;

    public BaseCatalog1() {
    }

    public BaseCatalog1(String id, String name, List<BaseCatalog2> baseCatalog2List) {
        this.id = id;
        this.name = name;
        this.baseCatalog2List = baseCatalog2List;
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

    public List<BaseCatalog2> getBaseCatalog2List() {
        return baseCatalog2List;
    }

    public void setBaseCatalog2List(List<BaseCatalog2> baseCatalog2List) {
        this.baseCatalog2List = baseCatalog2List;
    }
}
