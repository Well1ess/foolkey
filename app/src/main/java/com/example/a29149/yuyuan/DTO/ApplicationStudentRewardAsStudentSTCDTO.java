package com.example.a29149.yuyuan.DTO;

import java.util.List;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:某个悬赏的信息以及其所有的申请信息及老师信息
 */

public class ApplicationStudentRewardAsStudentSTCDTO {

    //悬赏DTO
    private RewardDTO rewardDTO;
    //老师申请的DTOS
    private List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOS;

    @Override
    public String toString() {
        return "ApplicationStudentRewardAsStuentSTCDTO{" +
                "rewardDTO=" + rewardDTO +
                ", applicationRewardWithTeacherSTCDTOS=" + applicationRewardWithTeacherSTCDTOS +
                '}';
    }

    public RewardDTO getRewardDTO() {
        if (rewardDTO == null)
            rewardDTO = new RewardDTO();
        return rewardDTO;
    }

    public void setRewardDTO(RewardDTO rewardDTO) {
        this.rewardDTO = rewardDTO;
    }

    public List<ApplicationRewardWithTeacherSTCDTO> getApplicationRewardWithTeacherSTCDTOS() {
        return applicationRewardWithTeacherSTCDTOS;
    }

    public void setApplicationRewardWithTeacherSTCDTOS(List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOS) {
        this.applicationRewardWithTeacherSTCDTOS = applicationRewardWithTeacherSTCDTOS;
    }
}
