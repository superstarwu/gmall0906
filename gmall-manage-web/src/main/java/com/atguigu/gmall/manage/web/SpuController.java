package com.atguigu.gmall.manage.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.web.Util.ManageUtil;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class SpuController {

    @Reference
    private SpuService spuService;

    @ResponseBody
    @RequestMapping("fileUpload")
    public String fileupload(@RequestParam("file")MultipartFile multipartFile){
        String imgUrl = ManageUtil.getImgUrl(multipartFile);
        return imgUrl;
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId){
         return spuService.getSpuSaleAttrList(spuId);
    }

    @RequestMapping("spuImgList")
    @ResponseBody
    public List<SpuImage> getImgList(String spuId){
        return spuService.getSpuImageList(spuId);
    }

    @RequestMapping("saveSpu")
    @ResponseBody
    public String saveSpu(SpuInfo spuInfo){
        spuService.addSpuInfo(spuInfo);
        return "success";
    }

    @ResponseBody
    @RequestMapping("spuList")
    public List<SpuInfo> getSpuInfo(String catalog3Id){
        List<SpuInfo> spuInfos = spuService.selectSpoInfoList(catalog3Id);
        return spuInfos;
    }

    @ResponseBody
    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> getAttrValue(){
          return spuService.getBaseSaleAttrList();
    }
}
