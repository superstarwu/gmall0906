package com.atguigu.gmall.manage.service.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.service.mapper.*;
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

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

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

        //平台属性集合
        List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.select(baseAttrInfo);
        for(BaseAttrInfo baseAttrInfo1 : baseAttrInfos){
           String attrId =  baseAttrInfo1.getId();
           BaseAttrValue baseAttrValue = new BaseAttrValue();
           baseAttrValue.setAttrId(attrId);
           //平台属性值集合
           List<BaseAttrValue> baseAttrValues = baseAttrValueMapper.select(baseAttrValue);
           baseAttrInfo1.setAttrValueList(baseAttrValues);
        }
        return baseAttrInfos;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        baseAttrInfoMapper.insertSelective(baseAttrInfo);
        //插入数据后返回主键
        String attrId = baseAttrInfo.getId();
        List<BaseAttrValue> baseAttrValues = baseAttrInfo.getAttrValueList();
        for(BaseAttrValue baseAttrValue : baseAttrValues){
            baseAttrValue.setAttrId(attrId);
            baseAttrValueMapper.insertSelective(baseAttrValue);
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValue(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }
    @Override
    public void saveAttrValue(BaseAttrValue baseAttrValue) {

    }
    @Override
    public void updateAttrValue(BaseAttrValue baseAttrValue) {

    }
    @Override
    public void deleteAttrValue(String id) {

    }

    @Override
    public void updateAttrValueByInfo(BaseAttrInfo baseAttrInfo) {
        String attrId = baseAttrInfo.getId();
        List<BaseAttrValue> baseAttrValues = baseAttrInfo.getAttrValueList();
        for(BaseAttrValue baseAttrValue : baseAttrValues){
            baseAttrValueMapper.updateByPrimaryKeySelective(baseAttrValue);
        }
    }

    @Override
    public void deleteAttrInfo(String attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        List<BaseAttrValue> baseAttrValues = baseAttrInfo.getAttrValueList();
        for(BaseAttrValue baseAttrValue : baseAttrValues){
            baseAttrValueMapper.delete(baseAttrValue);
        }
        baseAttrInfoMapper.delete(baseAttrInfo);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoByValueIds(String join) {
       List<BaseAttrInfo> baseAttrInfos = baseAttrInfoMapper.selectAttrInfoByValueIds(join);
        return baseAttrInfos;
    }

    @Override
    public BaseCatalog1 getBaseCatalog1ById(String catalog1Id) {

        return baseCataLog1Mapper.selectBaseCatalog1List(catalog1Id);
    }
}
