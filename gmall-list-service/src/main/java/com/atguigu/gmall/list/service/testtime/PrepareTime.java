package com.atguigu.gmall.list.service.testtime;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.service.ListService;
import com.atguigu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class PrepareTime {

    @Reference
    private SkuService skuService;

    @Reference
    private ListService listService;

    @Autowired
    private JestClient jestClient;

    @Scheduled(cron="0 0 2 * * ?")
    public void updateElas(){
      List<SkuInfo> skuInfoList =  skuService.getAllSkuInfo();
       for(SkuInfo skuInfo : skuInfoList){
           Set<SkuAttrValue> skuAttrValueSets =  new HashSet<>();
           List<SkuAttrValue> skuAttrValueListEs = new ArrayList<>();
           String skuId = skuInfo.getId();
           SkuAttrValue skuAttrValue2 = new SkuAttrValue();
           SkuAttrValue skuAttrValue = new SkuAttrValue();
           skuAttrValue.setSkuId(skuId);
           List<SkuAttrValue> skuAttrValueList = skuService.getSkuAttrValueListBySkuId(skuAttrValue);
           for(SkuAttrValue skuAttrValue1 : skuAttrValueList){
               String valueId = skuAttrValue1.getValueId();
               skuAttrValue2.setValueId(valueId);
               skuAttrValueSets.add(skuAttrValue2);
           }
           for(SkuAttrValue skuAttrValue1 : skuAttrValueSets){
               skuAttrValueListEs.add(skuAttrValue1);
           }
           skuInfo.setSkuAttrValueList(skuAttrValueListEs);
           SkuLsInfo skuLsInfo = new SkuLsInfo();
//           List<SkuLsAttrValue> skuLsAttrValues = new ArrayList<SkuLsAttrValue>();
//           SkuLsAttrValue skuLsAttrValue = new SkuLsAttrValue();
//
//          for (SkuAttrValue skuAttrValue : skuAttrValueList){
//             String valueId =  skuAttrValue.getValueId();
//             skuLsAttrValue.setValueId(valueId);
//             skuLsAttrValues.add(skuLsAttrValue);
//          }
//          skuLsInfo.setSkuAttrValueList(skuLsAttrValues);
           BeanUtils.copyProperties(skuInfo,skuLsInfo);

           Index index = new Index.Builder(skuLsInfo).index("gmall0906").type("SkuLsInfo").id(skuLsInfo.getId()).build();
           try {
               jestClient.execute(index);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        System.out.println("elasticsearch需要实时更新！！");
    }

}
