package com.atguigu.gmall.user.service.impl;

import com.atguigu.gmall.user.bean.UserAddress;
import com.atguigu.gmall.user.bean.UserInfo;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    @Transactional(readOnly =true)
    public List<UserInfo> getUserList() {
         return userInfoMapper.selectAll();
    }

    @Override
    @Transactional(readOnly =true)
    public UserInfo getUser(String id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void addUser(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void removeUser(String id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void changeUser(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKey(userInfo);
    }


    @Override
    @Transactional(readOnly =true)
    public List<UserAddress> getUserAddressList() {
        return userAddressMapper.selectAll();
    }

    @Override
    @Transactional(readOnly =true)
    public UserAddress getUserAddress(String id) {

        return userAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void addUserAddress(UserAddress userAddress) {

        userAddressMapper.insert(userAddress);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void removeUserAddress(String id) {

        userAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void changeUserAddress(UserAddress userAddress) {

        userAddressMapper.updateByPrimaryKey(userAddress);
    }
}
