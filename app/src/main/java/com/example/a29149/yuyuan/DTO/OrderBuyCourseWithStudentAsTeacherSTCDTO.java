package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

/**
 * Created by geyao on 2017/5/12.
 * 某个课程下，学生-订单信息DTO
 */

public class OrderBuyCourseWithStudentAsTeacherSTCDTO  extends AbstractDTO {
    //学生信息
    private StudentDTO studentDTO;
    //订单信息
    private OrderBuyCourseDTO orderBuyCourseDTO;

    @Override
    public String toString() {
        return "OrderBuyCourseWithStudentAsTeacherSTCDTO{" +
                "studentDTO=" + studentDTO +
                ", orderBuyCourseDTO=" + orderBuyCourseDTO +
                '}';
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public OrderBuyCourseDTO getOrderBuyCourseDTO() {
        return orderBuyCourseDTO;
    }

    public void setOrderBuyCourseDTO(OrderBuyCourseDTO orderBuyCourseDTO) {
        this.orderBuyCourseDTO = orderBuyCourseDTO;
    }
}
