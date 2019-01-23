package com.atguigu.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.CartInfo;
import com.atguigu.gmall.cart.service.mapper.CartInfoMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartInfoMapper cartInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public CartInfo getExistsByCartInfo(CartInfo exists) {

       CartInfo cartInfo = cartInfoMapper.selectOne(exists);
        return cartInfo;
    }

    @Override
    public void save(CartInfo cartInfo) {
        cartInfoMapper.insertSelective(cartInfo);
    }

    @Override
    public void updateCartPrice(CartInfo ifExists) {
        Example e = new Example(CartInfo.class);
        e.createCriteria().andEqualTo("userId",ifExists.getUserId()).andEqualTo("skuId",ifExists.getSkuId());
        cartInfoMapper.updateByExampleSelective(ifExists,e);
    }

    @Override
    public void flushCartCacheByUser(String userId) {

        List<CartInfo> cartInfos = getCarInfoByUserId(userId);
        Jedis jedis = redisUtil.getJedis();
        if(cartInfos != null){
            Map<String,String> stringMap = new HashMap<>();
            for(CartInfo cartInfo : cartInfos){
                String cartId = cartInfo.getId();
                stringMap.put(cartId, JSON.toJSONString(cartInfo));
            }
            jedis.hmset("cart:" + userId + ":info",stringMap);
        }
        jedis.close();
    }

    @Override
    public List<CartInfo> getCartInfoFromCache(String userId) {
        List<CartInfo> cartInfos = new ArrayList<>();
        Jedis jedis = redisUtil.getJedis();
       List<String> hvals = jedis.hvals("cart:" + userId + ":info");
        for(String hval : hvals){
            CartInfo cartInfo = JSON.parseObject(hval,CartInfo.class);
            cartInfos.add(cartInfo);
        }
        return cartInfos;
    }

    public List<CartInfo> getCarInfoByUserId(String userId) {
        CartInfo cartInfo = new CartInfo();
        cartInfo.setUserId(userId);
        return cartInfoMapper.select(cartInfo);
    }

}
