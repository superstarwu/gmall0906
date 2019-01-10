package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;

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
}
