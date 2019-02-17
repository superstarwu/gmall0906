package com.atguigu.gmall.order.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.bean.enums.PaymentWay;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {

    @Reference
    private UserService userService;

    @Reference
    private CartService cartService;

    @Reference
    private SkuService skuService;

    @Reference
    private OrderService orderService;

    @LoginRequired(isNeedLogin = true)
    @RequestMapping("submitOrder")
    public String toPayPage(String tradeCode,String deliveryAddressId,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String userId = (String)request.getAttribute("userId");
        boolean b = orderService.checkTradeCode(tradeCode,userId);
        if(b){
            UserAddress userAddress = userService.getUserAddress(deliveryAddressId);
            List<CartInfo> cartInfoFromCache = cartService.getCartInfoFromCache(userId);
            //订单保存业务（订单数据的一致性校验，库存价格）
            //对订单对象进行封装
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setProcessStatus("订单已提交");
            orderInfo.setOrderStatus("订单已提交");

            String outTradeNo = userId + "atguigu";
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(date);
            outTradeNo = outTradeNo + format + System.currentTimeMillis();
            orderInfo.setOutTradeNo(outTradeNo);


            orderInfo.setConsignee(userAddress.getConsignee());
            orderInfo.setConsigneeTel(userAddress.getPhoneNum());
            orderInfo.setDeliveryAddress(userAddress.getUserAddress());
            orderInfo.setCreateTime(new Date());
            orderInfo.setUserId(userId);
            orderInfo.setPaymentWay(PaymentWay.ONLINE);
            BigDecimal totalPrice = getTotalPrice(cartInfoFromCache);
            orderInfo.setTotalAmount(totalPrice);
            orderInfo.setOrderComment("尚硅谷商城");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,1);
            orderInfo.setExpireTime(c.getTime());

            List<OrderDetail> orderDetails = new ArrayList<>();
            List<String> delCartIds = new ArrayList<>();
            //对订单详情进行封装
            for(CartInfo cartInfo : cartInfoFromCache){
                if(cartInfo.getIsChecked().equals("1")){
                    //验价
                    SkuInfo skuInfo = skuService.getSkuInfo(cartInfo.getSkuId());
                    int i = skuInfo.getPrice().compareTo(cartInfo.getSkuPrice());
                    if(i == 0){
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setSkuName(cartInfo.getSkuName());
                        orderDetail.setSkuId(cartInfo.getSkuId());
                        orderDetail.setImgUrl(cartInfo.getImgUrl());
                        orderDetail.setOrderPrice(cartInfo.getCartPrice());
                        orderDetail.setSkuNum(cartInfo.getSkuNum());
                        orderDetails.add(orderDetail);
           //             delCartIds.add(cartInfo.getId());
                    }else {
                        return "tradeFail";
                    }
                    //验库存
                }
            }
            orderInfo.setOrderDetailList(orderDetails);
            orderService.saveOrderInfo(orderInfo);
            //删除购物车中已经保存订单的数据
          //  cartService.delCartInfoById(delCartIds);
            //重定向到支付系统，由支付系统对接支付宝平台，完成支付业务
            return "redirect:http://localhost:8090/paymentindex?outTradeNo=" + outTradeNo + "&totalAmount=" + totalPrice;
        }else {
            return "tradeFail";
        }

    }

    @LoginRequired(isNeedLogin = true)
    @RequestMapping("toTrade")
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String userId = (String)request.getAttribute("userId");
        //根据userId查询缓存中的购物车数据,需要和db中的数据做校验看价格是否有更新
        List<CartInfo> cartInfoFromCache = cartService.getCartInfoFromCache(userId);
        //将购物车数据转化为订单列表数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        if(cartInfoFromCache != null){
            for(CartInfo cartInfo1 : cartInfoFromCache){
                if(cartInfo1.getIsChecked().equals("1")){
                    OrderDetail orderDetail = new OrderDetail();

                    orderDetail.setImgUrl(cartInfo1.getImgUrl());
                    orderDetail.setOrderPrice(cartInfo1.getCartPrice());
                    orderDetail.setSkuId(cartInfo1.getSkuId());
                    orderDetail.setSkuNum(cartInfo1.getSkuNum());
                    orderDetail.setSkuName(cartInfo1.getSkuName());
                    orderDetail.setHasStock("1");
                    orderDetailList.add(orderDetail);
                }
            }
        }
        //查询userId的收货人列表信息
        List<UserAddress> userAddressList = userService.getUserAddresses(userId);
        UserInfo user = userService.getUser(userId);
        modelMap.put("userAddressList",userAddressList);
        modelMap.put("orderDetailList",orderDetailList);
        modelMap.put("nickName",user.getNickName());
        modelMap.put("totalAmount",getTotalPrice(cartInfoFromCache));
        //生成交易码，写入缓存
        String tradeCode = UUID.randomUUID().toString();
        modelMap.put("tradeCode",tradeCode);
        orderService.genTradeCode(tradeCode,userId);
        return "trade";
    }

    public BigDecimal getTotalPrice(List<CartInfo> cartInfos){
        BigDecimal b = new BigDecimal("0");
        for(CartInfo cartInfo : cartInfos){
            String isChecked = cartInfo.getIsChecked();
            if(isChecked.equals("1")){
                b = b.add(cartInfo.getCartPrice());
            }
        }
        return b;
    }
}
