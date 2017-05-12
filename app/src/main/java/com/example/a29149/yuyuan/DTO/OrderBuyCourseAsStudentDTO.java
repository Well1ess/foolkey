package com.example.a29149.yuyuan.DTO;

/**
 * Created by geyao on 2017/5/12.
 *学生获取订单，传给app时包括的信息，订单本身，课程DTO，老师的学生DTO，teacherDTO
 */

public class OrderBuyCourseAsStudentDTO {

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
}
