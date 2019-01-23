package com.atguigu.gmall.manage.service.mapper;

import com.atguigu.gmall.bean.BaseCatalog1;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BaseCatalog1Mapper extends Mapper<BaseCatalog1> {
    BaseCatalog1 selectBaseCatalog1List(@Param("catalog1Id") String catalog1Id);
}
