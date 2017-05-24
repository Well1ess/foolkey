package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a29149.yuyuan.ModelStudent.Order.fragment.FinishOrderFragment;
import com.example.a29149.yuyuan.ModelStudent.Order.fragment.NoClassFragment;
import com.example.a29149.yuyuan.ModelStudent.Order.fragment.NoCommentFragment;
import com.example.a29149.yuyuan.ModelStudent.Order.fragment.NoPayFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * ViewPager的FragmentPagerAdapter
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static List<Fragment> fragments;
    private List<String> mTags = Arrays.asList( "未付款", "未上课", "未评价",
            "已完成");

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initView();
    }

    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(new NoPayFragment());
        fragments.add(new NoClassFragment());
        fragments.add(new NoCommentFragment());
        fragments.add(new FinishOrderFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //返回TabLayout的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTags.get(position);
    }
}
