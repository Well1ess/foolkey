package com.example.a29149.yuyuan.AccessOrder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeCourseController;
import com.example.a29149.yuyuan.controller.course.judge.JudgeTeacherController;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

public class AccessOrderActivity extends AbstractAppCompatActivity {

    //UI相关
    @ViewInject(R.id.iv_photo)
    private ImageView mTeacherPhoto;
    @ViewInject((R.id.tv_course_title))
    private TextView mCourseTopic;
    @ViewInject(R.id.tv_teacher_name)
    private TextView mTeacherName;
    @ViewInject((R.id.tv_course_description))
    private TextView mCourseDescription;
    @ViewInject(R.id.rb_course_access)
    private RatingBar mCourseAccess;
    @ViewInject(R.id.et_evaluation_course_content)
    private EditText mEvaluationContent;
    @ViewInject(R.id.rb_teacher_access)
    private RatingBar teacherAccess;
    private RequestManager glide;

    //提交给后台
    private Long orderId;
    private Float teacherScore = null;
    private Float courseScore = null;
    private String evaluationContent = "";

    //华哥的跳跳跳动画
    //等待提示，华哥的跳跳跳动画
    public ShapeLoadingDialog shapeLoadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_order);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        //可以修改软键盘位置，也可以在AndroidManifest.xml里面配置
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initView();

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.getDialog().setCancelable(false);

        mCourseAccess.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                courseScore =  rating;
                log.d(this, rating +"");
            }
        });

        teacherAccess.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rate, boolean b) {
                teacherScore = rate;
            }
        });

    }

    /**
     * 填充页面数据
     */
    private void initView(){
        Intent intent = getIntent();
        String courseTopic = intent.getStringExtra("courseTopic");
        String teacherName = intent.getStringExtra("teacherName");
        String courseDescription = intent.getStringExtra("courseDescription");

        mCourseTopic.setText(courseTopic);
        mTeacherName.setText( teacherName );
        mCourseDescription.setText( courseDescription );

        String teacherUserName = intent.getStringExtra("teacherUserName");

        //填充图片
        Glide.with(this);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        mTeacherPhoto.setAnimation(alphaAnimation);
        alphaAnimation.start();
        mTeacherPhoto.setVisibility(View.VISIBLE);
        //用glide动态地加载图片
        glide.load(PictureInfoBO.getOnlinePhoto(teacherUserName))
                .transform(new GlideCircleTransform(this))
                .crossFade(2000)
                .into(mTeacherPhoto);
    }


    /**
     * 发布按钮
     * 先提交老师的，再提交课程的
     * @param view
     */
    public void submit(View view){
        if ( courseScore == null || teacherAccess == null ){
            Toast.makeText(this, "点击小星星进行打分", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //提交
            if ( mEvaluationContent.getText() == null || mEvaluationContent.getText().toString().equals("")){
                evaluationContent = "好评";
            }else
                evaluationContent = mEvaluationContent.getText().toString();

            shapeLoadingDialog.show();
            JudgeTeacherAction judgeTeacherAction = new JudgeTeacherAction();
            judgeTeacherAction.execute();
        }
    }


    /**
     * 对老师打分，成功会调用对课程打分
     */
    public class JudgeTeacherAction extends AsyncTask<String, String, String>{

        public JudgeTeacherController teacherController;

        @Override
        protected String doInBackground(String... strings) {
            teacherController = new JudgeTeacherController();
            return teacherController.execute(
                    orderId + "",
                    teacherScore + ""
            );
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ( s != null){
                try {
                    JSONObject jsonObject = new JSONObject( s );
                    if (jsonObject.getString("result").equals("success")){
                        JudgeCourseAction judgeCourseAction = new JudgeCourseAction();
                        judgeCourseAction.execute();
                        return;
                    }else {
                        Toast.makeText(AccessOrderActivity.this, "服务器暂时出错，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AccessOrderActivity.this, "参数异常，请检查", Toast.LENGTH_SHORT).show();
                }


            }else {
                Toast.makeText(AccessOrderActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
            shapeLoadingDialog.dismiss();
        }
    }


    /**
     * 对课程进行评价
     */
    public class JudgeCourseAction extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            return JudgeCourseController.execute(
                    orderId + "",
                    courseScore + "",
                    evaluationContent + "",
                    "",
                    "",
                    "",
                    ""
            );
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ( s != null){
                try {
                    JSONObject jsonObject = new JSONObject( s );
                    if (jsonObject.getString("result").equals("success")){
                        shapeLoadingDialog.dismiss();
                        Toast.makeText(AccessOrderActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                        AccessOrderActivity.this.finish();

                    }else {
                        Toast.makeText(AccessOrderActivity.this, "服务器暂时出错，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AccessOrderActivity.this, "参数异常，请检查", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(AccessOrderActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
            shapeLoadingDialog.dismiss();
        }
    }

}
