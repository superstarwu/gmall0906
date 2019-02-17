package com.atguigu.gmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.OrderInfo;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.payment.conf.AlipayConfig;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @Reference
    private OrderService orderService;

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/alipay/callback/return")
    public String callBackReturn(HttpServletRequest request,Map<String,String> paramsMap){ //页面同步反转的回调
        String out_trade_no = request.getParameter("out_trade_no");
        String trade_no = request.getParameter("trade_no");

        String sign = request.getParameter("sign");
        try {
            boolean b = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);//对支付宝回调签名的校验
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //幂等性检查
        boolean b = paymentService.checkPaymentStatus(out_trade_no);
        if(!b){
            //修改支付信息
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOutTradeNo(out_trade_no);
            paymentInfo.setAlipayTradeNo(trade_no);
            paymentInfo.setPaymentStatus("已支付");
            paymentInfo.setCallbackContent(request.getQueryString());
            paymentInfo.setCallbackTime(new Date());
            paymentService.updatePaymenyInfo(paymentInfo);

            //发送系统信息，触发并发商业支付业务服务200消息队列
            paymentService.sendPaymentSuccess(paymentInfo.getOutTradeNo(),paymentInfo.getPaymentStatus(),trade_no);
        }

//        try {
//            Thread.sleep(10000);
//            paymentService.updatePaymenyInfo(paymentInfo);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "finish";
    }

    @LoginRequired(isNeedLogin = true)
    @RequestMapping("/alipay/submit")
    @ResponseBody
    public String gotoPay(HttpServletRequest request, String outTradeNo, BigDecimal totalAmount, ModelMap modelMap){

        OrderInfo orderInfo = orderService.getOrderInfoByOutTradeNo(outTradeNo);
        String skuName = orderInfo.getOrderDetailList().get(0).getSkuName();

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建Api对应的request
        alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);//在公共参数中设置回跳和通知地址

        Map<String,String> requestMap = new HashMap<>();

        requestMap.put("out_trade_no",outTradeNo);
        requestMap.put("product_code","FAST_INSTANT_TRADE_PAY");
        requestMap.put("total_amount","0.01");
        requestMap.put("subject",skuName);

        alipayRequest.setBizContent(JSON.toJSONString(requestMap));//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.err.println(form);

            //生成(保存)支付信息
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOutTradeNo(outTradeNo);
            paymentInfo.setPaymentStatus("未支付");
            paymentInfo.setCreateTime(new Date());
            paymentInfo.setOrderId(orderInfo.getId());
            paymentInfo.setSubject(skuName);
            paymentInfo.setTotalAmount(totalAmount);
            paymentService.savePaymentInfo(paymentInfo);

            //发送检查支付结果的延迟消息队列
            paymentService.sendDelayPaymentCheck(outTradeNo,5);

        return form;
    }

    @LoginRequired(isNeedLogin = true)
    @RequestMapping("paymentindex")
    public String paymentIndex(HttpServletRequest request,String outTradeNo,BigDecimal totalAmount,ModelMap modelMap){

        String userId = (String)request.getAttribute("userId");

        modelMap.put("outTradeNo",outTradeNo);
        modelMap.put("totalAmount",totalAmount);
        return "paymentindex";
    }
}
