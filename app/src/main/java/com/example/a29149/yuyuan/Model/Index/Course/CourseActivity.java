package com.example.a29149.yuyuan.Model.Index.Course;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
    }

    @OnClick(R.id.key_value)
    public void setSearchListener(View view) {
        Intent searchActivity = new Intent(this, SearchActivity.class);
        startActivity(searchActivity,
                ActivityOptions.makeSceneTransitionAnimation(this, view, "searchView").toBundle());
    }

    @OnClick(R.id.head)
    public void setHeadListener(View view) {
        Intent toTeacherIndexActivity = new Intent(this, OriginIndexActivity.class);
        startActivity(toTeacherIndexActivity,
                ActivityOptions.makeSceneTransitionAnimation(this, view, "shareHead").toBundle());
    }
}
