package com.atguigu.gmall.manage.service.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuAttrValue;
import com.atguigu.gmall.bean.SkuImage;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.manage.service.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.manage.service.mapper.SkuImageMapper;
import com.atguigu.gmall.manage.service.mapper.SkuInfoMapper;
import com.atguigu.gmall.manage.service.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService{

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SkuInfo> getSkuInfoList(String spuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        return skuInfoMapper.select(skuInfo);
    }

    @Override
    public void addSkuInfo(SkuInfo skuInfo) {
        //保存sku信息
        skuInfoMapper.insertSelective(skuInfo);
        String skuId = skuInfo.getId();
        //保存sku平台属性信息
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for(SkuAttrValue skuAttrValue : skuAttrValueList){
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insertSelective(skuAttrValue);
        }
        //保存sku销售属性信息
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for(SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList){
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
        }
        //保存sku图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for(SkuImage skuImage : skuImageList){
            skuImage.setSkuId(skuId);
            skuImageMapper.insertSelective(skuImage);
        }
    }
    @Override
    public SkuInfo getSkuInfo(String skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        SkuInfo skuInfo1 = skuInfoMapper.selectOne(skuInfo);

        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
        skuInfo1.setSkuImageList(skuImageList);
        return skuInfo1;
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {
        List<SkuInfo> skuInfoList = skuSaleAttrValueMapper.selectSpuSaleAttrValueListBySpu(spuId);
        return skuInfoList;
    }
    @Override
    public SkuInfo getItemData(String skuId) {
        SkuInfo skuInfo = null;
        //从缓存中取出sku的数据
        Jedis jedis = redisUtil.getJedis();
       // String ping = jedis.ping();
        String skuInfoStr = jedis.get("sku:" + skuId + ":info");
        skuInfo = JSON.parseObject(skuInfoStr,SkuInfo.class);

        //从db中取出sku的数据
        if(skuInfo == null){//缓存中没有数据
            System.out.println("发现缓存中没有" + skuId + "商品数据,申请分布式锁");
            //拿到分布式锁
            String OK = jedis.set("sku:" + skuId + ":lock" ,"1","nx","px",10000);
           if(StringUtils.isBlank(OK)){
               System.out.println("未拿到分布式锁,开始自旋三秒");
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               return getItemData(skuId);
           }else{
               System.out.println("分布式锁申请成功，成功访问数据库");
               //拿到缓存锁查询数据库
               skuInfo = getSkuInfo(skuId);
           }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("访问数据库后，归还锁，将商品存入缓存");
            jedis.del("sku:" + skuId + ":lock");
           //同步缓存一份
            jedis.set("sku:" + skuId + ":info",JSON.toJSONString(skuInfo));
        }
        jedis.close();
        return skuInfo;
    }

    @Override
    public List<SkuInfo> getSkuInfoListByCatalog3Id(String catalog3Id) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setCatalog3Id(catalog3Id);
       List<SkuInfo> skuInfoList = skuInfoMapper.select(skuInfo);
        for(SkuInfo skuInfo1 : skuInfoList){
            SkuAttrValue skuAttrValue = new SkuAttrValue();
            skuAttrValue.setSkuId(skuInfo1.getId());
            List<SkuAttrValue> skuAttrValueList = skuAttrValueMapper.select(skuAttrValue);
            skuInfo1.setSkuAttrValueList(skuAttrValueList);
        }
        return skuInfoList;
    }

    @Override
    public SkuInfo getSkuInfoBySkuId(String skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        return skuInfoMapper.selectOne(skuInfo);
    }
}
