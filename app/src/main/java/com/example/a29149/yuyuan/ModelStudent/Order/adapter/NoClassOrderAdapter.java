package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;

import java.util.List;

/**
 * 未上课订单的adapter
 * Created by geyao on 2017/6/11.
 */

public class NoClassOrderAdapter extends YYOrderAdapter {
    public NoClassOrderAdapter(List<OrderBuyCourseAsStudentDTO> data, Context context) {
        super(data, context, true,  "联系对方");
    }

    @Override
    void buttonClick(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        //TODO聊天功能并没有做
        Toast.makeText(mContext, "聊天敬请期待", Toast.LENGTH_SHORT);
    }
}
