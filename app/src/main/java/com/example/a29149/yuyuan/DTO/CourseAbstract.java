package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.CourseTimeDayEnum;
import com.example.a29149.yuyuan.Enum.TeachMethodEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class CourseAbstract {

    private Long id;
    private Long creatorId;
    private TechnicTagEnum technicTagEnum;//标签

    private String topic;//标题

    private String description;//内容

    private Double price;//价格
    private TeachMethodEnum teachMethodEnum;
    private CourseTimeDayEnum courseTimeDayEnum;


    @Override

    public String toString() {

        return "CourseAbstract{" +

                "id=" + id +

                ", creatorId=" + creatorId +

                ", technicTagEnum=" + technicTagEnum +

                ", topic='" + topic + '\'' +

                ", description='" + description + '\'' +

                ", price=" + price +

                ", teachMethodEnum=" + teachMethodEnum +

                ", courseTimeDayEnum=" + courseTimeDayEnum +

                '}';

    }



    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }



    public Long getCreatorId() {

        return creatorId;

    }



    public void setCreatorId(Long creatorId) {

        this.creatorId = creatorId;

    }



    public TechnicTagEnum getTechnicTagEnum() {

        return technicTagEnum;

    }



    public void setTechnicTagEnum(TechnicTagEnum technicTagEnum) {

        this.technicTagEnum = technicTagEnum;

    }



    public String getTopic() {

        return topic;

    }



    public void setTopic(String topic) {

        this.topic = topic;

    }



    public String getDescription() {

        return description;

    }



    public void setDescription(String description) {

        this.description = description;

    }



    public Double getPrice() {

        return price;

    }



    public void setPrice(Double price) {

        this.price = price;

    }



    public TeachMethodEnum getTeachMethodEnum() {

        return teachMethodEnum;

    }



    public void setTeachMethodEnum(TeachMethodEnum teachMethodEnum) {

        this.teachMethodEnum = teachMethodEnum;

    }



    public CourseTimeDayEnum getCourseTimeDayEnum() {

        return courseTimeDayEnum;

    }



    public void setCourseTimeDayEnum(CourseTimeDayEnum courseTimeDayEnum) {

        this.courseTimeDayEnum = courseTimeDayEnum;

    }
}
