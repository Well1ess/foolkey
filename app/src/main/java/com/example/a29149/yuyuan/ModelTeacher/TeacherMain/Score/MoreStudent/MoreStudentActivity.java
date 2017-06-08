package com.example.a29149.yuyuan.ModelTeacher.TeacherMain.Score.MoreStudent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.ModelTeacher.TeacherMain.Score.Adapter.StudentListAdapter;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试，已废弃
 * */
@Deprecated
public class MoreStudentActivity extends AbstractAppCompatActivity {

    @ViewInject(R.id.student_list)
    private RecyclerView mStudentList;

    private StudentListAdapter mStudentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_student);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        List<String> strings = new ArrayList<>();

        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");

        mStudentListAdapter = new StudentListAdapter(strings, this);

        //开始上课的回调函数，可以在onStartCourse里面添加任意想要的参数，然后特异处理
        mStudentListAdapter.setStartCourse(new StudentListAdapter.StartCourse() {
            @Override
            public void onStartCourse() {
                Toast.makeText(MoreStudentActivity.this, "开始上课", Toast.LENGTH_SHORT).show();
            }
        });

        //结束上课的回调函数，可以在onStartCourse里面添加任意想要的参数，然后特异处理
        mStudentListAdapter.setEndCourse(new StudentListAdapter.EndCourse() {
            @Override
            public void onEndCourse() {
                Toast.makeText(MoreStudentActivity.this, "结束上课", Toast.LENGTH_SHORT).show();

            }
        });

        mStudentList.setLayoutManager(new GridLayoutManager(this, 4));
        mStudentList.setAdapter(mStudentListAdapter);

    }

    @OnClick(R.id.ib_return)
    public void setReturn(View view)
    {
        this.onBackPressed();
    }


}
