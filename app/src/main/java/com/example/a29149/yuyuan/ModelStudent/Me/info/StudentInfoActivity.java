package com.example.a29149.yuyuan.ModelStudent.Me.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * 显示学生信息的Activity
 * Created by geyao on 2017/5/25.
 */

public class StudentInfoActivity extends AbstractActivity {

    private StudentDTO mStudentDTO;//学生信息

    @ViewInject(R.id.iv_photo)
    private ImageView mStudentPhoto;

    @ViewInject(R.id.tv_nickedName)
    private TextView mStudentName;

    @ViewInject(R.id.tv_slogan)
    private TextView mSlogan;

    @ViewInject(R.id.tv_sex)
    private TextView mTeacherSex;

    @ViewInject(R.id.tv_organization)
    private TextView mTeacherOrganization;
    //声望
    @ViewInject(R.id.tv_prestige)
    private TextView mReputation;
    //描述
    @ViewInject(R.id.tv_description)
    private TextView mDescription;
    //上课次数
    @ViewInject(R.id.tv_teachingNumber)
    private TextView mCoursenum;
    //上课时间
    @ViewInject(R.id.tv_teachingTime)
    private TextView mTeachingTime;
    //是否认证
    @ViewInject(R.id.cb_state)
    private CheckBox mTeacherState;
    @ViewInject(R.id.tv_role)
    private TextView mTeacherRole;
    //评价
    @ViewInject(R.id.tv_teacherAverageScore)
    private TextView mEvaluateScore;
    //github
    @ViewInject(R.id.tv_github)
    private TextView mGithub;
    //博客
    @ViewInject(R.id.tv_blogUrl)
    private TextView mTeacherindex;
    //Email
    @ViewInject(R.id.tv_email)
    private TextView mEmail;
    @ViewInject(R.id.ib_return)
    private ImageButton mReturn;


    private RequestManager glide;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        mStudentDTO = (StudentDTO) getIntent().getSerializableExtra("DTO");

        initData();


    }

    private void initData() {


        mStudentName.setText(mStudentDTO.getNickedName());

        mSlogan.setText(mStudentDTO.getSlogan());

        mTeacherSex.setText(mStudentDTO.getSexTagEnum().toString());

        mTeacherOrganization.setText(mStudentDTO.getOrganization());
        //声望
        mReputation.setText(mStudentDTO.getPrestige() + "");
        //描述
        mDescription.setText(mStudentDTO.getDescription());
        //上课次数
        mCoursenum.setText(mStudentDTO.getLearningNumber() + "");
        //上课时间
        mTeachingTime.setText(mStudentDTO.getLearningTime() + "");
        //是否认证
        if (mStudentDTO.getRoleEnum().equals(RoleEnum.teacher)) {

        }
        else {
            mTeacherState.setVisibility(View.INVISIBLE);
            switch (mStudentDTO.getRoleEnum()){
                case student:mTeacherRole.setText("学生");break;
                case alreadyApplied:mTeacherRole.setText("认证中");break;
                default:mTeacherRole.setText("黑客！");
            }
        }
        //评价
        mEvaluateScore.setText(mStudentDTO.getStudentAverageScore() + "");
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
