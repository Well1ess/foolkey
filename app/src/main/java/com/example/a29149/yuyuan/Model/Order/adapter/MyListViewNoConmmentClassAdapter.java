package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.CourseDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Model.Order.global.GlobalValue;
import com.example.a29149.yuyuan.R;

import java.util.List;
import java.util.Map;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 已购买课程而未评论listView的Adapter
 */

public class MyListViewNoConmmentClassAdapter extends BaseAdapter implements OnClickListener{
    private Context mContext;

    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mBuyTime;//购买时长
    private TextView mCourseCost;//课程价格
    private List<OrderBuyCourseAsStudentDTO> courseNoCommentList;//完成课程但还未评价订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private CourseDTO mCourseDTO;//课程信息
    private RewardDTO mRewardDTO;//悬赏信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息

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
        initView(view);
        mOrderBuyCourseAsStudentDTO = courseNoCommentList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO = null ;
        switch ( mOrderBuyCourseDTO.getCourseTypeEnum() ){
            case 学生悬赏:{
                courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();
            }break;
            case 老师课程:{
                courseDTO =  mOrderBuyCourseAsStudentDTO.getCourse();
            }break;
        }

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_pay:
                pay();
        }
    }

    private void pay() {
        Toast.makeText(mContext, "支付未完成的订单", Toast.LENGTH_SHORT).show();
    }
}
