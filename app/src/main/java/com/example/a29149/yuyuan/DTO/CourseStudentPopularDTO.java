package com.example.a29149.yuyuan.DTO;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:
 */

public class CourseStudentPopularDTO {
    private StudentDTO studentDTO;
    private CourseStudentDTO courseStudentDTO;

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public CourseStudentDTO getCourseStudentDTO() {
        return courseStudentDTO;
    }

    public void setCourseStudentDTO(CourseStudentDTO courseStudentDTO) {
        this.courseStudentDTO = courseStudentDTO;
    }
}
