package com.example.a29149.yuyuan.Model.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.a29149.yuyuan.Util.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏未评价listView的Adapter
 */

public class MyListViewNoCommentRewardAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private  View view ;
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mExceptTime;//预计时长
    private TextView mTeacherCharge;//老师收费
    private TextView mCommentReward;//评论悬赏
    private List<Map<String,Object>> courseNoPayList;//已购买课程但还未付款订单
    private List<OrderBuyCourseAsStudentDTO> rewardNoCommentList;//完成课程但还未评价订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private boolean isReward = false;//是否是悬赏
    private int position; //记录位置
    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表

    public MyListViewNoCommentRewardAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> rewardNoCommentList) {
        this.rewardNoCommentList = rewardNoCommentList;
    }

    @Override
    public int getCount() {
        return rewardNoCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardNoCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view=View.inflate(mContext, R.layout.listview_item_nocommnent_reward,null);
        this.position = position;
        initView();
        mOrderBuyCourseAsStudentDTO = rewardNoCommentList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO = null ;
        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();

        mTeacherNameAndCourseName.setText(mStudentDTO.getNickedName()+":"+courseDTO.getTopic());
        mExceptTime.setText("预计时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        mTeacherCharge.setText("老师收费：" + courseDTO.getPrice().toString()+ Const.PRICE_NAME);

        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mExceptTime = (TextView) view.findViewById(R.id.tv_exceptTime);
        mTeacherCharge = (TextView) view.findViewById(R.id.teacherCharge);
        mCommentReward = (TextView) view.findViewById(R.id.tv_comment);
        mCommentReward.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_comment:
                commentReward();
        }
    }

    private void commentReward() {
        //跳转到悬赏订单评价
        Intent intent = new Intent(mContext, CommentRewardActivity.class);
        intent.putExtra("position",position);
        String orderId = rewardNoCommentList.get(position).getOrderDTO().getId() + "";
        Log.i("malei","orderId="+orderId);
        intent.putExtra("orderId",orderId);
        mContext.startActivity(intent);
    }
}
