package com.example.a29149.yuyuan.DTO;

/**
 * Created by 张丽华 on 2017/5/9.
 * Description:
 */

public class CourseTeacherPopularDTO {

    private CourseTeacherDTO courseTeacherDTO;
    private TeacherAllInfoDTO teacherAllInfoDTO;

    public CourseTeacherDTO getCourseTeacherDTO() {
        return courseTeacherDTO;
    }

    public void setCourseTeacherDTO(CourseTeacherDTO courseTeacherDTO) {
        this.courseTeacherDTO = courseTeacherDTO;
    }

    public TeacherAllInfoDTO getTeacherAllInfoDTO() {
        return teacherAllInfoDTO;
    }

    public void setTeacherAllInfoDTO(TeacherAllInfoDTO teacherAllInfoDTO) {
        this.teacherAllInfoDTO = teacherAllInfoDTO;
    }
}
