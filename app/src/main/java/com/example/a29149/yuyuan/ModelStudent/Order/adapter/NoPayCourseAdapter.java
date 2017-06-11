package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 课程而未购买listView的Adapter
 */

public class NoPayCourseAdapter extends BaseAdapter implements OnClickListener{
    private Context mContext;

    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mBuyTime;//购买时长
    private TextView mCourseCost;//课程价格
    private TextView mCancel;//取消订单
    private List<OrderBuyCourseAsStudentDTO> courseNoPayList;//课程但还未购买订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private int position; //记录位置

    private CourseAbstract courseDTO = null ;

    private RequestManager glide;

    public NoPayCourseAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> courseNoPayList) {
        this.courseNoPayList = courseNoPayList;
    }

    @Override
    public int getCount() {
        return courseNoPayList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseNoPayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        view=View.inflate(mContext, R.layout.adapter_order,null);
        this.position = position;

        mOrderBuyCourseAsStudentDTO = courseNoPayList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();

        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();


        String fisrtLine = mStudentDTO.getNickedName()+":"+courseDTO.getTopic();
        if (fisrtLine.length() > 9) {
            fisrtLine = fisrtLine.substring(0, 7);
            fisrtLine = fisrtLine + "...";
        }
        mTeacherNameAndCourseName.setText(fisrtLine);
        mBuyTime.setText("购买时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        mCourseCost.setText("课程价格：" + courseDTO.getPrice().toString());
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_photo);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_nickedName);
        mBuyTime = (TextView) view.findViewById(R.id.tv_buyTime);
        mCourseCost = (TextView) view.findViewById(R.id.tv_amount);
        mCancel = (TextView) view.findViewById(R.id.tv_button);

        mCancel.setOnClickListener(this);

        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(mTeacherPhone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_button:
                cancelCourse();
        }
    }

    private void cancelCourse() {
        Toast.makeText(mContext, "取消订单", Toast.LENGTH_SHORT).show();

    }
}
