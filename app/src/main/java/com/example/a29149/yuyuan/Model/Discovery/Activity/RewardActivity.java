package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseStudentDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏详情
 */

public class RewardActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mOrder;//我要接单
    private int position = -1;//item位置
    private StudentDTO studentDTO;//发布悬赏的学生信息
    private CourseStudentDTO courseStudentDTO;//悬赏信息
    private TextView mRewardUser;//发布悬赏人的信息
    private TextView mTeacherEvaluate;//悬赏价格
    private TextView mRewardDescription;//悬赏描述
    private TextView mCreateTime;//创建悬赏的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        position = extras.getInt("position");
        Log.i("malei",position+"");
        if(position != -1)
        {
            studentDTO = GlobalUtil.getInstance().getCourseStudentPopularDTOs().get(position).getStudentDTO();
            courseStudentDTO = GlobalUtil.getInstance().getCourseStudentPopularDTOs().get(position).getCourseStudentDTO();

        }
        else
        {
            studentDTO = new StudentDTO();
            courseStudentDTO = new CourseStudentDTO();
        }
        initView();
        initData();
    }


    private void initView() {
        mOrder = (RadioButton) findViewById(R.id.order);
        mOrder.setOnClickListener(this);

        mRewardUser = (TextView) findViewById(R.id.courseEvaluate);
        mTeacherEvaluate =(TextView) findViewById(R.id.teacherEvaluate);
        mRewardDescription = (TextView) findViewById(R.id.tv_description);
        mCreateTime = (TextView) findViewById(R.id.tv_createTime);
    }


    private void initData() {
        mRewardUser.setText(studentDTO.getNickedName());
        Log.i("malei",studentDTO.getNickedName());
        mTeacherEvaluate.setText(courseStudentDTO.getPrice()+"");
        mRewardDescription.setText(courseStudentDTO.getDescription());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.order:
                Intent intent = new Intent(RewardActivity.this, ApplyRewardTeacherActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

}
