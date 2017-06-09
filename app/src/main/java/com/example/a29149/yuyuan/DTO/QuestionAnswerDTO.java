package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.Enum.QuestionStateEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;

/**
 * 问答的DTO
 * Created by GR on 2017/5/22.
 */

public class QuestionAnswerDTO  extends AbstractDTO {

    private Long id;

    //提问者Id
    private Long askerId;

    //回答者的Id
    private Long answerId;

    //问题的标题
    private String title;

    //问题的描述
    private String questionContent;

    //问题的标价，虚拟币
    private Double price;

    //问题的创建时间
    private Date createdTime;

    //失效时间
    private Date invalidTime;

    //问题的状态 待付款, 待回答, 已回答, 已过期
    private QuestionStateEnum questionStateEnum;

    //围观的人数
    private Integer onlookerNumber;

    //回答的时间
    private Date answerTime;

    //回答的内容
    private String answerContent;

    //回答的最后更新时间
    private Date lastUpdateTime;

    //提问属于什么类别的
    private TechnicTagEnum technicTagEnum;

    @Override
    public String toString() {
        return "QuestionAnswerDTO{" +
                "id=" + id +
                ", askerId=" + askerId +
                ", answerId=" + answerId +
                ", title='" + title + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", price=" + price +
                ", createdTime=" + createdTime +
                ", invalidTime=" + invalidTime +
                ", questionStateEnum=" + questionStateEnum +
                ", onlookerNumber=" + onlookerNumber +
                ", answerTime=" + answerTime +
                ", answerContent='" + answerContent + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", technicTagEnum=" + technicTagEnum +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAskerId() {
        return askerId;
    }

    public void setAskerId(Long askerId) {
        this.askerId = askerId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public QuestionStateEnum getQuestionStateEnum() {
        return questionStateEnum;
    }

    public void setQuestionStateEnum(QuestionStateEnum questionStateEnum) {
        this.questionStateEnum = questionStateEnum;
    }

    public Integer getOnlookerNumber() {
        return onlookerNumber;
    }

    public void setOnlookerNumber(Integer onlookerNumber) {
        this.onlookerNumber = onlookerNumber;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public TechnicTagEnum getTechnicTagEnum() {
        return technicTagEnum;
    }

    public void setTechnicTagEnum(TechnicTagEnum technicTagEnum) {
        this.technicTagEnum = technicTagEnum;
    }
}
