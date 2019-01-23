package com.atguigu.gmall.interceptors;

import com.atguigu.gmall.annotations.LoginRequired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter{

public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String newToken = request.getParameter("newToken");
    HandlerMethod hm = (HandlerMethod) handler;
    LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);

    if(methodAnnotation != null){
        boolean loginCheck = false;

        if(loginCheck == false && methodAnnotation.isNeedLogin() == true){
            response.sendRedirect("http://localhost:8085/index?returnUrl=" + request.getRequestURL());
            return false;
        }
    }
    return true;
}
}