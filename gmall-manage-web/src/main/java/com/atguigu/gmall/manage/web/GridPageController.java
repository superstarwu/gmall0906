package com.atguigu.gmall.manage.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GridPageController {

    @Reference
    private AttrService attrService;

    @ResponseBody
    @RequestMapping("getcatalog1")
    public List<BaseCatalog1> getCatalog1(){
        List<BaseCatalog1> baseCataLog1List = attrService.getCataLog1();
        return baseCataLog1List;
    }

    @ResponseBody
    @RequestMapping("getcatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
            List<BaseCatalog2> baseCatalog2List = attrService.getCatalog2(catalog1Id);
        return baseCatalog2List;
    }

    @ResponseBody
    @RequestMapping("getcatalog3")
    public List<BaseCatalog3> getCataLog3(String catalog2Id){
        List<BaseCatalog3> baseCatalog3List = attrService.getCatalog3(catalog2Id);
        return baseCatalog3List;
    }

    @ResponseBody
    @RequestMapping("getAttrList")
    public List<BaseAttrInfo> getAttrInfo(String catalog3Id){
        List<BaseAttrInfo> baseAttrInfoList = attrService.getAttrInfo(catalog3Id);
      return  baseAttrInfoList;
    }

    @RequestMapping("saveAttr")
    @ResponseBody
    public String getAttrValue(BaseAttrInfo baseAttrInfo){
      //  attrService.
        attrService.saveAttrInfo(baseAttrInfo);
        return "success";
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){

        List<BaseAttrValue> baseAttrValues = attrService.getAttrValue(attrId);
        return baseAttrValues;
    }

    @RequestMapping("saveEditAttr")
    @ResponseBody
    public String updateEditAttr(BaseAttrInfo baseAttrInfo){
        attrService.updateAttrValueByInfo(baseAttrInfo);
        return "success";
    }

    @RequestMapping("removeAttrInfo")
    @ResponseBody
    public String removeAttrInfo(String attrId){
        attrService.deleteAttrInfo(attrId);
        return "success";
    }
}
