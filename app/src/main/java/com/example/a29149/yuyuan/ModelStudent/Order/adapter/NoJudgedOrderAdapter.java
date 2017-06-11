package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.CourseTypeEnum;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.StudentJudgeCourseActivity;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.StudentJudgeRewardActivity;
import com.example.a29149.yuyuan.ModelTeacher.Order.JudgeStudentActivity;
import com.example.a29149.yuyuan.Util.GlobalUtil;

import java.util.List;

/**
 * 未评价订单的adapter
 * Created by geyao on 2017/6/11.
 */

public class NoJudgedOrderAdapter extends YYOrderAdapter {

    /**
     * 构造函数
     * @param data
     * @param context
     */
    public NoJudgedOrderAdapter(List<OrderBuyCourseAsStudentDTO> data, Context context) {
        super(data, context, true, "立即评价");
    }

    @Override
    void buttonClick(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        //根据身份，做不同的处理
        if (GlobalUtil.getInstance().getUserRole().equals("student")) {
            if (orderBuyCourseAsStudentDTO.getOrderDTO().getCourseTypeEnum().compareTo(CourseTypeEnum.学生悬赏 ) == 0) {
                //学生评价悬赏
                commentReward(orderBuyCourseAsStudentDTO);
            }else {
                //学生评价课程
                judgeCourse( orderBuyCourseAsStudentDTO );
            }
        }else {
            // 老师评价订单，即评价学生
            teacherJudgeStudent(orderBuyCourseAsStudentDTO);
        }
    }

    /**
     * 学生 评价 悬赏 订单
     */
    private void commentReward(OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO) {
        //跳转到悬赏订单评价
        Intent intent = new Intent(mContext, StudentJudgeRewardActivity.class);
        //建一个Bundle来存储信息
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", mOrderBuyCourseAsStudentDTO);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 学生评价课程
     * @param orderBuyCourseAsStudentDTO 包含了综合信息的DTO
     */
    private void judgeCourse(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        //跳转到课程订单评价
        Intent intent = new Intent(mContext, StudentJudgeCourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }



    /**
     * 老师对学生进行评价,悬赏和课程通用
     * @param mOrderBuyCourseAsStudentDTO
     */
    private void teacherJudgeStudent(OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO){
        //跳转到评价学生界面
        Intent intent = new Intent(mContext, JudgeStudentActivity.class);
        //使用Bundle存放dto
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", mOrderBuyCourseAsStudentDTO);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
