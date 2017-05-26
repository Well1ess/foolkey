package com.example.a29149.yuyuan.ModelStudent.Me.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.text.SimpleDateFormat;

/**
 * 显示学生信息的Activity
 * Created by geyao on 2017/5/25.
 */

public class StudentInfoActivity extends Activity {

    private StudentDTO mStudentDTO;//学生信息

    @ViewInject(R.id.head)
    private ImageView mStudentPhoto;

    @ViewInject(R.id.student_name)
    private TextView mStudentName;

    @ViewInject(R.id.slogan)
    private TextView mSlogan;

    @ViewInject(R.id.tv_teacherSex)
    private TextView mTeacherSex;

    @ViewInject(R.id.tv_teacherOriganization)
    private TextView mTeacherOriganization;
    //声望
    @ViewInject(R.id.reputation)
    private TextView mReputation;
    //描述
    @ViewInject(R.id.description)
    private TextView mDescription;
    //上课次数
    @ViewInject(R.id.tv_coursenum)
    private TextView mCoursenum;
    //上课时间
    @ViewInject(R.id.tv_teachingtime)
    private TextView mTeachertime;
    //是否认证
    @ViewInject(R.id.cb_teacherstate)
    private CheckBox mTeacherstate;
    @ViewInject(R.id.student_role)
    private TextView mTeacherRole;
    //评价
    @ViewInject(R.id.tv_evaluatescore)
    private TextView mEvaluatescore;
    //github
    @ViewInject(R.id.tv_github)
    private TextView mGithub;
    //博客
    @ViewInject(R.id.tv_teacherindex)
    private TextView mTeacherindex;
    //Email
    @ViewInject(R.id.tv_email)
    private TextView mEmail;
    @ViewInject(R.id.bt_return)
    private ImageButton mReturn;


    private RequestManager glide;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        int position = getIntent().getIntExtra("position", 0);
        mStudentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();

        initData();


    }

    private void initData() {


        mStudentName.setText(mStudentDTO.getNickedName());

        mSlogan.setText(mStudentDTO.getSlogan());

        mTeacherSex.setText(mStudentDTO.getSexTagEnum().toString());

        mTeacherOriganization.setText(mStudentDTO.getOrganization());
        //声望
        mReputation.setText(mStudentDTO.getPrestige() + "");
        //描述
        mDescription.setText(mStudentDTO.getDescription());
        //上课次数
        mCoursenum.setText(mStudentDTO.getLearningNumber() + "");
        //上课时间
        mTeachertime.setText(mStudentDTO.getLearningTime() + "");
        //是否认证
        if (mStudentDTO.getRoleEnum().equals(RoleEnum.teacher)) {

        }
        else {
            mTeacherstate.setVisibility(View.INVISIBLE);
            switch (mStudentDTO.getRoleEnum()){
                case student:mTeacherRole.setText("学生");break;
                case alreadyApplied:mTeacherRole.setText("认证中");break;
                default:mTeacherRole.setText("黑客！");
            }
        }
        //评价
        mEvaluatescore.setText(mStudentDTO.getStudentAverageScore() + "");
        //github
        mGithub.setText(mStudentDTO.getGithubUrl());
        //博客
        mTeacherindex.setText(mStudentDTO.getBlogUrl());
        //Email
        mEmail.setText(mStudentDTO.getEmail());




        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()))
                .placeholder(R.drawable.photo_placeholder1)
                .transform( new GlideCircleTransform(this) )
                .into(mStudentPhoto);

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(StudentInfoActivity.this, LoginActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }




}
