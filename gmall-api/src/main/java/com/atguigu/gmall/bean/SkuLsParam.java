package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuLsParam implements Serializable{
    private static final long serialVersionUID = 1491679130760592169L;

    private String catalog3Id;
    private String[] valueId;
    private String keyword;
    private BigDecimal price;
    private int pageNo = 1;
    private int pageSize = 20;

    public SkuLsParam() {
    }

    public SkuLsParam(String catalog3Id, String[] valueId, String keyword, BigDecimal price, int pageNo, int pageSize) {
        this.catalog3Id = catalog3Id;
        this.valueId = valueId;
        this.keyword = keyword;
        this.price = price;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
