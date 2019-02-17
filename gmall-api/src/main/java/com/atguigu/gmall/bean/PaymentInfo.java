package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 6344356454856746844L;

    private String id;
    private String outTradeNo;
    private String orderId;
    private String alipayTradeNo;
    private BigDecimal totalAmount;
    private String subject;
    private String paymentStatus;
    private Date createTime;
    private Date callbackTime;
    private String callbackContent;

    public PaymentInfo() {
    }

    public PaymentInfo(String id, String outTradeNo, String orderId, String alipayTradeNo, BigDecimal totalAmount, String subject, String paymentStatus, Date createTime, Date callbackTime, String callbackContent) {
        this.id = id;
        this.outTradeNo = outTradeNo;
        this.orderId = orderId;
        this.alipayTradeNo = alipayTradeNo;
        this.totalAmount = totalAmount;
        this.subject = subject;
        this.paymentStatus = paymentStatus;
        this.createTime = createTime;
        this.callbackTime = callbackTime;
        this.callbackContent = callbackContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getCallbackContent() {
        return callbackContent;
    }

    public void setCallbackContent(String callbackContent) {
        this.callbackContent = callbackContent;
    }
}
