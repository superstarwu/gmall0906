package com.atguigu.gmall.bean.enums;

/**
 * @param
 * @return
 */
public enum  PaymentStatus {

    UNPAID("支付中"),
    PAID("已支付"),
    PAY_FALL("支付失败"),
    CLOSED("关闭");

    private String name;

    PaymentStatus (String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
