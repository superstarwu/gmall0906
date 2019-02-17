package com.atguigu.gmall.payment.service.impl;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.payment.mapper.PaymentInfoMapper;
import com.atguigu.gmall.service.PaymentService;
import com.atguigu.gmall.util.ActiveMQUtil;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void updatePaymenyInfo(PaymentInfo paymentInfo) {
        Example example = new Example(PaymentInfo.class);
        example.createCriteria().andEqualTo("outTradeNo",paymentInfo.getOutTradeNo());

        paymentInfoMapper.updateByExampleSelective(paymentInfo,example);
    }

    @Override
    public void sendPaymentSuccess(String outTradeNo, String paymentStatus, String trade_no) {
      try{

          //创建连接,连接消息服务器
          ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();
          Connection connection = connectionFactory.createConnection();
          connection.start();

          //第一个值表示是否使用事务，如果选择true,则第二个相当于值为0
          Session session = connection.createSession(true,Session.SESSION_TRANSACTED);

          //发送消息
          Queue queue = session.createQueue("PAYMENT_SUCCESS_QUEUE");

          MessageProducer producer = session.createProducer(queue);
          MapMessage mapMessage = new ActiveMQMapMessage();
          mapMessage.setString("out_trade_no",outTradeNo);
          mapMessage.setString("payment_status",paymentStatus);
          mapMessage.setString("tracking_no",trade_no);

          producer.setDeliveryMode(DeliveryMode.PERSISTENT);
          producer.send(mapMessage);
          session.commit(); //事务性消息，必须提交后才生效
          session.close();
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    @Override
    public void sendDelayPaymentCheck(String outTradeNo, int count) {
        try{
            //创建连接,连接消息服务器
            ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //第一个值表示是否使用事务，如果选择true,则第二个相当于值为0
            Session session = connection.createSession(true,Session.SESSION_TRANSACTED);

            //发送消息
            Queue queue = session.createQueue("PAYMENT_CHECHK_QUEUE");

            MessageProducer producer = session.createProducer(queue);
            MapMessage mapMessage = new ActiveMQMapMessage();
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,1000*10);
            mapMessage.setString("out_trade_no",outTradeNo);
            mapMessage.setInt("count",count);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(mapMessage);
            session.commit(); //事务性消息，必须提交后才生效
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public PaymentInfo checkPaymentResult(String out_trade_no) {
        PaymentInfo paymentInfo = new PaymentInfo();

        //调用alipayClient接口，根据out_trade_no查询支付状态
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        Map<String,Object> mapString = new HashMap<String,Object>();
        mapString.put("out_trade_no",out_trade_no);
        String s = JSON.toJSONString(mapString);
        request.setBizContent(s);
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //等待付款，付款成功，付款失败，已经结束
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(response.getMsg());
        paymentInfo.setOutTradeNo(out_trade_no);

        if(response.isSuccess()){
            System.out.println("交易已经创建");
            paymentInfo.setAlipayTradeNo(response.getTradeNo());
            paymentInfo.setPaymentStatus(response.getTradeStatus());
        }else{
            System.out.println("未创建交易");
        }
        return paymentInfo;
    }

    @Override
    public boolean checkPaymentStatus(String outTradeNo) {
        boolean b = false;
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(outTradeNo);
       PaymentInfo paymentInfo1 = paymentInfoMapper.selectOne(paymentInfo);
       if(paymentInfo1.getPaymentStatus().equals("已支付")){
           b = true;
       }
        return b;
    }
}
