package com.example.a29149.yuyuan.ModelStudent.Index;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.ModelStudent.Index.Fragment.ClassesFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.SlideMenu;

import java.util.ArrayList;
import java.util.List;

public class IndexMainFragment extends AbstractFragment {

    private View view;

    private TextView mCurrentTextView;

    @ViewInject(R.id.slide_menu)
    private SlideMenu mSlideMenu;

    @ViewInject(R.id.java)
    private TextView mSlideClassJava;

    @ViewInject(R.id.c)
    private TextView mSlideClassC;

    @ViewInject(R.id.js)
    private TextView mSlideClassJS;

    @ViewInject(R.id.python)
    private TextView mSlideClassPython;

    @ViewInject(R.id.php)
    private TextView mSlideClassPHP;

    @ViewInject(R.id.h5)
    private TextView mSlideClassHtml5;

    @ViewInject(R.id.android)
    private TextView mSlideClassAndroid;

    @ViewInject(R.id.ios)
    private TextView mSlideClassIOS;

    @ViewInject(R.id.mysql)
    private TextView mSlideClassMySQL;

    @ViewInject(R.id.font)
    private TextView mSlideClassFont;

    @ViewInject(R.id.below)
    private TextView mSlideClassBelow;

    @ViewInject(R.id.other)
    private TextView mSlideClassOther;

    @ViewInject(R.id.vp_content_pager)
    private ViewPager mClassPager;

    private List<ClassesFragment> fragmentList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();

    //暂存选择的课程类型
    private TechnicTagEnum technicTagEnum;

    public IndexMainFragment() {
    }

    public static IndexMainFragment newInstance() {
        IndexMainFragment fragment = new IndexMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // ViewPager+Fragment使用的还是比较频繁的，但是当我打开应用第一次进入时很正常，
        // 而第二次进入的时候却显示的是空白，当时感觉很是迷茫，可是仔细一查，原来是第二次
        // 加载的时候重复调用了onCreateView()这个方法，重新new了一个PageAdapter导致
        // 子fragment不显示，问题的解决方法就是在onCreateView()方法里面加入如下代码
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }

        view = inflater.inflate(R.layout.fragment_index_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);


        //默认选中第一个tab
        mCurrentTextView = mSlideClassJava;
        mSlideClassJava.setTextColor(getResources().getColor(R.color.white));

        //ViewPagerAdapter初始化
        fragmentList.clear();
        for (int i = 0; i < mSlideMenu.getChildCount(); i++) {
            fragmentList.add(ClassesFragment.newInstance(i + 1));
        }

        //默认首先在网络端获取一次数据
        fragmentList.get(0).resetContent(TechnicTagEnum.values()[0]);

        mClassPager.setAdapter(new MenuAdapter(getFragmentManager()));
        mClassPager.setOffscreenPageLimit(12);
        mClassPager.setOnPageChangeListener(new ViewPagerListener());

        //将菜单添加至List便于管理
        textViewList.clear();
        textViewList.add(mSlideClassJava);
        textViewList.add(mSlideClassC);
        textViewList.add(mSlideClassJS);
        textViewList.add(mSlideClassPython);
        textViewList.add(mSlideClassPHP);
        textViewList.add(mSlideClassHtml5);
        textViewList.add(mSlideClassAndroid);
        textViewList.add(mSlideClassIOS);
        textViewList.add(mSlideClassMySQL);
        textViewList.add(mSlideClassFont);
        textViewList.add(mSlideClassBelow);
        textViewList.add(mSlideClassOther);

        try {
            if (textViewList.size() != mSlideMenu.getChildCount())
                throw new IllegalArgumentException("添加菜单异常");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }



        return view;
    }

    @OnClick({R.id.java, R.id.c, R.id.python, R.id.js, R.id.php, R.id.ios,
            R.id.android, R.id.h5, R.id.mysql, R.id.font, R.id.below, R.id.other})
    public void setSlideClassClickListener(View view) {
        TextView textView = (TextView) view;
        if (mCurrentTextView != textView) {
            switch (textView.getId()) {
                case R.id.java:
                    mClassPager.setCurrentItem(0);
                    break;
                case R.id.c:
                    mClassPager.setCurrentItem(1);
                    break;
                case R.id.js:
                    mClassPager.setCurrentItem(2);
                    break;
                case R.id.python:
                    mClassPager.setCurrentItem(3);
                    break;
                case R.id.php:
                    mClassPager.setCurrentItem(4);
                    break;
                case R.id.h5:
                    mClassPager.setCurrentItem(5);
                    break;
                case R.id.android:
                    mClassPager.setCurrentItem(6);
                    break;
                case R.id.ios:
                    mClassPager.setCurrentItem(7);
                    break;
                case R.id.mysql:
                    mClassPager.setCurrentItem(8);
                    break;
                case R.id.font:
                    mClassPager.setCurrentItem(9);
                    break;
                case R.id.below:
                    mClassPager.setCurrentItem(10);
                    break;
                case R.id.other:
                    mClassPager.setCurrentItem(11);
                    break;
            }
        }
    }

    @OnClick(R.id.et_search)
    public void setSearchEdtListener(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), view, "searchView").toBundle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    class MenuAdapter extends FragmentPagerAdapter {

        public MenuAdapter(FragmentManager fm) {
            super(fm);
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

    class ViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int index) {
            if (mCurrentTextView != null)
                mCurrentTextView.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
            mCurrentTextView = textViewList.get(index);
            mCurrentTextView.setTextColor(getResources().getColor(R.color.white));
            mSlideMenu.smoothScrollByChild(index);

            //判断当前在那个View中
            switch (index) {
                case 0:
                    mClassPager.setCurrentItem(0);
                    break;
                case 1:
                    mClassPager.setCurrentItem(1);
                    break;
                case 2:
                    mClassPager.setCurrentItem(2);
                    break;
                case 3:
                    mClassPager.setCurrentItem(3);
                    break;
                case 4:
                    mClassPager.setCurrentItem(4);
                    break;
                case 5:
                    mClassPager.setCurrentItem(5);
                    break;
                case 6:
                    mClassPager.setCurrentItem(6);
                    break;
                case 7:
                    mClassPager.setCurrentItem(7);
                    break;
                case 8:
                    mClassPager.setCurrentItem(8);
                    break;
                case 9:
                    mClassPager.setCurrentItem(9);
                    break;
                case 10:
                    mClassPager.setCurrentItem(10);
                    break;
                case 11:
                    mClassPager.setCurrentItem(11);
                    break;
            }
            technicTagEnum = TechnicTagEnum.values()[index];
            fragmentList.get(index).resetContent(technicTagEnum);
        }
    }
}
