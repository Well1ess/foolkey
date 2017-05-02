package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.a29149.yuyuan.R;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 购买的课程的listView的Adapter
 */

public class MyListViewNoStartCourseAdapter extends BaseAdapter {
    private Context mContext;

    public MyListViewNoStartCourseAdapter(Context context)
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
        view=View.inflate(mContext, R.layout.nostartcourse_item,null);
        return view;
    }
}
