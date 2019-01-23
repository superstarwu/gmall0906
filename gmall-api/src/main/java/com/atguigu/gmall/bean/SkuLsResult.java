package com.atguigu.gmall.bean;

import java.io.Serializable;
import java.util.List;

public class SkuLsResult implements Serializable{
    private static final long serialVersionUID = -6316905946045506094L;

    private int Total;
    private List<String> valueList;
    private List<SkuLsInfo> skuLsInfoList;

    public SkuLsResult() {
    }

    public SkuLsResult(int total, List<String> valueList, List<SkuLsInfo> skuLsInfoList) {
        Total = total;
        this.valueList = valueList;
        this.skuLsInfoList = skuLsInfoList;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public List<SkuLsInfo> getSkuLsInfoList() {
        return skuLsInfoList;
    }

    public void setSkuLsInfoList(List<SkuLsInfo> skuLsInfoList) {
        this.skuLsInfoList = skuLsInfoList;
    }
}
