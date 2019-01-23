package com.atguigu.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexContoller {

    @Reference
    private AttrService attrService;

    @RequestMapping("index")
    public String getIndex(){

        return "index";
    }

    @RequestMapping("")
    public String getJson(String catalog1Id, ModelMap modelMap){
        BaseCatalog1 baseCatalog1 = attrService.getBaseCatalog1ById(catalog1Id);
        List<BaseCatalog2> baseCatalog2List = baseCatalog1.getBaseCatalog2List();
        for(BaseCatalog2 baseCatalog2 : baseCatalog2List){
            List<BaseCatalog3> baseCatalog3List = baseCatalog2.getBaseCatalog3List();
            for(BaseCatalog3 baseCatalog3 : baseCatalog3List){
                String catalog3Id = baseCatalog3.getId();
                String catalog3Name = baseCatalog3.getName();
            }
        }
        modelMap.put("baseCatalog1", JSON.toJSONString(baseCatalog1));
        return "";
    }

}
