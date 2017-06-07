package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
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
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by MaLei on 2017/5/23.
 * Email:ml1995@mail.ustc.edu.cn
 * 已完成悬赏订单的详情
 */

public class OrderRewardInfoActivity extends Activity implements View.OnClickListener {

    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private List<OrderBuyCourseAsStudentDTO> orderRewardList;//全部悬赏信息
    private CourseAbstract courseDTO = null ;

    @ViewInject(R.id.iv_teacherPhone)
    private ImageView photo2;

    @ViewInject(R.id.order_id)
    private TextView mOrderId;//订单编号

    @ViewInject(R.id.order_type)
    private TextView mOrderType;//订单类型

    @ViewInject(R.id.tv_course_title)
    private TextView mCourseTopic;//课程Topic

    @ViewInject(R.id.tv_teacher_name)
    private TextView mTeacherName;//老师姓名

    @ViewInject(R.id.tv_course_description)
    private TextView mCourseDescription;//课程描述

    @ViewInject(R.id.course_price)
    private TextView mCoursePrice;//课程价格

    @ViewInject(R.id.order_time)
    private TextView mOrderTime;//订单时间

    @ViewInject(R.id.order_number)
    private TextView mOrderNumber;//购买时长

    @ViewInject(R.id.teacher_prestige)
    private TextView mTeacherPrestige;//老师声望

    @ViewInject(R.id.teacher_slogan)
    private TextView mTeacherSlogan;//老师Slogan

    @ViewInject(R.id.not_follow)
    private TextView mFollow;//关注该老师

    @ViewInject(R.id.left)
    private RadioButton mChartWithTeacher;//关注该老师

    @ViewInject(R.id.iv_teacherPhoto)
    private ImageView photo;

    private RequestManager glide;

    //获取位置
    private int position;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        Log.i("malei","position="+position);


        orderRewardList = GlobalUtil.getInstance().getOrderRewardList();
        System.out.println(this.getClass() + "112行" + orderRewardList);
        initData();
    }

    private void initData() {
        mOrderBuyCourseAsStudentDTO = orderRewardList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();

        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();

        mOrderId.setText(mOrderBuyCourseDTO.getCourseId()+"");
        mOrderType.setText(mOrderBuyCourseDTO.getCourseTypeEnum().toString());
        mCourseTopic.setText(courseDTO.getTopic());
        mTeacherName.setText(mStudentDTO.getNickedName());
        mCourseDescription.setText(courseDTO.getDescription());
        mCoursePrice.setText(courseDTO.getPrice()+"元");
        SimpleDateFormat time=new SimpleDateFormat("yyyy-mm-dd");
        mOrderTime.setText(mOrderBuyCourseDTO.getCreatedTime().getDataTime());
        mOrderNumber.setText(mOrderBuyCourseDTO.getNumber().toString());
        mTeacherPrestige.setText("声望值："+mStudentDTO.getPrestige());
        mTeacherSlogan.setText(mStudentDTO.getSlogan());
        mFollow.setOnClickListener(this);
        mChartWithTeacher.setOnClickListener(this);

        glide = Glide.with( this );
        glide.load(PictureInfoBO.getOnlinePhoto( mStudentDTO.getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
                .into(photo);

        glide.load(PictureInfoBO.getOnlinePhoto( mStudentDTO.getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(this))
                .into(photo2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.not_follow:
                Toast.makeText(this,"关注",Toast.LENGTH_SHORT).show();
                break;
            case R.id.left:
                Toast.makeText(this,"联系老师",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
