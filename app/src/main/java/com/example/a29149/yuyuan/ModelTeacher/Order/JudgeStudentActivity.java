package com.example.a29149.yuyuan.ModelTeacher.Order;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.CourseTypeEnum;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.judge.JudgeStudentController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * Created by geyao on 2017/5/24.
 * 学生评价老师
 */
//TODO 界面什么的还没改
public class JudgeStudentActivity extends AbstractActivity {

    @ViewInject(R.id.iv_photo)
    private ImageView studentPhoto;

    @ViewInject(R.id.tv_nickedName)
    private TextView studentName;

    @ViewInject(R.id.tv_course_name)
    private TextView courseName;

    @ViewInject(R.id.rb_student_score)
    private RatingBar studentScore;

    @ViewInject(R.id.tv_order_price)
    private TextView mOrderPrice;

    private OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO; //要评价的订单

    private JudgeStudentController judgeStudentController;


    //打分的分数
    private Float score = null;
    private String orderId;

    @ViewInject(R.id.rb_publish)
    private RadioButton radioButton;

    private RequestManager glide;
    private String position;//记录评论位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_student);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);


        Intent intent = getIntent();
        orderBuyCourseAsStudentDTO = (OrderBuyCourseAsStudentDTO) intent.getSerializableExtra("DTO");
        //取展示的信息
        String studentNameStr = orderBuyCourseAsStudentDTO.getStudentDTO().getNickedName();
        String courseNameStr = orderBuyCourseAsStudentDTO.getCourse().getTopic();
        String orderPrice = orderBuyCourseAsStudentDTO.getOrderDTO().getAmount() + "";
        String currency;
        if (orderBuyCourseAsStudentDTO.getOrderDTO().getCourseTypeEnum().compareTo(CourseTypeEnum.老师课程) == 0){
            currency = "元";
        }else {
            currency = Const.PRICE_NAME;
        }
        if (orderPrice.equals("") || orderPrice.equals("0")
                ){
            mOrderPrice.setText("免费");
        }else {
            mOrderPrice.setText(orderPrice + "  " + currency);
        }
        studentName.setText(StringUtil.subString(studentNameStr, 10) );
        courseName.setText( StringUtil.subString(courseNameStr, 10) );

        //图片展示
        String studentUserName = orderBuyCourseAsStudentDTO.getStudentDTO().getUserName();
        glide = Glide.with(this);
        //用glide动态地加载图片
        glide.load(PictureInfoBO.getOnlinePhoto(studentUserName))
                .transform(new GlideCircleTransform(this))
                .error(R.drawable.photo_placeholder1)
                .into(studentPhoto);

        //拿传输的信息
        orderId = orderBuyCourseAsStudentDTO.getOrderDTO().getId() + "";

        studentScore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });
    }



    @OnClick(R.id.rb_publish)
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
                    GlobalUtil.getInstance().setFragmentFresh(true);
                    finish();
                }break;
                case "fail":{ // 评价失败
                    Toast.makeText(JudgeStudentActivity.this, "评价失败，请再试一次", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
