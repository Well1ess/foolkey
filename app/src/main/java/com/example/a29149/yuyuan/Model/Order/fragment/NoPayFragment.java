package com.example.a29149.yuyuan.Model.Order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRewardAdapter;
import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;


/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 未付款的Fragment
 */

public class NoPayFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyCourse;
    private MyListView mReward;
    private MyListView mRecommand;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_nopay, null);
        mBuyCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);

        MyListViewCourseAdapter myListViewCourseAdapter = new MyListViewCourseAdapter(mContext);
        MyListViewRewardAdapter myListViewRewardAdapter = new MyListViewRewardAdapter(mContext);
        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
        mBuyCourse.setAdapter(myListViewCourseAdapter);
        mReward.setAdapter(myListViewRewardAdapter);
        mRecommand.setAdapter(myListViewRecommandAdapter);
        return view;
    }
}
