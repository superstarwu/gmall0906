package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

public class CartInfo implements Serializable {
    private static final long serialVersionUID = -5520878168671886523L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String skuName;
    private Integer skuNum;
    private String userId;
    private String imgUrl;
    private String skuId;
    private BigDecimal cartPrice;
    private BigDecimal skuPrice;
    private String isChecked;

    public CartInfo() {
    }

    public CartInfo(String skuName, Integer skuNum, String userId, String imgUrl, String skuId, BigDecimal cartPrice, BigDecimal skuPrice, String isChecked) {
        this.skuName = skuName;
        this.skuNum = skuNum;
        this.userId = userId;
        this.imgUrl = imgUrl;
        this.skuId = skuId;
        this.cartPrice = cartPrice;
        this.skuPrice = skuPrice;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
