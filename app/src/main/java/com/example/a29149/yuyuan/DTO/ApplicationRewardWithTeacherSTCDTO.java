package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

/**
 * Created by geyao on 2017/5/10.
 * 申请悬赏的信息和对应老师信息
 */

public class ApplicationRewardWithTeacherSTCDTO  extends AbstractDTO {
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


    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  这个类本身的情况就是用于处理老师的申请，在拒绝了一条申请之后，就会把这个对象从adapter的数据里面删除
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        try {
            ApplicationRewardWithTeacherSTCDTO applicationRewardWithTeacherSTCDTO = (ApplicationRewardWithTeacherSTCDTO) o;
            if (this.getApplicationStudentRewardDTO().getId() .equals( applicationRewardWithTeacherSTCDTO.getApplicationStudentRewardDTO().getId() ))
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }


}
