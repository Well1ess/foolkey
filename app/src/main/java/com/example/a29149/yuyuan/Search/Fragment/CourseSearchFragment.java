package com.example.a29149.yuyuan.Search.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.R;

public class CourseSearchFragment extends YYSearchBaseFragment {

    public CourseSearchFragment() {
        super();
    }


    public static CourseSearchFragment newInstance() {
        CourseSearchFragment fragment = new CourseSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_search, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * @param pageNo
     * @param keyValue
     *
     * @Author: geyao
     * @Date: 2017/6/12
     * @Description: 搜索的抽象方法，由SearchActivity调用
     */
    @Override
    public void search(String pageNo, String keyValue) {

    }
}
