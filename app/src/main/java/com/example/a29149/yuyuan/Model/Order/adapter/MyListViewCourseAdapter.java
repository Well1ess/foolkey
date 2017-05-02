package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a29149.yuyuan.Model.Order.global.GlobalValue;
import com.example.a29149.yuyuan.R;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 购买的课程的listView的Adapter
 */

public class MyListViewCourseAdapter extends BaseAdapter {
    private Context mContext;
    private RadioButton rb_bug;//订单选择按钮
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mBuyTime;//购买时长
    private TextView mCourseCost;//课程价格
    private TextView mTeacherState;//老师状态：是否就绪
    private TextView mPay;//学生点击付款按钮

    public MyListViewCourseAdapter(Context context)
    {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        view=View.inflate(mContext, R.layout.buycourse_item,null);
        initView(view);
        //设置订单状态监听
        final GlobalValue globalValue = new GlobalValue();
        rb_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = globalValue.isCheck();
                if(isCheck)
                {
                    if(v==rb_bug)rb_bug.setChecked(false);
                }
                else
                {
                    if(v==rb_bug)rb_bug.setChecked(true);
                }
                globalValue.setCheck(!isCheck);
            }
        });
        return view;
    }

    private void initView(View view) {
        rb_bug = (RadioButton) view.findViewById(R.id.rb_buy);
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mBuyTime = (TextView) view.findViewById(R.id.tv_buyTime);
        mCourseCost = (TextView) view.findViewById(R.id.tv_courseCost);
        mTeacherState = (TextView) view.findViewById(R.id.tv_teacherState);
        mPay = (TextView) view.findViewById(R.id.tv_pay);
    }
}
