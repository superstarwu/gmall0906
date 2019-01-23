package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SkuLsInfo implements Serializable{
    private static final long serialVersionUID = 5193344976162818049L;
    private String id;
    private BigDecimal price;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private String skuDefaultImg;
    private String weight;
    private Long hotScore = 0L;
    private List<SkuLsAttrValue> skuAttrValueList;

    public SkuLsInfo() {
    }

    public SkuLsInfo(String id, BigDecimal price, String skuName, String skuDesc, String catalog3Id, String skuDefaultImg, String weight, Long hotScore, List<SkuLsAttrValue> skuAttrValueList) {
        this.id = id;
        this.price = price;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.catalog3Id = catalog3Id;
        this.skuDefaultImg = skuDefaultImg;
        this.weight = weight;
        this.hotScore = hotScore;
        this.skuAttrValueList = skuAttrValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Long getHotScore() {
        return hotScore;
    }

    public void setHotScore(Long hotScore) {
        this.hotScore = hotScore;
    }

    public List<SkuLsAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuLsAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }
}
