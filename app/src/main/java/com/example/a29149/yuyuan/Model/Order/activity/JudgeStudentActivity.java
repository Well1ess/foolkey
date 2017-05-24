package com.example.a29149.yuyuan.Model.Order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeStudentController;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * Created by geyao on 2017/5/24.
 */

public class JudgeStudentActivity extends Activity {

    @ViewInject(R.id.student_photo)
    private ImageView studentPhoto;

    @ViewInject(R.id.student_name)
    private TextView studentName;

    @ViewInject(R.id.course_name)
    private TextView courseName;

    @ViewInject(R.id.student_score)
    private RatingBar studentScore;


    private JudgeStudentController judgeStudentController;


    //打分的分数
    private Float score = null;
    private String orderId;

    @ViewInject(R.id.publish_button)
    private RadioButton radioButton;

    private RequestManager glide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_student);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);


        Intent intent = getIntent();
        //取展示的信息
        String studentNameStr = intent.getStringExtra("studentName");
        String courseNameStr = intent.getStringExtra("courseName");
        studentName.setText( studentNameStr );
        courseName.setText( courseNameStr );

        //图片展示
        String studentUserName = intent.getStringExtra( "studentUserName" );
        glide = Glide.with(this);
        //浅出效果，不然会有黄色一闪而过
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        studentPhoto.setAnimation(alphaAnimation);
        alphaAnimation.start();
        studentPhoto.setVisibility(View.VISIBLE);
        //用glide动态地加载图片
        glide.load(PictureInfoBO.getOnlinePhoto(studentUserName))
                .transform(new GlideCircleTransform(this))
                .crossFade(2000)
                .into(studentPhoto);

        //拿传输的信息
        orderId = intent.getStringExtra("orderId");

        studentScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });
    }



    @OnClick(R.id.publish_button)
    public void publishEvaluation(View view){
        //是否评价

        if ( score == null){
            Toast.makeText(JudgeStudentActivity.this, "点击星星进行评价", Toast.LENGTH_SHORT).show();
        }else {
            JudgeStudentAction judgeStudentAction = new JudgeStudentAction();
            judgeStudentAction.execute();
        }
    }


    /**
     * 发布评价的action
     */
    public class JudgeStudentAction extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            judgeStudentController = new JudgeStudentController();
            judgeStudentController.setOrderId( Long.parseLong( orderId ) );
            judgeStudentController.setScore( score + 0.0 );
            return judgeStudentController.execute();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch ( judgeStudentController.getResult() ) {
                case "success":{ // 评价成功
                    finish();
                }break;
                case "fail":{ // 评价失败
                    Toast.makeText(JudgeStudentActivity.this, "评价失败，请再试一次", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
