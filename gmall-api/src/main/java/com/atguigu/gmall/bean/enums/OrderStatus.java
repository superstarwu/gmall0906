package com.atguigu.gmall.bean.enums;

/**
 * @param
 * @return
 */
public enum OrderStatus {

    UNPAID("未支付"),
    PAID("已支付"),
    WAITING_DELEVER("待发货"),
    DELEVERED("已发货"),
    CLOSED("已关闭"),
    FINISHED("已完成"),
    SPLIT("订单已拆分");

    private String comment;

    OrderStatus(String comment){
        this.comment = comment;
    }
    private String getComment(){
        return comment;
    }
    private void setComment(String comment){
        this.comment = comment;
    }
}
