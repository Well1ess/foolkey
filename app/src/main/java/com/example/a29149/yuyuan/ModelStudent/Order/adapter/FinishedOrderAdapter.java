package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;

import java.util.List;

/**
 * 已完成订单
 * Created by geyao on 2017/6/11.
 */

public class FinishedOrderAdapter extends YYOrderAdapter {

    /**
     * 构造函数
     * @param data
     * @param context
     */
    public FinishedOrderAdapter(List<OrderBuyCourseAsStudentDTO> data, Context context) {
        super(data, context, true, "发起维权");
    }


    @Override
    void buttonClick(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        Toast.makeText(mContext, "发起维权！！", Toast.LENGTH_SHORT).show();
    }
}
