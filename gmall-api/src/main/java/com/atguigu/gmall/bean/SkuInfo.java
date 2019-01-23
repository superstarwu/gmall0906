package com.atguigu.gmall.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SkuInfo implements Serializable {
    private static final long serialVersionUID = 7570521952792141565L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Id
    private String id;
    private String spuId;
    private BigDecimal price;
    private String skuName;
    private String skuDesc;
    private String weight;
    private String tmId;
    private String catalog3Id;
    private String skuDefaultImg;
   @Transient
    private List<SkuImage> skuImageList;
    @Transient
    private List<SkuAttrValue> skuAttrValueList;
    @Transient
    private List<SkuSaleAttrValue> skuSaleAttrValueList;
    public SkuInfo() {

    }

    public SkuInfo(String spuId, BigDecimal price, String skuName, String skuDesc, String weight, String tmId, String catalog3Id, String skuDefaultImg, List<SkuImage> skuImageList, List<SkuAttrValue> skuAttrValueList, List<SkuSaleAttrValue> skuSaleAttrValueList) {
        this.spuId = spuId;
        this.price = price;
        this.skuName = skuName;
        this.skuDesc = skuDesc;
        this.weight = weight;
        this.tmId = tmId;
        this.catalog3Id = catalog3Id;
        this.skuDefaultImg = skuDefaultImg;
        this.skuImageList = skuImageList;
        this.skuAttrValueList = skuAttrValueList;
        this.skuSaleAttrValueList = skuSaleAttrValueList;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
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

    public List<SkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<SkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }

    public List<SkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<SkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<SkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }
}
