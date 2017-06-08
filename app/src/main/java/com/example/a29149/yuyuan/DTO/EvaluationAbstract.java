package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.Enum.EvaluationStateEnum;

/**
 * Created by GR on 2017/5/24.
 */

public class EvaluationAbstract  extends AbstractDTO {

    private Long id;

    //评价人的id
    private Long creatorId;

    //被评价者id
    private Long acceptor_id;

    //相关的订单
    private Long orderId;

    //评价的状态，目前没有什么用
    private EvaluationStateEnum evaluationStateEnum;

    //分数
    private Double score;

    @Override
    public String toString() {
        return "EvaluationAbstract{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", acceptor_id=" + acceptor_id +
                ", orderId=" + orderId +
                ", evaluationStateEnum=" + evaluationStateEnum +
                ", score=" + score +
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

    public Long getAcceptor_id() {
        return acceptor_id;
    }

    public void setAcceptor_id(Long acceptor_id) {
        this.acceptor_id = acceptor_id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public EvaluationStateEnum getEvaluationStateEnum() {
        return evaluationStateEnum;
    }

    public void setEvaluationStateEnum(EvaluationStateEnum evaluationStateEnum) {
        this.evaluationStateEnum = evaluationStateEnum;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
