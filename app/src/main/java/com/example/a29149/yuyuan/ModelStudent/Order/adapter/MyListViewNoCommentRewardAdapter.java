package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.CommentRewardActivity;
import com.example.a29149.yuyuan.ModelTeacher.Order.JudgeStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏未评价listView的Adapter
 */

public class MyListViewNoCommentRewardAdapter extends BaseAdapter {
    private static final String TAG = "MyListViewNoCommentRewa";

    private Context mContext;

    private List<OrderBuyCourseAsStudentDTO> rewardNoCommentList;//完成课程但还未评价订单
    private StudentDTO mStudentDTO;//学生信息
    private TeacherDTO mTeacherDTO;//老师信息
    private OrderBuyCourseDTO mOrderBuyCourseDTO;//订单信息
    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;//全部信息
    private int position; //记录位置

    //图片加载器
    private RequestManager glide;

    public MyListViewNoCommentRewardAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> rewardNoCommentList) {
        this.rewardNoCommentList = rewardNoCommentList;
    }
    //设置当前点击位置
    public void setPosition(int position) {
        this.position = position;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.listview_item_nocommnent_reward, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据位置，获取DTO
        mOrderBuyCourseAsStudentDTO = rewardNoCommentList.get(position);
        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO =  mOrderBuyCourseAsStudentDTO.getCourse() ;
        //初始化图形界面
        initView();
        //昵称
        viewHolder.mNickedName.setText(StringUtil.subString(mStudentDTO.getNickedName(), 18));
        //购买时长
        viewHolder.mExceptTime.setText("购买时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        //价格显示
        viewHolder.mTeacherCharge.setText("" + courseDTO.getPrice().toString()+ Const.PRICE_NAME);
        //SetPositionListeten.setSetPositionListeren();

        //评论按键设置点击监听
        viewHolder.mCommentReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalUtil.getInstance().getUserRole().equals("student")) //学生评价悬赏
                    commentReward(position);
                else  // 老师评价订单，即评价学生
                    teacherJudgeStudent(position);
            }
        });

        //加载头像
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()) )
                .error(R.drawable.photo_placeholder1)
                .transform( new GlideCircleTransform( mContext ))
                .into(viewHolder.mPhoto);
        return view;
    }

    //绑定UI
    private void initView() {
        //
    }

    /**
     * 评价 悬赏 订单
     */
    private void commentReward(int position) {
        //跳转到悬赏订单评价
        Intent intent = new Intent(mContext, CommentRewardActivity.class);
        //建一个Bundle来存储信息
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", rewardNoCommentList.get(position));
        intent.putExtras(bundle);
//        intent.putExtra("position",position);
//        intent.putExtra("teacherUserName", rewardNoCommentList.get(position).getStudentDTO().getUserName());
//        intent.putExtra("teacherName", rewardNoCommentList.get(position).getStudentDTO().getNickedName());
//        intent.putExtra("courseName", rewardNoCommentList.get(position).getCourse().getTopic());
//        intent.putExtra("orderPrice", rewardNoCommentList.get(position).getOrderDTO().getAmount() + "");
        mContext.startActivity(intent);
    }

    //老师对学生进行评价
    private void teacherJudgeStudent(int position){
        //跳转到评价学生界面
        Intent intent = new Intent(mContext, JudgeStudentActivity.class);
        //Log.i("malei","position="+position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", rewardNoCommentList.get(position));
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private static class ViewHolder{
        private ImageView mPhoto;//老师头像
        private TextView mNickedName;//老师姓名
        private TextView mExceptTime;//预计时长
        private TextView mTeacherCharge;//老师收费
        private TextView mCommentReward;//评论悬赏

        ViewHolder(View view){
            mPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            mNickedName = (TextView) view.findViewById(R.id.tv_nickedName);
            mExceptTime = (TextView) view.findViewById(R.id.tv_exceptTime);
            mTeacherCharge = (TextView) view.findViewById(R.id.teacherCharge);
            mCommentReward = (TextView) view.findViewById(R.id.tv_comment);
        }
    }


}
