package com.atguigu.gmall.manage.service.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.service.mapper.*;
import com.atguigu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Override
    public void addSpuInfo(SpuInfo spuInfo) {
        spuInfoMapper.insertSelective(spuInfo);
        String spuId = spuInfo.getId();
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for(SpuImage spuImage : spuImageList){
            spuImage.setSpuId(spuId);
            spuImageMapper.insertSelective(spuImage);
        }
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for(SpuSaleAttr spuSaleAttr : spuSaleAttrList){
            spuSaleAttr.setSpuId(spuId);
            spuSaleAttrMapper.insertSelective(spuSaleAttr);
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for(SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList){
                spuSaleAttrValue.setSpuId(spuId);
                spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
            }
        }
    }

    @Override
    public void changeSpuInfo(SpuInfo spuInfo) {

    }

    @Override
    public void deleteSpuInfo(String id) {

    }
    @Override
    public List<SpuInfo> selectSpoInfoList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public SpuInfo selectSpuInfo(String id) {
        return null;
    }

    @Override
    public void addSpuSaleAttr(SpuSaleAttr spuSaleAttr) {

    }

    @Override
    public void changeSpuSaleAttr(SpuSaleAttr spuSaleAttr) {

    }

    @Override
    public void deleteSpuSaleAttr(String id) {

    }

    @Override
    public SpuSaleAttr selectSpuSaleAttr(String id) {
        return null;
    }

    @Override
    public List<BaseAttrValue> getAttrValue() {
        return baseAttrValueMapper.selectAll();
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    public List<SpuImage> getSpuImageList(String id) {
        return null;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String id) {

        return null;
    }

}
