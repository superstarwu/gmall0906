package com.atguigu.gmall.manage.web;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SkuController {

    @Reference
    private SkuService skuService;

    @RequestMapping("skuInfoListBySpu")
    @ResponseBody
    public List<SkuInfo> getSkuInfoList(String spuId){
        return skuService.getSkuInfoList(spuId);
    }

    @RequestMapping("saveSku")
    @ResponseBody
    public String addSku(SkuInfo skuInfo){
       skuService.addSkuInfo(skuInfo);
        return "success";
    }
}
