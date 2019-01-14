package com.atguigu.gmall.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 8710785889422923958L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String consignee;
    private String consigneeTel;
    private String totalAmount;
    private String orderStatus;
    private String userId;
    private String paymentWay;
    private String deliveryAddress;
    private String orderComment;
    private String tradeBody;
    private String createTime;
    private String expireTime;
    private String processStatus;
    private String trackingNo;
    private String parentOrderId;
    private String outTradeNo;
    @Transient
    private List<OrderDetail> orderDetailList;

    @Transient
    private List<OrderInfo> orderSubList;

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<OrderInfo> getOrderSubList() {
        return orderSubList;
    }

    public void setOrderSubList(List<OrderInfo> orderSubList) {
        this.orderSubList = orderSubList;
    }

    public OrderInfo() {
    }

    public OrderInfo(String id, String consignee, String consigneeTel,
                     String totalAmount, String orderStatus, String userId,
                     String paymentWay, String deliveryAddress, String orderComment,
                     String tradeBody, String createTime, String expireTime, String processStatus,
                     String trackingNo, String parentOrderId, String outTradeNo) {
        this.id = id;
        this.consignee = consignee;
        this.consigneeTel = consigneeTel;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.paymentWay = paymentWay;
        this.deliveryAddress = deliveryAddress;
        this.orderComment = orderComment;
        this.tradeBody = tradeBody;
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.processStatus = processStatus;
        this.trackingNo = trackingNo;
        this.parentOrderId = parentOrderId;
        this.outTradeNo = outTradeNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getTradeBody() {
        return tradeBody;
    }

    public void setTradeBody(String tradeBody) {
        this.tradeBody = tradeBody;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
