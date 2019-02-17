package com.atguigu.gmall.payment.delaylistener;

import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import java.util.Date;

@Component
public class AplipayDelayListener {

    @Autowired
    private PaymentService paymentService;

    @JmsListener(containerFactory = "jmsQueueListener",destination = "PAYMENT_CHECHK_QUEUE")
    public void consumeCheckResult(MapMessage mapMessage){
        try{
            String out_trade_no = mapMessage.getString("out_trade_no");
            int count = mapMessage.getInt("count");

            //如果没有支付成功，再次发送延迟检查支付队列
            if(count > 0){
                //进行支付状态检查
                System.out.println("开始进行第" + (6 - count) + "轮查询支付状态");
               PaymentInfo paymentInfo = paymentService.checkPaymentResult(out_trade_no);
                if(paymentInfo.getPaymentStatus()!= null&&(paymentInfo.getPaymentStatus().equals("TRADE_SUCCESS")||paymentInfo.getPaymentStatus().equals("TRADE_FINISHED"))) {
                   //交易成功或失败，记录交易状态
                    System.out.println("支付成功，交易完成...");
                    boolean b = paymentService.checkPaymentStatus(out_trade_no);
                    if(!b){
                        //生成支付信息
                        PaymentInfo paymentInfoUpdate = new PaymentInfo();
                        paymentInfoUpdate.setAlipayTradeNo(paymentInfo.getAlipayTradeNo());
                        paymentInfoUpdate.setOutTradeNo(out_trade_no);
                        paymentInfoUpdate.setPaymentStatus("已支付");
                        paymentInfoUpdate.setCallbackContent(paymentInfo.getCallbackContent());
                        paymentInfoUpdate.setCallbackTime(new Date());
                        paymentService.updatePaymenyInfo(paymentInfoUpdate);
                        //发送系统信息，触发并发商业支付业务服务200消息队列
                        paymentService.sendPaymentSuccess(paymentInfo.getOutTradeNo(),paymentInfo.getPaymentStatus(),paymentInfo.getAlipayTradeNo());
                    }
                }else{
                    System.out.println("未支付交易未完成，仍在进行第" + (6 - count) + "轮巡查");
                    paymentService.sendDelayPaymentCheck(out_trade_no,count - 1);
                }
            }else{
                System.out.println("检查支付状况的巡查次数已尽，但仍未支付，支付窗口关闭。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
