package com.example.a29149.yuyuan.DTO;

import java.util.List;

/**
 * Created by geyao on 2017/5/12.
 * 课程订单的DTO
 */

public class OrderBuyCourseAsTeacherSTCDTO {
    //课程信息
    private CourseDTO courseDTO;

    //订单-学生信息
    private List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOS;

    @Override
    public String toString() {
        return "OrderBuyCourseAsTeacherSTCDTO{" +
                "courseDTO=" + courseDTO +
                ", orderBuyCourseWithStudentAsTeacherSTCDTOS=" + orderBuyCourseWithStudentAsTeacherSTCDTOS +
                '}';
    }

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public List<OrderBuyCourseWithStudentAsTeacherSTCDTO> getOrderBuyCourseWithStudentAsTeacherSTCDTOS() {
        return orderBuyCourseWithStudentAsTeacherSTCDTOS;
    }

    public void setOrderBuyCourseWithStudentAsTeacherSTCDTOS(List<OrderBuyCourseWithStudentAsTeacherSTCDTO> orderBuyCourseWithStudentAsTeacherSTCDTOS) {
        this.orderBuyCourseWithStudentAsTeacherSTCDTOS = orderBuyCourseWithStudentAsTeacherSTCDTOS;
    }
}
