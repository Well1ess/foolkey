package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:
 */

public class RewardWithStudentSTCDTO  extends AbstractDTO {
    private StudentDTO studentDTO;
    private RewardDTO rewardDTO;

    @Override
    public String toString() {
        return "RewardWithStudentSTCDTO{" +
                "studentDTO=" + studentDTO +
                ", rewardDTO=" + rewardDTO +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        try {
            RewardWithStudentSTCDTO dto = (RewardWithStudentSTCDTO) o;
            if (this.getRewardDTO().getId().equals( dto.getRewardDTO().getId() ))
                return true;
        }catch (Exception e){
            //
        }
        return false;

    }

    @Override
    public int hashCode() {
        int result = studentDTO != null ? studentDTO.hashCode() : 0;
        result = 31 * result + (rewardDTO != null ? rewardDTO.hashCode() : 0);
        return result;
    }

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
