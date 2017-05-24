package com.example.a29149.yuyuan.DTO;

/**
 * 老师申请悬赏的DTO
 * Created by GR on 2017/5/24.
 */

public class ApplicationRewardWithStudentSTCDTO {

    //申请信息
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;
    //悬赏DTO
    private RewardDTO rewardDTO;
    //学生(悬赏发布者)信息DTO
    private StudentDTO studentDTO;

    @Override
    public String toString() {
        return "ApplicationRewardWithStudentSTCDTO{" +
                "applicationStudentRewardDTO=" + applicationStudentRewardDTO +
                ", rewardDTO=" + rewardDTO +
                ", studentDTO=" + studentDTO +
                '}';
    }

    public ApplicationStudentRewardDTO getApplicationStudentRewardDTO() {
        return applicationStudentRewardDTO;
    }

    public void setApplicationStudentRewardDTO(ApplicationStudentRewardDTO applicationStudentRewardDTO) {
        this.applicationStudentRewardDTO = applicationStudentRewardDTO;
    }

    public RewardDTO getRewardDTO() {
        return rewardDTO;
    }

    public void setRewardDTO(RewardDTO rewardDTO) {
        this.rewardDTO = rewardDTO;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }
}

