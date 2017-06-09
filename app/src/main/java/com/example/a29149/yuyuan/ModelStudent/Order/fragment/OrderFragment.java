package com.example.a29149.yuyuan.ModelStudent.Order.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyFragmentPagerAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.AbstractObject.AbstracFragment;


/**
 * Created by MaLei on 2017/5/2.
 * Email:ml1995@mail.ustc.edu.cn
 * 订单Fragment
 */

public class OrderFragment extends AbstracFragment implements View.OnClickListener {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_order,null);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();
        initData();
        return view;
    }


    private void initView() {
        tabLayout = (TabLayout) view.findViewById(R.id.id_tl);
        viewPager = (ViewPager) view.findViewById(R.id.id_vp);
        View keySearch = view.findViewById(R.id.et_search);
        keySearch.setOnClickListener(this);
    }

    private void initData() {
        //添加了4个fragment
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        //设置tablayout按照标签数目平分
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), v, "searchView").toBundle());
    }

    /**
     * 临时的方法
     * 获取子fragment
     * @param position
     * @return
     */
    private Fragment getFragment(int position){
        return myFragmentPagerAdapter.getItem(position);
    }

    /**
     * 获取未评价的fragment
     * @return
     */
    public NoCommentFragment getNoCommentFragment(){
        return (NoCommentFragment) getFragment(2);
    }
}
