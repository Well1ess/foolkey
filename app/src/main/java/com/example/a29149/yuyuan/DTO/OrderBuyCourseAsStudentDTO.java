package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;

/**
 * Created by geyao on 2017/5/12.
 *学生获取订单，传给app时包括的信息，订单本身，课程DTO，老师的学生DTO，teacherDTO
 */

public class OrderBuyCourseAsStudentDTO  extends AbstractDTO {

    private OrderBuyCourseDTO orderDTO;

    private CourseAbstract course;

    private StudentDTO studentDTO;

    private TeacherDTO teacherDTO;

    public CourseAbstract getCourse() {
        return course;
    }

    public void setCourse(CourseAbstract course) {
        this.course = course;
    }

    public OrderBuyCourseAsStudentDTO() {
        super();
    }

    public OrderBuyCourseDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderBuyCourseDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    @Override
    public String toString() {
        return "OrderBuyCourseAsStudentDTO{" +
                "orderDTO=" + orderDTO +
                ", course=" + course +
                ", studentDTO=" + studentDTO +
                ", teacherDTO=" + teacherDTO +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        try {
            OrderBuyCourseAsStudentDTO dto = (OrderBuyCourseAsStudentDTO)o;
            if (this.orderDTO.getId().equals( dto.getOrderDTO().getId() )){
                //只要订单的id相同，我就认为他俩相同
                return true;
            }
        }catch (Exception e){
            //
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = orderDTO != null ? orderDTO.hashCode() : 0;
        result = 31 * result + (course != null ? course.hashCode() : 0);
        result = 31 * result + (studentDTO != null ? studentDTO.hashCode() : 0);
        result = 31 * result + (teacherDTO != null ? teacherDTO.hashCode() : 0);
        return result;
    }
}
