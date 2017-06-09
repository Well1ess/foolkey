package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

import java.util.List;

/**
 * Created by geyao on 2017/5/13.
 * 悬赏信息和学生订单的信息
 */

public class OrderBuyRewardAsTeacherSTCDTO  extends AbstractDTO {

    //悬赏信息
    private RewardDTO rewardDTO;

    //订单-学生信息
    private List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOS;

    @Override
    public String toString() {
        return "OrderBuyRewardAsTeacherSTCDTO{" +
                "rewardDTO=" + rewardDTO +
                ", orderBuyCourseWithStudentAsTeacherSTCDTOS=" + orderBuyCourseWithStudentAsTeacherSTCDTOS +
                '}';
    }

    public RewardDTO getRewardDTO() {
        return rewardDTO;
    }

    public void setRewardDTO(RewardDTO rewardDTO) {
        this.rewardDTO = rewardDTO;
    }

    public List<OrderBuyCourseWithStudentAsTeacherSTCDTO> getOrderBuyCourseWithStudentAsTeacherSTCDTOS() {
        return orderBuyCourseWithStudentAsTeacherSTCDTOS;
    }

    public void setOrderBuyCourseWithStudentAsTeacherSTCDTOS(List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOS) {
        this.orderBuyCourseWithStudentAsTeacherSTCDTOS = orderBuyCourseWithStudentAsTeacherSTCDTOS;
    }
}
