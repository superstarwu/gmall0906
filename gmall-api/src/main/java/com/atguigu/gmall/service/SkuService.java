package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {

    List<SkuInfo> getSkuInfoList(String spuId);

    void addSkuInfo(SkuInfo skuInfo);

    SkuInfo getSkuInfo(String skuId);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);

    SkuInfo getItemData(String skuId);

    List<SkuInfo> getSkuInfoListByCatalog3Id(String catalog3Id);

    SkuInfo getSkuInfoBySkuId(String skuId);
}
