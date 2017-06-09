package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.StringUtil;
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

    private OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO; //要评价的订单


    @ViewInject(R.id.iv_photo)
    private ImageView photo; //照片


    @ViewInject(R.id.tv_nickedName)
    private TextView mNickedName; //昵称

    @ViewInject(R.id.tv_title)
    private TextView mCourseName; //课程的名字

    @ViewInject(R.id.rb_student_score)
    private RatingBar studentScore; //打分条

    @ViewInject(R.id.tv_order_price)
    private TextView mOrderPrice; //订单金额

    @ViewInject(R.id.rb_publish)
    private RadioButton radioButton; //发布

    @ViewInject(R.id.ib_return)
    private ImageButton back; //返回键，暂时没用到

    private RequestManager glide; // 图片加载器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //绑定UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_teacher);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mRewardScore = (TextView) findViewById(R.id.ed_score);
        mPublish = (TextView) findViewById(R.id.rb_publish);
        mPublish.setOnClickListener(this);
        //获取信息
        orderBuyCourseAsStudentDTO = (OrderBuyCourseAsStudentDTO) getIntent().getSerializableExtra("DTO");

        String teacherName = orderBuyCourseAsStudentDTO.getStudentDTO().getNickedName();
        String courseName = orderBuyCourseAsStudentDTO.getCourse().getTopic();
        String orderPrice = orderBuyCourseAsStudentDTO.getOrderDTO().getAmount() + "";
        //加载图片
        //FIXME 图片加载失败
        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto(orderBuyCourseAsStudentDTO.getStudentDTO().getUserName()) )
                .placeholder(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
                .into(photo);
        //文字信息
        if (orderPrice == null || orderPrice.equals("")||orderPrice.equals("0"))
            mOrderPrice.setText("免费");
        else
            mOrderPrice.setText( orderPrice + "  " + Const.PRICE_NAME);
        mCourseName.setText(StringUtil.subString(courseName, 15));
        mNickedName.setText( StringUtil.subString( teacherName, 15) );
        //打分监听器
        studentScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });

    }

    //点击监听器
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.rb_publish:
                publishCommentReward();
                break;
            case R.id.ib_return:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * 发布悬赏
     *
     */
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
    private class CommentRewardAction extends AsyncTask<String, Integer, String> {

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
                    orderBuyCourseAsStudentDTO.getOrderDTO().getId() + "",
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
                        //TODO 这里应当是通知adapter刷新数据
//                        GlobalUtil.getInstance().setFragmentFresh(true);
//                        Intent intent = new Intent(CommentRewardActivity.this, MainStudentActivity.class);
//                        startActivity(intent);
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
