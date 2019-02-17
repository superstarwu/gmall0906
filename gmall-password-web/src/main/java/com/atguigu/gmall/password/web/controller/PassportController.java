package com.atguigu.gmall.password.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.util.ConstantClass;
import com.atguigu.gmall.util.CookieUtil;
import com.atguigu.gmall.util.JwtUtil;
import com.atguigu.gmall.util.Md5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Reference
    private CartService cartService;

    @Reference
    private UserService userService;

    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request, String token,String requestId, ModelMap modelMap){
        Map userMap = JwtUtil.decode(ConstantClass.ENCODEKEY,token,Md5.toMd(requestId));
        String userId = (String)userMap.get("userId");
        Map<String,String> statusMap = new HashMap<>();
        statusMap.put("userId",userId);
        if(userMap == null){//{status:success,userId:2}
            statusMap.put("status",ConstantClass.STATUSFAIL);
            String status = JSON.toJSONString(statusMap);
            return status;
        }else {
            statusMap.put("status",ConstantClass.STATUSSUCCESS);
            String status = JSON.toJSONString(statusMap);
            return status;
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
        //用户名密码验证
        UserInfo userlogin = userService.selectUser(userInfo);
        String token = "";
        if(userlogin == null){
            //用户名或密码错误
            return "err";
        }else{
            //生成token
            HashMap<String,String> stringHashMap = new HashMap<>();
            stringHashMap.put("userId",userlogin.getId());
            stringHashMap.put("nickName",userlogin.getNickName());
            String nip = request.getHeader("request-forwared-for");//nginx中
            if(StringUtils.isBlank(nip)){
                nip = request.getRemoteAddr();//servlet中的ip
                if(StringUtils.isBlank(nip)){
                    nip = "127.0.0.1";
                }
            }
            token = JwtUtil.encode(ConstantClass.ENCODEKEY,stringHashMap,Md5.toMd(nip));
            //将用户数据存入缓存
           userService.addUserCache(userlogin);
            //同步购物车的数据
           String listCartCookie = CookieUtil.getCookieValue(request,ConstantClass.COOKIENAME,true);
           String userId = userlogin.getId();
           if(StringUtils.isNotBlank(listCartCookie)){
               //cookie中有购物车的数据
               cartService.addCartData(userId,listCartCookie);
           }else{
               //cookie中没有购物车的数据
               cartService.flushCartCacheByUser(userId);
           }
           //删除cookie中的数据
            CookieUtil.deleteCookie(request,response,ConstantClass.COOKIENAME);
        }
        return token;
    }

    @RequestMapping("index")
    public String index(String returnUrl,ModelMap modelMap){
        modelMap.put("originUrl",returnUrl);
        return "index";
    }

}
