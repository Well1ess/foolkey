package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.example.a29149.yuyuan.Model.Order.global.GlobalValue;
import com.example.a29149.yuyuan.R;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 购买的课程的listView的Adapter
 */

public class MyListViewRewardAdapter extends BaseAdapter {
    private Context mContext;

    public MyListViewRewardAdapter(Context context)
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
        final View view ;
        view=View.inflate(mContext, R.layout.xuanshang_item,null);
        final RadioButton rb_bug = (RadioButton) view.findViewById(R.id.rb_buy);
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
}
