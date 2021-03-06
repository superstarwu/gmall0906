package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class SkuImage implements Serializable{
    private static final long serialVersionUID = 1942913134627250162L;
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String skuId;

    private String imgName;

    private String imgUrl;

    private String spuImgId;

    private String isDefault;

    public SkuImage() {
    }

    public SkuImage(String skuId, String imgName, String imgUrl, String spuImgId, String isDefault) {
        this.skuId = skuId;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.spuImgId = spuImgId;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSpuImgId() {
        return spuImgId;
    }

    public void setSpuImgId(String spuImgId) {
        this.spuImgId = spuImgId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}