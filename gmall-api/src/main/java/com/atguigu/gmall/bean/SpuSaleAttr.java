package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SpuSaleAttr implements Serializable {
    private static final long serialVersionUID = 6817227497489344631L;

    @Column
    @Id
    private String id;
    private String spuId;
    private String saleAttrId;
    private String saleAttrName;
    @Transient
    private List<SpuSaleAttrValue> spuSaleAttrValueList;

    @Transient
    private Map spuSaleAttrValueJson;

    public SpuSaleAttr() {
    }

    public SpuSaleAttr(String id, String spuId, String saleAttrId, String saleAttrName, List<SpuSaleAttrValue> spuSaleAttrValueList, Map spuSaleAttrValueJson) {
        this.id = id;
        this.spuId = spuId;
        this.saleAttrId = saleAttrId;
        this.saleAttrName = saleAttrName;
        this.spuSaleAttrValueList = spuSaleAttrValueList;
        this.spuSaleAttrValueJson = spuSaleAttrValueJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(String saleAttrId) {
        this.saleAttrId = saleAttrId;
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = saleAttrName;
    }

    public List<SpuSaleAttrValue> getSpuSaleAttrValueList() {
        return spuSaleAttrValueList;
    }

    public void setSpuSaleAttrValueList(List<SpuSaleAttrValue> spuSaleAttrValueList) {
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public Map getSpuSaleAttrValueJson() {
        return spuSaleAttrValueJson;
    }

    public void setSpuSaleAttrValueJson(Map spuSaleAttrValueJson) {
        this.spuSaleAttrValueJson = spuSaleAttrValueJson;
    }
}
