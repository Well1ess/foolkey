package com.example.a29149.yuyuan.DTO;

import android.util.Log;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

import java.util.List;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:某个悬赏的信息以及其所有的申请信息及老师信息
 */

public class ApplicationStudentRewardAsStudentSTCDTO  extends AbstractDTO {
    private static final String TAG = "ApplicationStudentRewar";
    //悬赏DTO
    private RewardDTO rewardDTO;
    //老师申请的DTO
    private List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOS;

    @Override
    public String toString() {
        return "ApplicationStudentRewardAsStuentSTCDTO{" +
                "rewardDTO=" + rewardDTO +
                ", applicationRewardWithTeacherSTCDTOS=" + applicationRewardWithTeacherSTCDTOS +
                '}';
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  特别改造过的方法，主要用于adapter判断这个item是不是我们指定的申请，判断的标准就是reward的Id
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        try {
            ApplicationStudentRewardAsStudentSTCDTO applicationStudentRewardAsStudentSTCDTO = (ApplicationStudentRewardAsStudentSTCDTO) o;
            if (this.getRewardDTO().getId() .equals( applicationStudentRewardAsStudentSTCDTO.getRewardDTO().getId() ) ) {
                Log.d(TAG, "equals: 44   " + this.getRewardDTO().getId());;
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
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
