package com.example.a29149.yuyuan.DTO;

/**
 * Created by geyao on 2017/5/10.
 * 申请悬赏的DTO
 */

public class ApplicationStudentRewardDTO extends ApplicationAbstract {

    //学生(发布悬赏人)的id
    private Long studentId;

    //悬赏的id
    private Long courseId;

    @Override
    public String toString() {
        return "ApplicationStudentRewardDTO{" +
                "studentId=" + studentId +
                ", orderId=" + courseId +
                "} " + super.toString();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
