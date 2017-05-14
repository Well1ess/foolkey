package com.example.a29149.yuyuan.TeacherMain.Score.MoreStudent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
/**
 * 测试，已废弃
 * */
public class MoreStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_student);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
    }

    @OnClick(R.id.bt_return)
    public void setReturn(View view)
    {
        this.onBackPressed();
    }
}
