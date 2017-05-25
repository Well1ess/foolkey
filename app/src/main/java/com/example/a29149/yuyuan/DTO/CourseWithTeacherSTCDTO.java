package com.example.a29149.yuyuan.DTO;

/**
 * 搜索课程得到的结果
 * Created by geyao on 2017/5/25.
 */

public class CourseWithTeacherSTCDTO {

    //课程信息
    private CourseDTO courseDTO;
    //老师信息
    private TeacherAllInfoDTO teacherAllInfoDTO;

    @Override
    public String toString() {
        return "CourseWithTeacherSTCDTO{" +
                "courseDTO=" + courseDTO +
                ", teacherAllInfoDTO=" + teacherAllInfoDTO +
                '}';
    }

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public TeacherAllInfoDTO getTeacherAllInfoDTO() {
        return teacherAllInfoDTO;
    }

    public void setTeacherAllInfoDTO(TeacherAllInfoDTO teacherAllInfoDTO) {
        this.teacherAllInfoDTO = teacherAllInfoDTO;
    }


}
