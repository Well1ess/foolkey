package com.example.a29149.yuyuan.Model.Order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoStartCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;

/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 未开始上课的Fragment
 */

public class NoClassFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyedCourse;
    private MyListView mRecommand;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_noclass,null);
        mBuyedCourse = (MyListView) view.findViewById(R.id.lv_noStartCourse);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);

        MyListViewNoStartCourseAdapter myListViewNoStartCourseAdapter = new MyListViewNoStartCourseAdapter(mContext);
        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
        mBuyedCourse.setAdapter(myListViewNoStartCourseAdapter);
        mRecommand.setAdapter(myListViewRecommandAdapter);
        return view;
    }
}
