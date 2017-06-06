package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;

/**
 * 为了测试RecyclerView设置的例子
 * Created by geyao on 2017/6/6.
 */

@Deprecated
public class RewardViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView head;
    public TextView money;
    public TextView label;
    public TextView studentKind;

    public RewardViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.reward_title);
        head = (ImageView) view.findViewById(R.id.photo_circle);
        money = (TextView) view.findViewById(R.id.reward_money);
        label = (TextView) view.findViewById(R.id.label);
//            prestige = (TextView) view.findViewById(R.id.prestige);
        studentKind = (TextView) view.findViewById(R.id.student_kind);
    }
}
