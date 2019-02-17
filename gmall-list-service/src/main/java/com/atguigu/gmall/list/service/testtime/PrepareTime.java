package com.atguigu.gmall.list.service.testtime;

import com.alibaba.dubbo.config.annotation.Reference;
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
import java.util.List;

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
           SkuLsInfo skuLsInfo = new SkuLsInfo();
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
