package com.atguigu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

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

    @Override
    public UserInfo selectUser(UserInfo userInfo) {
        //先查缓存中是否存在此用户，不存在再查数据库
        Jedis jedis = redisUtil.getJedis();
        String user = jedis.get("user:" + userInfo.getId() + ":info");
        if(user!= null){
            UserInfo userInfo1 = JSON.parseObject(user,UserInfo.class);
          //  System.err.println(userInfo1.getNickName());
            return userInfo1;
        }
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    public void addUserCache(UserInfo userlogin) {
       Jedis jedis = redisUtil.getJedis();
       //设置用户缓存
       jedis.setex("user:" + userlogin.getId() + ":info",60*60*24, JSON.toJSONString(userlogin));
       jedis.close();
    }

    @Override
    public List<UserAddress> getUserAddresses(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }
}
