package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a29149.yuyuan.DTO.CourseStudentDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏详情
 */

public class RewardActivity extends AppCompatActivity implements View.OnClickListener {

    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;
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

        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        displayInfo = new WarningDisplayDialog.Builder(this);
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("接      单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO:网络传输
            }
        });
        displayInfo.create();
    }

    //为底部菜单添加监听
    @OnClick({R.id.want_learn, R.id.chart, R.id.order})
    public void setMenuListener(View view)
    {
        switch (view.getId())
        {
            case R.id.want_learn:
                //TODO:网络传输
                break;
            case R.id.chart:
                //TODO:网络传输
                break;
            case R.id.order:
                displayInfo.setMsg("您确定要此单吗？");
                displayInfo.getDialog().dismiss();
                break;
        }
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
