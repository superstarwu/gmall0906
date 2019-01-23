package com.atguigu.gmall.password.web.controller;

import com.atguigu.gmall.bean.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PassportController {

    @RequestMapping("login")
    @ResponseBody
    public String login(UserInfo userInfo, ModelMap modelMap){

        //用户名密码验证
        return "token";
    }

    @RequestMapping("index")
    public String index(String returnUrl,ModelMap modelMap){
        modelMap.put("originUrl",returnUrl);
        return "index";
    }

}
