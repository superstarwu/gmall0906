package com.atguigu.gmall.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class controller{

    @Reference
    private UserService userService;

    @ResponseBody
    @RequestMapping("userList")
    public List<UserInfo> getUserList(){
        List<UserInfo> userList = userService.getUserList();
        return userList;
    }
}
