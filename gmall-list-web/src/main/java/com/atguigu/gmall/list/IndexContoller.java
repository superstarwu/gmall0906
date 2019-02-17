package com.atguigu.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;

@Controller
public class IndexContoller {

    @Reference
    private AttrService attrService;

    @RequestMapping("index")
    public String getIndex(){

        return "index";
    }


    @RequestMapping("commodlist")
    public String getJson(String catalog1Id, ModelMap modelMap) throws Exception {
        BaseCatalog1 baseCatalog1 = attrService.getBaseCatalog1ById(catalog1Id);
        //转换成json字符串
        String baseCatalog1Json = JSON.toJSONString(baseCatalog1);
        //io流传递
       FileInputStream fileInputStream = new FileInputStream(baseCatalog1Json);

        return "";
    }

}
