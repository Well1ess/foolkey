package com.example.a29149.yuyuan.Model.Order.fragment;

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

import com.example.a29149.yuyuan.Model.Order.adapter.MyFragmentPagerAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;


/**
 * Created by MaLei on 2017/5/2.
 * Email:ml1995@mail.ustc.edu.cn
 * 订单Fragment
 */

public class OrderFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

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
        View keySearch = view.findViewById(R.id.key_value);
        keySearch.setOnClickListener(this);
    }

    private void initData() {
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), view, "searchView").toBundle());
    }
}
