package com.atguigu.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.AttrService;
import com.atguigu.gmall.service.ListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ListController {

    @Reference
    private ListService listService;

    @Reference
    private AttrService attrService;


    @RequestMapping("list.html")
    public String getIndex(SkuLsParam skuLsParam, ModelMap modelMap){
        //调用list查询服务
        List<SkuLsInfo> skuLsInfoList = listService.skuLsInfoList(skuLsParam);
        Set<String> valueIds = new HashSet<>();
        for(SkuLsInfo skuLsInfo : skuLsInfoList){
            List<SkuLsAttrValue> skuLsAttrValues = new ArrayList<>();
            skuLsAttrValues = skuLsInfo.getSkuAttrValueList();
            for(SkuLsAttrValue skuLsAttrValue : skuLsAttrValues){
                String valueId = skuLsAttrValue.getValueId();
                valueIds.add(valueId);
            }
        }
        String join = StringUtils.join(valueIds,",");
        modelMap.put("skuLsInfoList",skuLsInfoList);
        //查询数据库中的BaseAttrInfo集合
        List<BaseAttrInfo> attrList = new ArrayList<>();
        attrList = attrService.getAttrInfoByValueIds(join);

        //面包屑中的valueId的集合
        String[] valueId = skuLsParam.getValueId();
     //   Set<String> valueId2 = new HashSet<>();
        List<AttrValueCrumb> attrValueCrumbs = new ArrayList<>();
        if(valueId != null && valueId.length>0){
            Iterator<BaseAttrInfo> baseAttrInfoIterator = attrList.iterator();
            while(baseAttrInfoIterator.hasNext()){
                List<BaseAttrValue> baseAttrValues = baseAttrInfoIterator.next().getAttrValueList();
                for(BaseAttrValue baseAttrValue : baseAttrValues){
                    //列表中的属性值Id
                    String id = baseAttrValue.getId();
                    for(String sid : valueId){
                        //如果id与valueId1相匹配则去掉
                        if(id.equals(sid)){
                            AttrValueCrumb attrValueCrumb = new AttrValueCrumb();
                           String valueName = baseAttrValue.getValueName();
                            String urlParam = getMyUrlParam(skuLsParam,sid);
                            attrValueCrumb.setValueName(valueName);
                            attrValueCrumb.setUrlParam(urlParam);
                            attrValueCrumbs.add(attrValueCrumb);
                            //valueId2.add(sid);
                            baseAttrInfoIterator.remove();
                        }
                    }
                }
            }
        }
        modelMap.put("attrValueSelectedList",attrValueCrumbs);
        modelMap.put("attrList",attrList);
        //根据当前请求参数对象，生成当前请求参数字符串
        String urlParam = getMyUrlParam(skuLsParam);
        modelMap.put("urlParam",urlParam);
        return "list";
    }

    private String getMyUrlParam(SkuLsParam skuLsParam,String... crumbValueId){
        String urlParam = "";
        String catalog3Id = skuLsParam.getCatalog3Id();
        String[] valueIds = skuLsParam.getValueId();
        String keyword = skuLsParam.getKeyword();

        if(StringUtils.isNotBlank(catalog3Id)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if(StringUtils.isNotBlank(keyword)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if(valueIds != null && valueIds.length>0){
            for(String valueId : valueIds){
                if(crumbValueId != null && !crumbValueId[0].equals(valueId)){
                    urlParam = urlParam + "&valueId=" + valueId;
                }
            }
        }
        return urlParam;
    }

    private String getMyUrlParam(SkuLsParam skuLsParam) {
        String urlParam = "";
        String catalog3Id = skuLsParam.getCatalog3Id();
        String keyword = skuLsParam.getKeyword();
        String[] valueId = skuLsParam.getValueId();

        if(StringUtils.isNotBlank(catalog3Id)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" +catalog3Id;
        }

        if(StringUtils.isNotBlank(keyword)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" +keyword;
        }

        if(valueId!=null&&valueId.length>0){
            for (String id : valueId) {
                urlParam = urlParam + "&valueId=" +id;
            }
        }
        return urlParam;
    }
}
