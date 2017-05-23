package com.example.a29149.yuyuan.ModelTeacher.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.CourseDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.ModelTeacher.Order.activity.CommentCourseActivity;
import com.example.a29149.yuyuan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 课程而未评论listView的Adapter
 */

public class MyListViewNoConmmentClassAdapter extends BaseAdapter implements OnClickListener{
    private Context mContext;

    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mBuyTime;//购买时长
    private TextView mCourseCost;//课程价格
    private TextView mComment;//评价订单
    private List<OrderBuyCourseAsStudentDTO> courseNoCommentList;//完成课程但还未评价订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private int position; //记录位置
    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表
    private CourseAbstract courseDTO = null ;

    public MyListViewNoConmmentClassAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> courseNoCommentList) {
        this.courseNoCommentList = courseNoCommentList;
    }

    @Override
    public int getCount() {
        return courseNoCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseNoCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        view=View.inflate(mContext, R.layout.listview_item_nocomment_course,null);
        this.position = position;
        initView(view);
        mOrderBuyCourseAsStudentDTO = courseNoCommentList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();

        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();


        mTeacherNameAndCourseName.setText(mStudentDTO.getNickedName()+":"+courseDTO.getTopic());
        mBuyTime.setText("购买时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        mCourseCost.setText("课程价格：" + courseDTO.getPrice().toString());
        return view;
    }

    private void initView(View view) {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mBuyTime = (TextView) view.findViewById(R.id.tv_buyTime);
        mCourseCost = (TextView) view.findViewById(R.id.tv_courseCost);
        mComment = (TextView) view.findViewById(R.id.tv_comment);
        mComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_comment:
                commentCourse();
        }
    }

    private void commentCourse() {
        //跳转到课程订单评价
        Intent intent = new Intent(mContext, CommentCourseActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("Topic",courseDTO.getTopic());
        intent.putExtra("TeacherName",mStudentDTO.getNickedName());
        intent.putExtra("Description",courseDTO.getDescription());
        intent.putExtra("CoursePrice",courseDTO.getPrice());

        mContext.startActivity(intent);
    }
}
