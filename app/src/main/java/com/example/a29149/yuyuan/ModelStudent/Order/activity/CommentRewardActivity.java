package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeTeacherController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/13.
 * 评价悬赏订单
 * 目前就是评价老师而已
 */

public class CommentRewardActivity extends AbstractActivity implements View.OnClickListener {

    private TextView mPublish;//发布评价
    private TextView mRewardScore;//订单分数
    private Float score;//保存评价分数
    private String orderId;//保存评价订单ID
    private int position;//记录评论位置


    @ViewInject(R.id.student_photo)
    private ImageView teacherPhoto;
    private String teacherUserName;

    @ViewInject(R.id.tv_student_name)
    private TextView mTeacherName;

    @ViewInject(R.id.tv_course_name)
    private TextView mCourseName;

    @ViewInject(R.id.rb_student_score)
    private RatingBar studentScore;

    @ViewInject(R.id.tv_order_price)
    private TextView mOrderPrice;

    @ViewInject(R.id.rb_publish)
    private RadioButton radioButton;

    private RequestManager glide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_teacher);
        Intent intent = getIntent();
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        position = intent.getIntExtra("position",-1);
        orderId = intent.getStringExtra("orderId");

        initView();
    }

    private void initView() {
        mRewardScore = (TextView) findViewById(R.id.ed_score);
        mPublish = (TextView) findViewById(R.id.rb_publish);
        mPublish.setOnClickListener(this);

        teacherUserName = getIntent().getStringExtra("teacherUserName");
        String teacherName = getIntent().getStringExtra("teacherName");
        String courseName = getIntent().getStringExtra("courseName");
        String orderPrice = getIntent().getStringExtra("orderPrice");

        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto(teacherName) )
                .placeholder(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
                .into(teacherPhoto);

        if (orderPrice == null || orderPrice.equals("")||orderPrice.equals("0"))
            mOrderPrice.setText("免费");
        else
            mOrderPrice.setText( orderPrice + "  " + Const.PRICE_NAME);
        mCourseName.setText( courseName );
        mTeacherName.setText( teacherName );

        studentScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.rb_publish:
                publishCommentReward();
                break;
            default:
                break;
        }

    }

    private void publishCommentReward() {
        if ( score == null )
            Toast.makeText(this, "点击星星进行评价哦", Toast.LENGTH_SHORT).show();
        else
        {

            new CommentRewardAction(score + "").execute();
        }

    }

    /**
     * 提交评论悬赏订单Action
     */
    public class CommentRewardAction extends AsyncTask<String, Integer, String> {

        String score;

        public CommentRewardAction(String score) {
            super();
            this.score = score;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i("malei", GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().toString());
            return JudgeTeacherController.execute(
                    //GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId() + "",
                    orderId,
                    score
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
                        Toast.makeText(CommentRewardActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                        //跳转到主页面
                        GlobalUtil.getInstance().setFragmentFresh(true);
                        Intent intent = new Intent(CommentRewardActivity.this, MainStudentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentRewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentRewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
