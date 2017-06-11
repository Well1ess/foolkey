package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;

import java.util.List;

/**
 * 未付款订单
 * Created by geyao on 2017/6/11.
 */

public class NoPayOrderAdapter extends YYOrderAdapter {
    private static final String TAG = "NoPayOrderAdapter";

    /**
     * 构造函数
     * @param data
     * @param context
     */
    public NoPayOrderAdapter(List<OrderBuyCourseAsStudentDTO> data, Context context) {
        super(data, context, true, "立即付款");
    }

    /**
     * 按钮的点击监听事件
     * @param orderBuyCourseAsStudentDTO
     */
    @Override
    void buttonClick(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        //TODO 做立即付款
        Toast.makeText(mContext, "立即付款", Toast.LENGTH_SHORT);
    }
}
