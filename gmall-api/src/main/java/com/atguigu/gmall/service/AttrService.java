package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface AttrService {

    List<BaseCatalog1> getCataLog1();

    BaseCatalog1 getBaseCataLog1(String id);

    void addBaseCataLog1(BaseCatalog1 baseCataLog1);

    void deleteBaseCataLog1(String id);

    void updateBadeCataLog1(BaseCatalog1 baseCataLog1);

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    BaseCatalog2 getBaseCataLog2(String id);

    void addBaseCatalog2(BaseCatalog2 baseCatalog2);

    void deleteBaseCatalog2(String id);

    void updateBadeCatalog2(BaseCatalog2 baseCatalog2);

    List<BaseCatalog3> getCatalog3(String catalog2Id);

    BaseCatalog3 getBaseCatalog3(String id);

    void addBaseCatalog3(BaseCatalog3 baseCatalog3);

    void deleteBaseCatalog3(String id);

    void updateBadeCatalog3(BaseCatalog3 baseCatalog3);

    List<BaseAttrInfo> getAttrInfo(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValue(String attrId);

    void saveAttrValue(BaseAttrValue baseAttrValue);

    void updateAttrValue(BaseAttrValue baseAttrValue);

    void deleteAttrValue(String id);

    void updateAttrValueByInfo(BaseAttrInfo baseAttrInfo);

    void deleteAttrInfo(String attrId);

    List<BaseAttrInfo> getAttrInfoByValueIds(String join);

    BaseCatalog1 getBaseCatalog1ById(String catalog1Id);
}
