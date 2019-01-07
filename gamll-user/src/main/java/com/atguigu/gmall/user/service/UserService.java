package com.atguigu.gmall.user.service;

import com.atguigu.gmall.user.bean.UserAddress;
import com.atguigu.gmall.user.bean.UserInfo;

import java.util.List;

public interface UserService {

    List<UserInfo> getUserList();

    UserInfo getUser(String id);

    void addUser(UserInfo userInfo);

    void removeUser(String id);

    void changeUser(UserInfo userInfo);

    List<UserAddress> getUserAddressList();

    UserAddress getUserAddress(String id);

    void addUserAddress(UserAddress userAddess);

    void removeUserAddress(String id);

    void changeUserAddress(UserAddress userAddress);
}
