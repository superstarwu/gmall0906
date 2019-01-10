package com.atguigu.gmall.manage.service.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.manage.service.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.manage.service.mapper.BaseCatalog1Mapper;
import com.atguigu.gmall.manage.service.mapper.BaseCatalog2Mapper;
import com.atguigu.gmall.manage.service.mapper.BaseCatalog3Mapper;
import com.atguigu.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private BaseCatalog1Mapper baseCataLog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Override
    public List<BaseCatalog1> getCataLog1() {
        return baseCataLog1Mapper.selectAll();
    }

    @Override
    public BaseCatalog1 getBaseCataLog1(String id) {
        return null;
    }

    @Override
    public void addBaseCataLog1(BaseCatalog1 baseCataLog1) {

    }

    @Override
    public void deleteBaseCataLog1(String id) {

    }

    @Override
    public void updateBadeCataLog1(BaseCatalog1 baseCataLog1) {

    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public BaseCatalog2 getBaseCataLog2(String id) {
        return null;
    }

    @Override
    public void addBaseCatalog2(BaseCatalog2 baseCatalog2) {
        baseCatalog2Mapper.insert(baseCatalog2);
    }

    @Override
    public void deleteBaseCatalog2(String id) {

    }

    @Override
    public void updateBadeCatalog2(BaseCatalog2 baseCatalog2) {

    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public BaseCatalog3 getBaseCatalog3(String id) {
        return null;
    }

    @Override
    public void addBaseCatalog3(BaseCatalog3 baseCatalog3) {

    }

    @Override
    public void deleteBaseCatalog3(String id) {

    }

    @Override
    public void updateBadeCatalog3(BaseCatalog3 baseCatalog3) {

    }

    @Override
    public List<BaseAttrInfo> getAttrInfo(String catalog3Id) {
        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);
        return baseAttrInfoMapper.select(baseAttrInfo);
    }
}