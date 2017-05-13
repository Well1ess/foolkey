package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CourseTypeEnum;
import com.example.a29149.yuyuan.Enum.TeachMethodEnum;

/**
 * Created by geyao on 2017/5/12.
 */

public class OrderBuyCourseDTO extends OrderAbstract{
    //课程id
    private Long courseId;

    //教师id
    private Long teacherId;

    //购买时长，0.5h，1.0h，1.5h。。。
    private Double number;

    //折扣，默认是1.0
    private Double cutOffPercent;

    //优惠券的id
    private Long couponId;

    //授课方法，线上, 线下, 不限
    private TeachMethodEnum teachMethodEnum;

    //课程结束的时间
    private Date lessonEndTime;

    //课程类型 老师课程, 学生悬赏,
    private CourseTypeEnum courseTypeEnum;

    public OrderBuyCourseDTO() {
        super();
    }

    @Override
    public String toString() {
        return "OrderBuyCourseDTO{" +
                "courseId=" + courseId +
                ", teacherId=" + teacherId +
                ", number=" + number +
                ", cutOffPercent=" + cutOffPercent +
                ", couponId=" + couponId +
                ", teachMethodEnum=" + teachMethodEnum +
                ", lessonEndTime=" + lessonEndTime +
                ", courseTypeEnum=" + courseTypeEnum +
                "} " + super.toString();
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getCutOffPercent() {
        return cutOffPercent;
    }

    public void setCutOffPercent(Double cutOffPercent) {
        this.cutOffPercent = cutOffPercent;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public TeachMethodEnum getTeachMethodEnum() {
        return teachMethodEnum;
    }

    public void setTeachMethodEnum(TeachMethodEnum teachMethodEnum) {
        this.teachMethodEnum = teachMethodEnum;
    }

    public Date getLessonEndTime() {
        return lessonEndTime;
    }

    public void setLessonEndTime(Date lessonEndTime) {
        this.lessonEndTime = lessonEndTime;
    }

    public CourseTypeEnum getCourseTypeEnum() {
        return courseTypeEnum;
    }

    public void setCourseTypeEnum(CourseTypeEnum courseTypeEnum) {
        this.courseTypeEnum = courseTypeEnum;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
