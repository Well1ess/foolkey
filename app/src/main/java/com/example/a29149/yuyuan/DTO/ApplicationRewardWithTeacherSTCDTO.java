package com.example.a29149.yuyuan.DTO;

/**
 * Created by geyao on 2017/5/10.
 * 申请悬赏的信息和对应老师信息
 */

public class ApplicationRewardWithTeacherSTCDTO {
    //老师（申请者）信息
    private TeacherAllInfoDTO teacherAllInfoDTO;
    //申请信息
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;

    @Override
    public String toString() {
        return "ApplicationRewardWithTeacherSTCDTO{" +
                "teacherAllInfoDTO=" + teacherAllInfoDTO +
                ", applicationStudentRewardDTO=" + applicationStudentRewardDTO +
                '}';
    }

    public TeacherAllInfoDTO getTeacherAllInfoDTO() {
        return teacherAllInfoDTO;
    }

    public void setTeacherAllInfoDTO(TeacherAllInfoDTO teacherAllInfoDTO) {
        this.teacherAllInfoDTO = teacherAllInfoDTO;
    }

    public ApplicationStudentRewardDTO getApplicationStudentRewardDTO() {
        return applicationStudentRewardDTO;
    }

    public void setApplicationStudentRewardDTO(ApplicationStudentRewardDTO applicationStudentRewardDTO) {
        this.applicationStudentRewardDTO = applicationStudentRewardDTO;
    }
}
