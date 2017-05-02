package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CouponTypeEnum;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class CouponDTO {

    private Long id;
    private Long ownerId;
    private String name;
    private Double level;// 门槛
    private Double value;
    private Date releaseTime;
    private Date deadTime;
    private CouponTypeEnum couponTypeEnum;


    @Override

    public String toString() {

        return "CouponDTO{" +

                "id=" + id +

                ", ownerId=" + ownerId +

                ", name='" + name + '\'' +

                ", level=" + level +

                ", value=" + value +

                ", releaseTime=" + releaseTime +

                ", deadTime=" + deadTime +

                ", couponTypeEnum=" + couponTypeEnum +

                '}';

    }


    public Long getId() {

        return id;

    }


    public void setId(Long id) {

        this.id = id;

    }


    public Long getOwnerId() {

        return ownerId;

    }


    public void setOwnerId(Long ownerId) {

        this.ownerId = ownerId;

    }


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public Double getLevel() {

        return level;

    }


    public void setLevel(Double level) {

        this.level = level;

    }


    public Double getValue() {

        return value;

    }


    public void setValue(Double value) {

        this.value = value;

    }


    public Date getReleaseTime() {

        return releaseTime;

    }


    public void setReleaseTime(Date releaseTime) {

        this.releaseTime = releaseTime;

    }


    public Date getDeadTime() {

        return deadTime;

    }


    public void setDeadTime(Date deadTime) {

        this.deadTime = deadTime;

    }


    public CouponTypeEnum getCouponTypeEnum() {

        return couponTypeEnum;

    }


    public void setCouponTypeEnum(CouponTypeEnum couponTypeEnum) {

        this.couponTypeEnum = couponTypeEnum;

    }

}