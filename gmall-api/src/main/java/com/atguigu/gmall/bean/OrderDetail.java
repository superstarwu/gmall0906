package com.atguigu.gmall.bean;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = -4326230913545876815L;

    private String id;
    private String orderId;
    private String skuId;
    private String skuName;
    private String imgUrl;
    private String orderPrice;
    private String skuNum;

    public OrderDetail() {
    }

    public OrderDetail(String id, String orderId, String skuId, String skuName, String imgUrl, String orderPrice, String skuNum) {
        this.id = id;
        this.orderId = orderId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.imgUrl = imgUrl;
        this.orderPrice = orderPrice;
        this.skuNum = skuNum;
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

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }
}
