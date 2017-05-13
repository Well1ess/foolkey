package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.ApplicationStateEnum;

/**
 * Created by geyao on 2017/5/10.
 * 所有的申请共有的信息
 */

public class ApplicationAbstract {

    private Long id;

    //申请人

    private Long applicantId;

    //messageId，暂时不做

    private Long messageId;

    //申请的时间

    private Date applyTime;

    //状态 agree, refuse, expired, cancel, processing

    private ApplicationStateEnum state;

    @Override
    public String toString() {
        return "ApplicationAbstract{" +
                "id=" + id +
                ", applicantId=" + applicantId +
                ", messageId=" + messageId +
                ", applyTime=" + applyTime +
                ", state=" + state +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public ApplicationStateEnum getState() {
        return state;
    }

    public void setState(ApplicationStateEnum state) {
        this.state = state;
    }
}
