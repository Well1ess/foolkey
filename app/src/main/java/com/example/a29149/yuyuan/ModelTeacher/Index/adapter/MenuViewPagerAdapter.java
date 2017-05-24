package com.example.a29149.yuyuan.ModelTeacher.Index.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a29149.yuyuan.ModelTeacher.Index.course.OwnerCourseTeacherFragment;
import com.example.a29149.yuyuan.ModelTeacher.Index.reward.OwnerRewardTeacherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyao on 2017/5/14.
 * 老师主界面的课程和悬赏viewPager的适配器
 */

public class MenuViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();

    public MenuViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.clear();
        fragmentList.add(new OwnerCourseTeacherFragment());
        fragmentList.add(new OwnerRewardTeacherFragment());
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
