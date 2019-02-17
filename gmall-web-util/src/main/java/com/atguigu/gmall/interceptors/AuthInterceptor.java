package com.atguigu.gmall.interceptors;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter{

public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    //注解判断
    HandlerMethod hm = (HandlerMethod) handler;
    LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
    String token = "";
    String newToken = request.getParameter("newToken");
    String oldToken = CookieUtil.getCookieValue(request,"oldToken",true);

    if(StringUtils.isNotBlank(oldToken)){
        token = oldToken;
    }
    if(StringUtils.isNotBlank(newToken)){
        token = newToken;
    }

    if(methodAnnotation != null){
        //校验
        boolean loginCheck = false;
        if(StringUtils.isNotBlank(token)){
            String nip = request.getHeader("request-forwared-for");//nginx中的
            if(StringUtils.isBlank(nip)){
                nip = request.getRemoteAddr();//servlet中的ip
                if(StringUtils.isBlank(nip)){
                    nip = "127.0.0.1";
                }
            }
            String status = HttpClientUtil.doGet("http://localhost:8085/verify?token=" + token + "&requestId=" + nip);
            Map<String,String> statusMap = JSON.parseObject(status,Map.class);
            if(status!=null && statusMap.get("status").equals(ConstantClass.STATUSSUCCESS)){
                //远程调用验证中心的验证业务
                loginCheck = true;
                //将新的token更新到cookie中
                CookieUtil.setCookie(request,response,"oldToken",token,60,true);
                //添加用户信息到请求的业务中
               // Map userMap = JwtUtil.decode(ConstantClass.ENCODEKEY,token, Md5.toMd(nip));
               String userId  = statusMap.get("userId");
               request.setAttribute("userId",userId);
            }
        }
        if(loginCheck == false && methodAnnotation.isNeedLogin() == true){
            response.sendRedirect("http://localhost:8085/index?returnUrl=" + request.getRequestURL());
            return false;
        }
    }
    return true;
 }
}