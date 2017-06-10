package com.example.a29149.yuyuan.ModelStudent.Order.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MaLei on 2017/5/2.
 * Email:ml1995@mail.ustc.edu.cn
 * 订单Fragment
 */

public class OrderFragment extends AbstractFragment implements View.OnClickListener {

    //持有子Fragment的引用
    private List<AbstractFragment> childFragmentList;
    //未付款订单
    private NoPayFragment noPayFragment;
    //未评论订单
    private NoCommentFragment noCommentFragment;
    //未上课订单
    private NoClassFragment noClassFragment;
    //已完成订单
    private FinishOrderFragment finishOrderFragment;

    //无参构造器
    public OrderFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定UI
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //给各个子Fragment赋值
        noPayFragment = new NoPayFragment();
        noCommentFragment = new NoCommentFragment();
        noClassFragment = new NoClassFragment();
        finishOrderFragment = new FinishOrderFragment();

        //给list赋值
        childFragmentList = new ArrayList<>();

        //按序添加，此顺序将会影响实际的顺序
        childFragmentList.add(noPayFragment);
        childFragmentList.add(noClassFragment);
        childFragmentList.add(noCommentFragment);
        childFragmentList.add(finishOrderFragment);

        //子Fragment设置当前Fragment
        noPayFragment.setFragment(R.id.fl_container, this, childFragmentList);
        noClassFragment.setFragment(R.id.fl_container, this, childFragmentList);
        noCommentFragment.setFragment(R.id.fl_container, this, childFragmentList);
        finishOrderFragment.setFragment(R.id.fl_container, this, childFragmentList);

        //设置切换的Fragment
        switchFragment(R.id.fl_container, noClassFragment);
        switchFragment(R.id.fl_container, noCommentFragment);
        switchFragment(R.id.fl_container, finishOrderFragment);
        switchFragment(R.id.fl_container, noPayFragment);

        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);


        //搜索
        View keySearch = view.findViewById(R.id.et_search);
        keySearch.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), v, "searchView").toBundle());
    }


    /**
     * 获取未评价的fragment
     * @return
     */
    public NoCommentFragment getNoCommentFragment(){
        return noCommentFragment;
    }
}
