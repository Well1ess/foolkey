package com.example.a29149.yuyuan.DTO;

import com.example.a29149.yuyuan.Enum.EvaluationStateEnum;

/**
 * Created by GR on 2017/5/24.
 */

public class EvaluationAbstract {

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
}
