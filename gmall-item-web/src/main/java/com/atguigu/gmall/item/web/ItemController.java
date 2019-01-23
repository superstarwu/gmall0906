package com.atguigu.gmall.item.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    private SkuService skuService;

    @Reference
    private SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String getItemPage(@PathVariable("skuId")String skuId, ModelMap modelMap){

        SkuInfo skuInfo = skuService.getItemData(skuId);
        String spuId = skuInfo.getSpuId();

        //销售属性的集合
        List<SpuSaleAttr> spuSaleAttrListCheckBySku = new ArrayList<>();
        spuSaleAttrListCheckBySku = spuService.getSpuAttrValueListCheckBySku(spuId,skuId);
        modelMap.put("spuSaleAttrListCheckBySku",spuSaleAttrListCheckBySku);
        modelMap.put("skuInfo",skuInfo);
        //隐藏一个hash表
        List<SkuInfo> skuInfoList = skuService.getSkuSaleAttrValueListBySpu(spuId);
        Map<String,String> skuMap = new HashMap<>();
        for(SkuInfo sku : skuInfoList){
            String v = sku.getId();
            List<SkuSaleAttrValue> skuSaleAttrValueList = sku.getSkuSaleAttrValueList();
            String k = "";
            for(SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList){
                String valueId = skuSaleAttrValue.getSaleAttrValueId();
                k = k + "|" + valueId;
            }
            skuMap.put(k,v);
        }
        modelMap.put("skuMap", JSON.toJSONString(skuMap));
        return "item";
    }
    @RequestMapping("index")
    public String getA(ModelMap map){

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for(int i = 0; i < 5; i ++){
            list1.add("集合1==》" + i);
            list2.add("集合2==>" + i);
        }
        map.put("list1",list1);
        map.put("list2",list2 );
        map.put("hello","hello easyui and themleaf");
        List<UserInfo> list3 = new ArrayList<>();
        for(int i = 0; i < 5 ;i ++){
            UserInfo userInfo = new UserInfo();
            userInfo.setName("张" + i);
            list3.add(userInfo);
        }
        map.put("list3",list3);
        UserInfo userInfo = new UserInfo();
        userInfo.setName("李四");
        map.put("userInfo",null);
        return "index";
    }
}
