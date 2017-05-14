package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.VerifyStateEnum;

/**
 * Created by 麻磊 on 2017/5/9.
 * Description:老师信息DTO
 */

public class TeacherDTO {
    private Long id;
    private Float teacherAverageScore;
    private Float teachingTime;
    private Integer teachingNumber;
    //认证状态，分为 进行中，已认证，失败 三种
    private VerifyStateEnum verifyState;
    //关注者人数
    private Integer followerNumber;
    public TeacherDTO() {
        super();
    }
    @Override
    public String toString() {
        return "TeacherDTO{" +
                "id=" + id +
                ", teacherAverageScore=" + teacherAverageScore +
                ", teachingTime=" + teachingTime +
                ", teachingNumber=" + teachingNumber +
                ", verifyState=" + verifyState +
                ", followerNumber=" + followerNumber +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Float getTeacherAverageScore() {
        return teacherAverageScore;
    }

    public void setTeacherAverageScore(Float teacherAverageScore) {
        this.teacherAverageScore = teacherAverageScore;
    }

    public Float getTeachingTime() {
        return teachingTime;
    }

    public void setTeachingTime(Float teachingTime) {
        this.teachingTime = teachingTime;
    }

    public Integer getTeachingNumber() {
        return teachingNumber;
    }

    public void setTeachingNumber(Integer teachingNumber) {
        this.teachingNumber = teachingNumber;
    }

    public VerifyStateEnum getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(VerifyStateEnum verifyState) {
        this.verifyState = verifyState;
    }

    public Integer getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(Integer followerNumber) {
        this.followerNumber = followerNumber;
    }

}





