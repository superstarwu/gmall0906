package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PaymentInfo;

public interface PaymentService {
    void savePaymentInfo(PaymentInfo paymentInfo);

    void updatePaymenyInfo(PaymentInfo paymentInfo);

    void sendPaymentSuccess(String outTradeNo, String paymentStatus, String trade_no);

    void sendDelayPaymentCheck(String outTradeNo, int count);

    PaymentInfo checkPaymentResult(String out_trade_no);

    boolean checkPaymentStatus(String outTradeNo);
}
