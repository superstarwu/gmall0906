package com.atguigu.gmall.cart.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Reference
    private SkuService skuService;

    @Reference
    private CartService cartService;

    @LoginRequired(isNeedLogin = true)
    @RequestMapping("toTrade")
    public String toTrade(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        return "toTrade";
    }

    @RequestMapping("firstindex")
    public String loginFirstIndex(){
        return "redirect:http://localhost:8083/index";
    }

    @LoginRequired(isNeedLogin = false)
    @RequestMapping("addSkuNum")
    public String addSkuNum(CartInfo cartInfo,HttpServletResponse response,HttpServletRequest request,ModelMap modelMap){
        String userId = "2";
        List<CartInfo> cartInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            cartInfos = cartService.getCartInfoFromCache(userId);
        }else{
            String listCartInfoCookie = CookieUtil.getCookieValue(request,"listCartCookie",true);
            cartInfos = JSON.parseArray(listCartInfoCookie,CartInfo.class);
        }
        for(CartInfo cartInfo1 : cartInfos){
            if(cartInfo1.getSkuId().equals(cartInfo.getSkuId())){
                //  cartInfo1.setSkuNum(cartInfo.getSkuNum());
                    cartInfo1.setSkuNum(cartInfo1.getSkuNum() + 1);
                cartInfo1.setCartPrice(cartInfo1.getSkuPrice().multiply(new BigDecimal(cartInfo1.getSkuNum())));
                if(StringUtils.isNotBlank(userId)){
                    cartService.updateCartPrice(cartInfo1);
                    cartService.flushCartCacheByUser(userId);
                }else {
                    CookieUtil.setCookie(request,response,"listCartCookie",JSON.toJSONString(cartInfos),1000*60*60*24,true);
                }
            }
        }
        modelMap.put("cartList",cartInfos);
        BigDecimal b = getTotalPrice(cartInfos);
        modelMap.put("totalPrice",b);
        return "cartListInner";
    }

    @LoginRequired(isNeedLogin = false)
    @RequestMapping("reduceSkuNum")
    public String reduceSkuNum(CartInfo cartInfo,HttpServletResponse response,HttpServletRequest request,ModelMap modelMap){
        String userId = "2";
        List<CartInfo> cartInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            cartInfos = cartService.getCartInfoFromCache(userId);
        }else{
            String listCartInfoCookie = CookieUtil.getCookieValue(request,"listCartCookie",true);
            cartInfos = JSON.parseArray(listCartInfoCookie,CartInfo.class);
        }
        for(CartInfo cartInfo1 : cartInfos){
            if(cartInfo1.getSkuId().equals(cartInfo.getSkuId())){
              //  cartInfo1.setSkuNum(cartInfo.getSkuNum());
                if(cartInfo1.getSkuNum() > 1){
                    cartInfo1.setSkuNum(cartInfo1.getSkuNum() - 1);
                }
                cartInfo1.setCartPrice(cartInfo1.getSkuPrice().multiply(new BigDecimal(cartInfo1.getSkuNum())));
                if(StringUtils.isNotBlank(userId)){
                    cartService.updateCartPrice(cartInfo1);
                    cartService.flushCartCacheByUser(userId);
                }else {
                    CookieUtil.setCookie(request,response,"listCartCookie",JSON.toJSONString(cartInfos),1000*60*60*24,true);
                }
            }
        }
        modelMap.put("cartList",cartInfos);
        BigDecimal b = getTotalPrice(cartInfos);
        modelMap.put("totalPrice",b);
        return "cartListInner";
    }

    @LoginRequired(isNeedLogin = false)
    @RequestMapping("checkCart")
    public String checkCart(CartInfo cartInfo,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String userId = "2";
        List<CartInfo> cartInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            cartInfos = cartService.getCartInfoFromCache(userId);
        }else{
            String listCartInfoCookie = CookieUtil.getCookieValue(request,"listCartCookie",true);
            cartInfos = JSON.parseArray(listCartInfoCookie,CartInfo.class);
        }
        //修改购物车的状态
        for(CartInfo cartInfo1 : cartInfos){
            if(cartInfo1.getSkuId().equals(cartInfo.getSkuId())){
                cartInfo1.setIsChecked(cartInfo.getIsChecked());
                if(StringUtils.isNotBlank(userId)){
                    cartService.updateCartPrice(cartInfo1);
                    cartService.flushCartCacheByUser(userId);
                }else{
                    CookieUtil.setCookie(request,response,"listCartCookie",JSON.toJSONString(cartInfos),1000*60*60*24,true);
                }
            }
        }
        modelMap.put("cartList",cartInfos);
        BigDecimal b = getTotalPrice(cartInfos);
        modelMap.put("totalPrice",b);

        return "cartListInner";
    }

    @LoginRequired(isNeedLogin = false)
    @RequestMapping("cartList")
    public String getCartList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String userId = "2";
       List<CartInfo> cartInfos = new ArrayList<>();
       if(StringUtils.isNotBlank(userId)){
           cartInfos = cartService.getCartInfoFromCache(userId);
       }else {
           String listCartCookie = CookieUtil.getCookieValue(request, "listCartCookie", true);
           if(StringUtils.isNotBlank(listCartCookie)){
               cartInfos = JSON.parseArray(listCartCookie,CartInfo.class);
           }
       }
       modelMap.put("cartList",cartInfos);
       BigDecimal b = getTotalPrice(cartInfos);
       modelMap.put("totalPrice",b);
        return "cartList";
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
    @LoginRequired(isNeedLogin = false)
    @RequestMapping("addToCart")
    public String addToCart(HttpServletRequest request, HttpServletResponse response,String skuId,int num){

        String userId = "2";
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(skuId);
        CartInfo cartInfo = new CartInfo();
        cartInfo.setSkuId(skuId);
        cartInfo.setCartPrice(skuInfo.getPrice().multiply(new BigDecimal(num)));
        cartInfo.setIsChecked("1");
        cartInfo.setSkuName(skuInfo.getSkuName());
        cartInfo.setSkuNum(num);
        cartInfo.setSkuPrice(skuInfo.getPrice());
        cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
        //此处需要判断user是否登陆
        if(userId != null){
            cartInfo.setUserId(userId);
        }

        List<CartInfo> cartInfos = new ArrayList<>();
        //购物车的添加逻辑
        if(StringUtils.isBlank(userId)){
            //用户没有登陆，操作cookie
            String listCartCookieStr = CookieUtil.getCookieValue(request, "listCartCookie", true);
            cartInfos = JSON.parseArray(listCartCookieStr,CartInfo.class);
            if(StringUtils.isBlank(listCartCookieStr)){
                cartInfos = new ArrayList<>();
                cartInfos.add(cartInfo);
        }else{
            //判断是否重复
            boolean b = if_new_cart(cartInfos,cartInfo);
            if(b){
                //新车
                cartInfos.add(cartInfo);
            }else {
                //老车
                for(CartInfo info : cartInfos) {
                    //当商品的id相同时直接更新数量
                    if(info.getSkuId().equals(skuId)){
                        info.setSkuNum(info.getSkuNum() + cartInfo.getSkuNum());
                        info.setCartPrice(info.getSkuPrice().multiply(new BigDecimal(info.getSkuNum())));
                    }
                    }
                }
            }
            //覆盖浏览器的cookie
            CookieUtil.setCookie(request,response,"listCartCookie",JSON.toJSONString(cartInfos),1000*60*60*24,true);
        }else{
            //用户已经登陆，操作db
            CartInfo exists = new CartInfo();
            exists.setSkuId(skuId);
            exists.setUserId(userId);
            CartInfo ifExists = cartService.getExistsByCartInfo(exists);
            if(ifExists == null){
                //添加
                cartService.save(cartInfo);
            }else{
                //修改
                ifExists.setSkuNum(ifExists.getSkuNum() + num);
                ifExists.setCartPrice(ifExists.getSkuPrice().multiply(new BigDecimal(ifExists.getSkuNum())));
                cartService.updateCartPrice(ifExists);
            }
            //同步redis缓存
            cartService.flushCartCacheByUser(userId);
        }
         return "redirect:http://localhost:8084/success.html";
    }

    public boolean if_new_cart(List<CartInfo> cartInfos,CartInfo cartInfo){
        boolean b = true;
        for(CartInfo cartInfo1 : cartInfos){
            String skuId = cartInfo1.getSkuId();
            if(skuId.equals(cartInfo.getSkuId())){
                b = false;
            }
        }
        return b;
    }
}
