package com.example.a29149.yuyuan.DTO;

/**
 * Created by GR on 2017/5/24.
 */

public class EvaluationCourseDTO  extends EvaluationAbstract {

    //评价的文字内容
    private String content;

    //课程id
    private Long courseId;

    //图片1地址
    private String pic1Path;

    private String pic2Path;

    private String pic3Path;

    private String pic4Path;


    @Override
    public String toString() {
        return "EvaluationCourseDTO{" +
                "content='" + content + '\'' +
                ", courseId=" + courseId +
                ", pic1Path='" + pic1Path + '\'' +
                ", pic2Path='" + pic2Path + '\'' +
                ", pic3Path='" + pic3Path + '\'' +
                ", pic4Path='" + pic4Path + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getPic1Path() {
        return pic1Path;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path;
    }

    public String getPic2Path() {
        return pic2Path;
    }

    public void setPic2Path(String pic2Path) {
        this.pic2Path = pic2Path;
    }

    public String getPic3Path() {
        return pic3Path;
    }

    public void setPic3Path(String pic3Path) {
        this.pic3Path = pic3Path;
    }

    public String getPic4Path() {
        return pic4Path;
    }

    public void setPic4Path(String pic4Path) {
        this.pic4Path = pic4Path;
    }
}
