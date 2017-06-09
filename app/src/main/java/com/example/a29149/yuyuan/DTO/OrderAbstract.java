package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;

/**
 * Created by geyao on 2017/5/12.
 */

public class OrderAbstract  extends AbstractDTO {

    private Long id;

    //金额，虚拟币
    private Double amount;

    //创建时间
    private Date createdTime;

    //支付时间
    private Date payTime;

    //存活时间，如果一直不付款，则过了以后就会取消
    private Date existingTime;

    //用户的id，一般都是studentId
    private Long userId;

    //订单的状态
    private OrderStateEnum orderStateEnum;

    @Override
    public String toString() {
        return "AbstractOrder{" +
                "id=" + id +
                ", amount=" + amount +
                ", createdTime=" + createdTime +
                ", payTime=" + payTime +
                ", existingTime=" + existingTime +
                ", userId=" + userId +
                ", orderStateEnum=" + orderStateEnum +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getExistingTime() {
        return existingTime;
    }

    public void setExistingTime(Date existingTime) {
        this.existingTime = existingTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStateEnum getOrderStateEnum() {
        return orderStateEnum;
    }

    public void setOrderStateEnum(OrderStateEnum orderStateEnum) {
        this.orderStateEnum = orderStateEnum;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
