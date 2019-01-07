package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.user.bean.UserAddress;
import com.atguigu.gmall.user.bean.UserInfo;
import com.atguigu.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("userList")
    @ResponseBody
    public List<UserInfo> userList(){
        List<UserInfo> userList = userService.getUserList();
        return userList;
    }

    @RequestMapping("user")
    @ResponseBody
    public UserInfo getUser(String id){
        id = "2";
        UserInfo userInfo = userService.getUser(id);
        return userInfo;
    }

    @RequestMapping("addUser")
    public String addUser(UserInfo userInfo){
       userInfo = new  UserInfo(null,"yueshen", "lanyue","123456", "月神","1696545632", "jyeu@emial.com", "picture","0");
       userService.addUser(userInfo);
       return "userList";
    }

    @RequestMapping("changeUser")
    public String changeUser(UserInfo userInfo){
        userInfo = new  UserInfo("1","donghuang", "boss","123456", "东君","36589652185", "jfk@emial.com", "picture","1");
        userService.changeUser(userInfo);
        return "userList";
    }

    @RequestMapping("removeUser")
    public String removeUser(String id){
        userService.removeUser(id);
        return "userList";
    }

    @ResponseBody
    @RequestMapping("user/address/list")
    public List<UserAddress> getUserAddressList(){
        List<UserAddress> userAddressList = userService.getUserAddressList();
        return userAddressList;
    }

    @ResponseBody
    @RequestMapping("user/address")
    public UserAddress getUserAddress(String id){

        UserAddress userAddress = userService.getUserAddress(id);
        return  userAddress;
    }

    @RequestMapping("add/user/address")
    public String addUserAddress(UserAddress userAddress){
        userAddress = new UserAddress(null,"佛山","4","月神","1696545632","1");
        userService.addUserAddress(userAddress);
        return "user/address/list";
    }

    @RequestMapping("remove/user/address")
    public String removeUserAddress(String id){

        userService.removeUserAddress(id);
        return "user/address/list";
    }

    @RequestMapping("change/user/address")
    public String changeUserAddress(UserAddress userAddress){

        userService.changeUserAddress(userAddress);
        return "user/address/list";
    }
}
