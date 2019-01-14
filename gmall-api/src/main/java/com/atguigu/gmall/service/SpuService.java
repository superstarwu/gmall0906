package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface SpuService {

    void addSpuInfo(SpuInfo spuInfo);

    void changeSpuInfo(SpuInfo spuInfo);

    void deleteSpuInfo(String id);

    List<SpuInfo> selectSpoInfoList(String catalog3Id);

    SpuInfo selectSpuInfo(String id);

    void addSpuSaleAttr(SpuSaleAttr spuSaleAttr);

    void changeSpuSaleAttr(SpuSaleAttr spuSaleAttr);

    void deleteSpuSaleAttr(String id);

    SpuSaleAttr selectSpuSaleAttr(String id);

    List<BaseAttrValue> getAttrValue();

    List<BaseSaleAttr> getBaseSaleAttrList();

    List<SpuImage> getSpuImageList(String id);

    List<SpuSaleAttr> getSpuSaleAttrList(String id);
}
