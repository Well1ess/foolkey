package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CourseTeacherStateEnum;

/**
 * Created by geyao on 2017/5/12.
 * 课程信息
 */

public class CourseDTO extends CourseAbstract{

        //建议时长 0.5h/1h...
        private Float duration;


        //开课状态
        private CourseTeacherStateEnum courseTeacherStateEnum;

        //销量
        private Integer sales;

        //平均得分
        private Float averageScore;

        @Override
        public String toString() {
            return "CourseTeacherDTO{" +
                    "duration=" + duration +
                    ", courseTeacherStateEnum=" + courseTeacherStateEnum +
                    ", sales=" + sales +
                    ", averageScore=" + averageScore +
                    "} " + super.toString();
        }

        public Float getDuration() {
            return duration;
        }

        public void setDuration(Float duration) {
            this.duration = duration;
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

        public Float getAverageScore() {
            return averageScore;
        }

        public void setAverageScore(Float averageScore) {
            this.averageScore = averageScore;
        }


}
