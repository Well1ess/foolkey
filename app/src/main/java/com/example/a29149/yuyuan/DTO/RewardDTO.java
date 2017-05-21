package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.RewardStateEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.StudentBaseEnum;
import com.example.a29149.yuyuan.Enum.TeacherRequirementEnum;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:悬赏信息DTO
 */

public class RewardDTO extends CourseAbstract{

    // 认证老师,非认证老师,不限
    private TeacherRequirementEnum teacherRequirementEnum;

    // 悬赏状态 待接单,已解决
    private RewardStateEnum rewardStateEnum;

    // 学生基础
    private StudentBaseEnum studentBaseEnum;

    // 性别
    private SexTagEnum sexTagEnum;

    @Override
    public String toString() {
        return "RewardDTO{" +
                "teacherRequirementEnum=" + teacherRequirementEnum +
                ", rewardStateEnum=" + rewardStateEnum +
                ", studentBaseEnum=" + studentBaseEnum +
                ", sexTagEnum=" + sexTagEnum +
                '}';
    }

    public TeacherRequirementEnum getTeacherRequirementEnum() {
        return teacherRequirementEnum;
    }

    public void setTeacherRequirementEnum(TeacherRequirementEnum teacherRequirementEnum) {
        this.teacherRequirementEnum = teacherRequirementEnum;
    }

    public RewardStateEnum getRewardStateEnum() {
        return rewardStateEnum;
    }

    public void setRewardStateEnum(RewardStateEnum rewardStateEnum) {
        this.rewardStateEnum = rewardStateEnum;
    }

    public StudentBaseEnum getStudentBaseEnum() {
        return studentBaseEnum;
    }

    public void setStudentBaseEnum(StudentBaseEnum studentBaseEnum) {
        this.studentBaseEnum = studentBaseEnum;
    }

    public SexTagEnum getSexTagEnum() {
        return sexTagEnum;
    }

    public void setSexTagEnum(SexTagEnum sexTagEnum) {
        this.sexTagEnum = sexTagEnum;
    }
}
