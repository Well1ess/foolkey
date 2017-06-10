package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.R;

public class TeacherSearchFragment extends AbstractFragment {

    public TeacherSearchFragment() {
        super();
    }

    public static TeacherSearchFragment newInstance() {
        TeacherSearchFragment fragment = new TeacherSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_search, container, false);


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

}
