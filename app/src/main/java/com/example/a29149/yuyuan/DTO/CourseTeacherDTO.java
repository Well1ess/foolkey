package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CourseTeacherStateEnum;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class CourseTeacherDTO extends CourseAbstract{
    //预计时长 0.5h/1h...
    private Double duration;

    //课程数
    private Integer classAmount;

    private CourseTeacherStateEnum courseTeacherStateEnum;
    private Integer sales;

    private Double averageScore;



    @Override

    public String toString() {

        return "CourseTeacherDTO{" +

                "duration=" + duration +

                ", classAmount=" + classAmount +

                ", courseTeacherStateEnum=" + courseTeacherStateEnum +

                ", sales=" + sales +

                ", averageScore=" + averageScore +

                '}';

    }



    public Double getDuration() {

        return duration;

    }



    public void setDuration(Double duration) {

        this.duration = duration;

    }



    public Integer getClassAmount() {

        return classAmount;

    }



    public void setClassAmount(Integer classAmount) {

        this.classAmount = classAmount;

    }



    public CourseTeacherStateEnum getCourseTeacherStateEnum() {

        return courseTeacherStateEnum;

    }



    public void setCourseTeacherStateEnum(CourseTeacherStateEnum courseTeacherStateEnum) {

        this.courseTeacherStateEnum = courseTeacherStateEnum;

    }



    public Integer getSales() {

        return sales;

    }



    public void setSales(Integer sales) {

        this.sales = sales;

    }



    public Double getAverageScore() {

        return averageScore;

    }



    public void setAverageScore(Double averageScore) {

        this.averageScore = averageScore;

    }

}
