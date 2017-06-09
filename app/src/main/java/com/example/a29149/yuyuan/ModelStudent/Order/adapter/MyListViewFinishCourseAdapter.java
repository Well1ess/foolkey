package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 已完成订单的课程listView的Adapter
 */

public class MyListViewFinishCourseAdapter extends BaseAdapter {

    private Context mContext;
    private  View view ;
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mExceptTime;//预计时长
    private TextView mTeacherCharge;//老师收费
    private TextView mStudentRewardState;//学生悬赏状态：是否申请
    private TextView mPay;//学生点击付款按钮
    private List<OrderBuyCourseAsStudentDTO> courseFinishList;//已完成的悬赏订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private CourseAbstract courseDTO = null ;

    private RequestManager glide;

    public MyListViewFinishCourseAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> courseFinishList) {
        if (courseFinishList == null) {
            this.courseFinishList = new ArrayList<>();
        }else {
            this.courseFinishList = courseFinishList;
        }
    }

    @Override
    public int getCount() {
        return courseFinishList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseFinishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view=View.inflate(mContext, R.layout.listview_item_finish_reward,null);
        initView();
        mOrderBuyCourseAsStudentDTO = courseFinishList.get(position);

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
        mTeacherNameAndCourseName.setText(mStudentDTO.getNickedName() +" : " + courseDTO.getTopic().toString() + "");
        mExceptTime.setText("购买时长 ：" + mOrderBuyCourseDTO.getNumber().toString()+"h");
        mTeacherCharge.setText("课程价格 : " + mOrderBuyCourseDTO.getAmount()+"" + Const.PRICE_NAME);

        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_photo);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_nickedName);
        mExceptTime = (TextView) view.findViewById(R.id.tv_exceptTime);
        mTeacherCharge = (TextView) view.findViewById(R.id.teacherCharge);

        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()))
                .placeholder(R.drawable.photo_placeholder1)
                .error(R.drawable.photo_placeholder1)
                .transform( new GlideCircleTransform( mContext ))
                .into(mTeacherPhone);
    }


}
