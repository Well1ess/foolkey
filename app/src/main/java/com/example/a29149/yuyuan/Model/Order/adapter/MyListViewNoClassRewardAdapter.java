package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Model.Order.activity.CommentRewardActivity;
import com.example.a29149.yuyuan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏未评价listView的Adapter
 */

public class MyListViewNoClassRewardAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private  View view ;
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mRewardTitle;//预计时长
    private TextView mTeacherCharge;//老师收费
    private TextView mCancalReward;//取消悬赏
    private List<Map<String,Object>> courseNoPayList;//已购买课程但还未付款订单
    private List<OrderBuyCourseAsStudentDTO> rewardNoStartList;//购买悬赏但还未开始订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private boolean isReward = false;//是否是悬赏
    private int position; //记录位置
    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表

    public MyListViewNoClassRewardAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> rewardNoStartList) {
        this.rewardNoStartList = rewardNoStartList;
    }

    @Override
    public int getCount() {
        return rewardNoStartList.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardNoStartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view=View.inflate(mContext, R.layout.listview_item_nostart_reward,null);
        this.position = position;
        initView();
        mOrderBuyCourseAsStudentDTO = rewardNoStartList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO = null ;
        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();

        mTeacherNameAndCourseName.setText(mStudentDTO.getNickedName()+":"+courseDTO.getTopic());
        mRewardTitle.setText("悬赏标题:" + courseDTO.getTopic().toString() + "");
        mTeacherCharge.setText("悬赏价格：" + courseDTO.getPrice().toString()+"RMB");

        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mRewardTitle = (TextView) view.findViewById(R.id.tv_rewardTitle);
        mTeacherCharge = (TextView) view.findViewById(R.id.tv_rewardCost);
        mCancalReward = (TextView) view.findViewById(R.id.tv_cancel);
        mCancalReward.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_cancel:
                cancelReward();
        }
    }

    private void cancelReward() {
        Toast.makeText(mContext, "取消订单", Toast.LENGTH_SHORT).show();
    }
}