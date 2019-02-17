package com.atguigu.gmall.order.orderListener;

import com.atguigu.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;

@Component
public class OrderMqListener {

    @Autowired
    private OrderService orderService;

    @JmsListener(containerFactory = "jmsQueueListener",destination ="PAYMENT_SUCCESS_QUEUE" )
    public void consumePaymentResult(MapMessage mapMessage){
        String out_trade_no = null;//此三项不能为空，需要判断为空时该如何处理
        String tracking_no = null;
        String payment_status = null;//有可能成功，也有可能失败，该分析判断

        try{
            out_trade_no = mapMessage.getString("out_trade_no");
            tracking_no = mapMessage.getString("tracking_no");
            payment_status = mapMessage.getString("payment_status");
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(out_trade_no + "已经完成，请指示！！");
        //消费代码
        orderService.updateOrderStatus(out_trade_no,tracking_no,payment_status);

        //发送一个订单成功的消息，由库存系统消费(或者调用库存接口)
        orderService.sendOrderResult(out_trade_no);
    }
}
