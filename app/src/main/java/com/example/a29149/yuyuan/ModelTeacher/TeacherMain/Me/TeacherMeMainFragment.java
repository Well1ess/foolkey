package com.example.a29149.yuyuan.ModelTeacher.TeacherMain.Me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;

@Deprecated
public class TeacherMeMainFragment extends Fragment {

    public TeacherMeMainFragment() {

    }

    public static TeacherMeMainFragment newInstance() {
        TeacherMeMainFragment fragment = new TeacherMeMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_me_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        return view;
    }
}
