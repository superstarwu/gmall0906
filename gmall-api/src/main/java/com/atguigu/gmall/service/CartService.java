package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.CartInfo;

import java.util.List;

public interface CartService {

    CartInfo getExistsByCartInfo(CartInfo exists);

    void save(CartInfo cartInfo);

    void updateCartPrice(CartInfo ifExists);

    void flushCartCacheByUser(String userId);

    List<CartInfo> getCartInfoFromCache(String userId);
}
