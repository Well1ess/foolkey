package com.example.a29149.yuyuan.DTO;

/**
 * Created by GR on 2017/5/23.
 */

public class OrderAskQuestionDTO extends OrderAbstract{

    //被提问者id
    private Long receiverId;

    //问题id
    private Long questionId;

    //优惠券的id
    private Long couponId;

    @Override
    public String toString() {
        return "OrderAskQuestionDTO{" +
                "receiverId=" + receiverId +
                ", questionId=" + questionId +
                ", couponId=" + couponId +
                '}';
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
