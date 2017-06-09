package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeCourseController;
import com.example.a29149.yuyuan.controller.course.judge.JudgeTeacherController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/13.
 * 评价课程订单
 */

public class StudentJudgeCourseActivity extends AbstractActivity implements View.OnClickListener {


    private RatingBar mCourseScore;//课程分数
    private EditText mCourseContent;//课程内容评价
    private RatingBar mTeacherScore;//课程分数
    private String scoreCourse;//保存评价课程分数
    private String commentContent;//保存评价课程内容
    private String scoreTeacher;//保存评价老师分数

    private RequestManager glide;

    private OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO; //要评价的订单

    private ImageButton mReturnButton;//返回按钮
    private ImageView mTeacherPhoto;//老师头像



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_course);
        Intent intent = getIntent();
        //从意图里获取要展示的dto
        orderBuyCourseAsStudentDTO = (OrderBuyCourseAsStudentDTO) intent.getSerializableExtra("DTO");

        initView();
    }

    private void initView() {
        //绑定UI
        mCourseScore = (RatingBar) findViewById(R.id.course_access);
        mCourseContent = (EditText) findViewById(R.id.ed_comment_content);
        mTeacherScore = (RatingBar) findViewById(R.id.course_teacher);
        //课程标题
        TextView mTopic = (TextView) findViewById(R.id.tv_title);
        //昵称
        TextView mTeacherName = (TextView) findViewById(R.id.tv_nickedName);
        //课程描述
        TextView mDescription = (TextView) findViewById(R.id.tv_description);
        //订单价格
        TextView mCoursePrice = (TextView) findViewById(R.id.tv_price);
        //发布的按钮
        RadioButton mPublish = (RadioButton) findViewById(R.id.rb_main_menu_discovery);
        mReturnButton.findViewById(R.id.ib_return);

        //设置课程标题
        mTopic.setText(StringUtil.subString(orderBuyCourseAsStudentDTO.getCourse().getTopic(), 10));
        //设置价格
        mCoursePrice.setText( "价格" + StringUtil.subString( orderBuyCourseAsStudentDTO.getOrderDTO().getAmount(), 10 ) );
        //昵称
        mTeacherName.setText(StringUtil.subString(orderBuyCourseAsStudentDTO.getStudentDTO().getNickedName(), 10));
        //描述
        mDescription.setText( StringUtil.subString( orderBuyCourseAsStudentDTO.getCourse().getDescription(), 40 ) );


        mPublish.setOnClickListener(this);
        //返回按键
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //老师头像
        mTeacherPhoto.findViewById(R.id.iv_photo);
        Glide.with(this);
        //用glide动态地加载图片
        glide.load(PictureInfoBO.getOnlinePhoto( orderBuyCourseAsStudentDTO.getStudentDTO().getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
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

    /**
     * 发布评价
     * 学生对课程的评价，要同时评价老师与课程
     */
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
            //FIXME 这样提交网络请求方式不好
            new CommentCourseAction().execute();
            new CommentRewardAction().execute();
        }

    }

    /**
     * 提交评价【课程】订单Action
     */
    private class CommentCourseAction extends AsyncTask<String, Integer, String> {


        CommentCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return JudgeCourseController.execute(
                    orderBuyCourseAsStudentDTO.getOrderDTO().getId() + "",
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
                        Toast.makeText(StudentJudgeCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                        //TODO 这里没有刷新adapter
                    }
                } catch (Exception e) {
                    Toast.makeText(StudentJudgeCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StudentJudgeCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 提交评价【老师】订单Action
     */
    private class CommentRewardAction extends AsyncTask<String, Integer, String> {


        CommentRewardAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return JudgeTeacherController.execute(
                    orderBuyCourseAsStudentDTO.getOrderDTO().getId() + "",
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
                        Toast.makeText(StudentJudgeCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                        //TODO 这里没有刷新adapter
                    }
                } catch (Exception e) {
                    Toast.makeText(StudentJudgeCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StudentJudgeCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
