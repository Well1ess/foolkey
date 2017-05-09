package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CourseStudentStateEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Enum.StudentBaseEnum;
import com.example.a29149.yuyuan.Enum.TeacherRequirementEnum;


/**
 * 悬赏
 * Created by admin on 2017/4/24.
 */
public class CourseStudentDTO extends CourseAbstract{

    // 认证老师,非认证老师,不限
    private TeacherRequirementEnum teacherRequirementEnum;

    // 悬赏状态 待接单,已解决
    private CourseStudentStateEnum courseStudentStateEnum;

    // 学生基础
    private StudentBaseEnum studentBaseEnum;

    // 性别
    private SexTagEnum sexTagEnum;

    @Override
    public String toString() {
        return "CourseStudentDTO{" +
                "teacherRequirementEnum=" + teacherRequirementEnum +
                ", courseStudentStateEnum=" + courseStudentStateEnum +
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

    public CourseStudentStateEnum getCourseStudentStateEnum() {
        return courseStudentStateEnum;
    }

    public void setCourseStudentStateEnum(CourseStudentStateEnum courseStudentStateEnum) {
        this.courseStudentStateEnum = courseStudentStateEnum;
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
