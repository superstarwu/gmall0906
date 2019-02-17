package com.atguigu.gmall.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OrderDetail;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.enums.PaymentWay;
import com.atguigu.gmall.order.mapper.OrderDetailMapper;
import com.atguigu.gmall.order.mapper.OrderInfoMapper;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.util.ActiveMQUtil;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ActiveMQUtil activeMQUtil;

    @Override
    public void genTradeCode(String tradeCode, String userId) {
            Jedis jedis = redisUtil.getJedis();
            jedis.setex("user:" + userId + ":tradeCode", 60 * 30, tradeCode);
            jedis.close();
    }

    @Override
    public boolean checkTradeCode(String tradeCode, String userId) {
        boolean b = false;
        Jedis jedis = redisUtil.getJedis();
        String code = jedis.get("user:" + userId + ":tradeCode");
        if(tradeCode.equals(code)){
            b = true;
            jedis.del("user:" + userId + ":tradeCode");
        }
        return b;
    }

    @Override
    public void saveOrderInfo(OrderInfo orderInfo) {
        //保存订单信息
        orderInfoMapper.insertSelective(orderInfo);
        String orderId = orderInfo.getId();

        //保存订单详情
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            orderDetail.setOrderId(orderId);
            orderDetailMapper.insertSelective(orderDetail);
        }
    }

    @Override
    public OrderInfo getOrderInfoByOutTradeNo(String outTradeNo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOutTradeNo(outTradeNo);
        orderInfo = orderInfoMapper.selectOne(orderInfo);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        List<OrderDetail> orderDetails = orderDetailMapper.select(orderDetail);
        orderInfo.setOrderDetailList(orderDetails);
        return orderInfo;
    }

    @Override
    public void updateOrderStatus(String out_trade_no, String tracking_no, String payment_status) {

        Example e = new Example(OrderInfo.class);
        e.createCriteria().andEqualTo("outTradeNo",out_trade_no);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus("订单已支付");
        orderInfo.setPaymentWay(PaymentWay.ONLINE);
        orderInfo.setProcessStatus("订单已支付");
        orderInfo.setTrackingNo(tracking_no);

        orderInfoMapper.updateByExampleSelective(orderInfo,e);
    }

    @Override
    public void sendOrderResult(String out_trade_no) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOutTradeNo(out_trade_no);
        OrderInfo orderInfo1 = orderInfoMapper.selectOne(orderInfo);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo1.getId());
        List<OrderDetail> orderDetailList = orderDetailMapper.select(orderDetail);
        orderInfo1.setOrderDetailList(orderDetailList);

        try{
            ConnectionFactory connectionFactory = activeMQUtil.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true,Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue("ORDER_SUCCESS_QUEUE");
            MessageProducer messageProducer = session.createProducer(queue);
            TextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText(JSON.toJSONString(orderInfo1));

            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            messageProducer.send(textMessage);

            session.commit();//事务型消息，必须提交后才生效
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
