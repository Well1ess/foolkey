package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:
 */

public class RewardWithStudentSTCDTO  extends AbstractDTO {
    private StudentDTO studentDTO;
    private RewardDTO rewardDTO;

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public RewardDTO getRewardDTO() {
        return rewardDTO;
    }

    public void setRewardDTO(RewardDTO rewardDTO) {
        this.rewardDTO = rewardDTO;
    }
}
