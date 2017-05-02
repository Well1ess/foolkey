package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;

import java.util.Arrays;
import java.util.List;
/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 以View的形式的PagerAdapter，现已废弃
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> views;
    private List<String> mTags = Arrays.asList("购物车", "未付款", "未上课", "未评价", "全部订单");
    private Context mContext;
    private MyListView mBuyCourse;
    private MyListView mXuanShang;
    private MyListView mRecommand;

    public MyPagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.mContext = context;
    }

    private View initView()
    {
        final View view=View.inflate(mContext, R.layout.fragment_viewpager_nopay,null);
        mBuyCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mXuanShang = (MyListView) view.findViewById(R.id.lv_xuanshang);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);

        MyListViewCourseAdapter myListViewCourseAdapter = new MyListViewCourseAdapter(mContext);
        mBuyCourse.setAdapter(myListViewCourseAdapter);
        mXuanShang.setAdapter(myListViewCourseAdapter);
        mRecommand.setAdapter(myListViewCourseAdapter);
        return view;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = initView();
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return mTags.get(position);
    }
}
