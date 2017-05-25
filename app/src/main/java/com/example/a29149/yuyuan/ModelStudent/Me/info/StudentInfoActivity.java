package com.example.a29149.yuyuan.ModelStudent.Me.info;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;

/**
 * 显示学生信息的Activity
 * Created by geyao on 2017/5/25.
 */

public class StudentInfoActivity extends Activity {

    @ViewInject(R.id.head)
    private ImageView mStudentPhoto;
    @ViewInject(R.id.student_name)
    private TextView mStudentName;
    @ViewInject(R.id.slogan)
    private TextView mSlogan;

}
