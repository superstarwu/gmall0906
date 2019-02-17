package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.OrderInfo;

public interface OrderService {
    void genTradeCode(String trdeCode, String userId);

    boolean checkTradeCode(String tradeCode, String userId);

    void saveOrderInfo(OrderInfo orderInfo);

    OrderInfo getOrderInfoByOutTradeNo(String outTradeNo);

    void updateOrderStatus(String out_trade_no, String tracking_no, String payment_status);

    void sendOrderResult(String out_trade_no);
}
