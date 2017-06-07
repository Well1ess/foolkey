package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeCourseController;
import com.example.a29149.yuyuan.controller.course.judge.JudgeTeacherController;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/13.
 * 评价课程订单
 */

public class CommentCourseActivity extends Activity implements View.OnClickListener {


    private RadioButton mPublish;//发布评价
    private RatingBar mCourseScore;//课程分数
    private EditText mCourseContent;//课程内容评价
    private RatingBar mTeacherScore;//课程分数
    private String scoreCourse;//保存评价课程分数
    private String commentContent;//保存评价课程内容
    private String scoreTeacher;//保存评价老师分数
    private int position;//记录评论位置
    private String topic;
    private String teacherName;
    private String description;
    private String price;
    private TextView mTopic;//课程标题
    private TextView mTeacherName;//课程老师名
    private TextView mDescription;//发布评价
    private TextView mCoursePrice;//课程价格
    private String teacherUserName;//老师的用户名，用来显示头像
    private RequestManager glide;

    private ImageButton mReturnButton;//返回按钮
    private ImageView mTeacherPhoto;//老师头像



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_course);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        topic = intent.getStringExtra("Topic");
        teacherName = intent.getStringExtra("TeacherName");
        description = intent.getStringExtra("Description");
        price = intent.getStringExtra("CoursePrice");
        teacherUserName = intent.getStringExtra("teacherUserName");
        Log.i("malei",position+"=position"+topic+"=topic"+teacherName+"=teacherName"+description+"=description"+price+"=price");

        initView();


    }

    private void initView() {
        mCourseScore = (RatingBar) findViewById(R.id.course_access);
        mCourseContent = (EditText) findViewById(R.id.ed_comment_content);
        mTeacherScore = (RatingBar) findViewById(R.id.course_teacher);
        mTopic = (TextView) findViewById(R.id.tv_title);
        mTopic.setText(topic);
        mTeacherName = (TextView) findViewById(R.id.tv_teacherName);
        mTeacherName.setText(teacherName);
        mDescription = (TextView) findViewById(R.id.tv_description);
        mDescription.setText(description);
        mCoursePrice = (TextView) findViewById(R.id.tv_price);
        mCoursePrice.setText("￥ "+price);

        mPublish = (RadioButton) findViewById(R.id.rb_main_menu_discovery);
        mPublish.setOnClickListener(this);
        //返回按键
        mReturnButton.findViewById(R.id.ib_return);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //老师头像
        mTeacherPhoto.findViewById(R.id.teacher_photo);
        Glide.with(this);
        //浅出效果，不然会有黄色一闪而过
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        mTeacherPhoto.setAnimation(alphaAnimation);
        alphaAnimation.start();
        mTeacherPhoto.setVisibility(View.VISIBLE);
        //用glide动态地加载图片
        glide.load(PictureInfoBO.getOnlinePhoto(teacherUserName ) )
                .transform(new GlideCircleTransform(this))
                .crossFade(3000)
                .into(mTeacherPhoto);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.rb_main_menu_discovery:
                publishCommentReward();
                Toast.makeText(this, "评论课程", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    private void publishCommentReward() {
        float courseScore = mCourseScore.getRating();
        float teacherScore = mTeacherScore.getRating();
        if (TextUtils.isEmpty(mCourseContent.getText().toString()))
            //非空判断
            Toast.makeText(this, "请输入评价内容", Toast.LENGTH_SHORT).show();
        else
        {
            scoreCourse = courseScore +"";
            commentContent = mCourseContent.getText().toString();
            scoreTeacher = teacherScore+"";
            new CommentCourseAction().execute();
            new CommentRewardAction().execute();
        }

    }

    /**
     * 提交评论课程订单Action
     */
    public class CommentCourseAction extends AsyncTask<String, Integer, String> {


        public CommentCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println();
            System.out.println(this.getClass() + "这里的图片路径依然是写死的！\n");

            return JudgeCourseController.execute(
                    GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId() + "",
                    scoreCourse,
                    commentContent,
                    "",
                    "",
                    "",
                    ""
            );

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {
                        Toast.makeText(CommentCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 提交评论悬赏订单Action
     */
    public class CommentRewardAction extends AsyncTask<String, Integer, String> {


        public CommentRewardAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return JudgeTeacherController.execute(
                    GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId() + "",
                    scoreTeacher
            );


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {
                        Toast.makeText(CommentCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
