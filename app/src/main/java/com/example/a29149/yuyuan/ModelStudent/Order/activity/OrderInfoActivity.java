package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.CourseDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.text.SimpleDateFormat;

/**
 * Created by MaLei on 2017/5/23.
 * Email:ml1995@mail.ustc.edu.cn
 * 订单的详情
 *
 * 这个Activity要复用，所有的学生订单都使用这一张Activity
 *
 * 注意，这里是学生视角
 *
 */

public class OrderInfoActivity extends AbstractActivity implements View.OnClickListener {
    private static final String TAG = "OrderInfoActivity";

    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
//    private List<OrderBuyCourseAsStudentDTO> orderRewardList;//全部悬赏信息
    private CourseAbstract courseDTO = null ;


    @ViewInject(R.id.tv_order_id)
    private TextView mOrderId;//订单编号

    @ViewInject(R.id.tv_courseType)
    private TextView mOrderType;//课程类型

    @ViewInject(R.id.tv_title)
    private TextView mCourseTopic;//课程标题

    @ViewInject(R.id.tv_nickedName)
    private TextView mTeacherName;//老师/学生 姓名

    @ViewInject(R.id.tv_description)
    private TextView mCourseDescription;//课程描述

    @ViewInject(R.id.tv_price)
    private TextView mCoursePrice;//课程价格

    @ViewInject(R.id.tv_createdTime)
    private TextView mCreatedTime;//订单创建时间

    @ViewInject(R.id.tv_number)
    private TextView mOrderNumber;//购买时长

    @ViewInject(R.id.tv_orderStateEnum)
    private TextView mOrderState; // 订单状态

    @ViewInject(R.id.tv_teachMethodEnum)
    private TextView mTeachMethodEnum; //授课方式

    @ViewInject(R.id.tv_payTime)
    private TextView mPayTime; // 支付时间

    @ViewInject(R.id.tv_lessonEndTime)
    private TextView mLessenEndTime; // 下课时间

    @ViewInject(R.id.rb_left)
    private RadioButton mLeftButton;//左边按钮

    @ViewInject(R.id.rb_center)
    private RadioButton mCenterButton; //中间按钮

    @ViewInject(R.id.rb_right)
    private RadioButton mRightButton; //右边按钮

    @ViewInject(R.id.iv_photo)
    private ImageView photo; //头像

    private RequestManager glide;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //绑定UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        //获取意图，从意图中获取DTO
        Intent intent = getIntent();
        mOrderBuyCourseAsStudentDTO = ( OrderBuyCourseAsStudentDTO )intent.getSerializableExtra("DTO");
        //给UI填充数据
        initData();
    }

    /**
     * 填充数据
     */
    private void initData() {
        //老师的studentDTO
        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        //老师的teacherDTO
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        //这里的courseDTO，还不知道它到底是reward还是course
        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();
        //设置订单编号
        mOrderId.setText(mOrderBuyCourseDTO.getCourseId()+"");
        //设置订单类型
        mOrderType.setText(mOrderBuyCourseDTO.getCourseTypeEnum().toString());
        //设置课程标题
        mCourseTopic.setText(StringUtil.subString(courseDTO.getTopic(), 10));
        //设置老师的昵称
        mTeacherName.setText(StringUtil.subString( mStudentDTO.getNickedName(), 10) );
        //设置课程的描述
        mCourseDescription.setText(StringUtil.subString( courseDTO.getDescription(), 20 ));
        //设置课程的价格
        mCoursePrice.setText( courseDTO.getPrice()+"元");
        //设置生成时间
        SimpleDateFormat time=new SimpleDateFormat("yyyy-mm-dd");
        mCreatedTime.setText(mOrderBuyCourseDTO.getCreatedTime().getDataTime());
        //设置购买时长
        mOrderNumber.setText(mOrderBuyCourseDTO.getNumber().toString());
        //设置上课方式
        mTeachMethodEnum.setText( mOrderBuyCourseDTO.getTeachMethodEnum().toString() );
        //设置支付时间
        if (mOrderBuyCourseDTO.getPayTime() != null )
            mPayTime.setText( mOrderBuyCourseDTO.getPayTime().getDataTime() );
        else {
            mPayTime.setText("未付款");
        }
        //设置下课时间
        if (mOrderBuyCourseDTO.getLessonEndTime() != null)
            mLessenEndTime.setText( mOrderBuyCourseDTO.getLessonEndTime().toString() );
        else {
            mLessenEndTime.setText( "未下课" );
        }
        //加载图片
        glide = Glide.with( this );
        glide.load(PictureInfoBO.getOnlinePhoto( mStudentDTO.getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
                .into(photo);

    }

    /**
     * 按钮
     * 设置监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_left:
                Toast.makeText(this,"联系老师",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
