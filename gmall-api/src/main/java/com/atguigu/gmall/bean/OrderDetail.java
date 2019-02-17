package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = -4326230913545876815L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String orderId;
    private String skuId;
    private String skuName;
    private String imgUrl;
    private BigDecimal orderPrice;
    private Integer skuNum;

    @Transient
    private String hasStock;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String skuId, String skuName, String imgUrl, BigDecimal orderPrice, Integer skuNum, String hasStock) {
        this.orderId = orderId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.imgUrl = imgUrl;
        this.orderPrice = orderPrice;
        this.skuNum = skuNum;
        this.hasStock = hasStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public String getHasStock() {
        return hasStock;
    }

    public void setHasStock(String hasStock) {
        this.hasStock = hasStock;
    }
}
