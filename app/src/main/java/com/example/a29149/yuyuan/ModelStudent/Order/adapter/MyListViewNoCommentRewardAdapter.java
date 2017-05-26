package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

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
        this.setSetpositionListeren(new SetPositionListeren() {
            @Override
            public int setPosition() {
                 return position;
            }
        });
        view=View.inflate(mContext, R.layout.listview_item_nocommnent_reward,null);

        mOrderBuyCourseAsStudentDTO = rewardNoCommentList.get(position);


        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO = null ;
        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();
        initView();
        String fisrtLine = mStudentDTO.getNickedName()+":"+courseDTO.getTopic();
        if (fisrtLine.length() > 9) {
            fisrtLine = fisrtLine.substring(0, 7);
            fisrtLine = fisrtLine + "...";
        }
        mTeacherNameAndCourseName.setText(fisrtLine);
        mExceptTime.setText("预计时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        mTeacherCharge.setText("" + courseDTO.getPrice().toString()+ Const.PRICE_NAME);
        //SetPositionListeten.setSetPositionListeren();

        //加载头像
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()) )
                .placeholder(R.drawable.photo_placeholder1)
                .error(R.drawable.photo_placeholder1)
                .transform( new GlideCircleTransform( mContext ))
                .into(mTeacherPhone);

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
            case R.id.tv_comment:{
                if (GlobalUtil.getInstance().getUserRole().equals("student")) //学生评价悬赏
                    commentReward();
                else  // 老师评价订单
                    teacherJudgeStudent();
            }break;
            default:break;
        }
    }

    private void commentReward() {
        //跳转到悬赏订单评价
        Intent intent = new Intent(mContext, CommentRewardActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("teacherUserName", rewardNoCommentList.get(position).getStudentDTO().getUserName());
        intent.putExtra("teacherName", rewardNoCommentList.get(position).getStudentDTO().getNickedName());
        intent.putExtra("courseName", rewardNoCommentList.get(position).getCourse().getTopic());
        intent.putExtra("orderPrice", rewardNoCommentList.get(position).getOrderDTO().getAmount() + "");


        String orderId = rewardNoCommentList.get(position).getOrderDTO().getId() + "";
        Log.i("malei","orderId="+orderId);
        intent.putExtra("orderId",orderId);
        mContext.startActivity(intent);
    }

    //老师对学生进行评价
    private void teacherJudgeStudent(){
        int position = 0;
        //4.进行回调
        if(mListeren != null){
            position = mListeren.setPosition();
        }
        Intent intent = new Intent(mContext, JudgeStudentActivity.class);
        //Log.i("malei","position="+position);
        intent.putExtra("position", position);
        //传输一些信息
        String orderId = rewardNoCommentList.get(position).getOrderDTO().getId() + "";
        intent.putExtra("orderId",orderId);

        String studentName = rewardNoCommentList.get(position).getStudentDTO().getNickedName();
        intent.putExtra("studentName", studentName);

        String courseName = rewardNoCommentList.get(position).getCourse().getTopic();
        intent.putExtra("courseName", courseName);

        String studentUserName = rewardNoCommentList.get(position).getStudentDTO().getUserName();
        intent.putExtra("studentUserName", studentUserName);

        String price = rewardNoCommentList.get(position).getOrderDTO().getAmount().toString();
        intent.putExtra("orderPrice", price);
        intent.putExtra("currency", Const.PRICE_NAME);

        mContext.startActivity(intent);
    }

    //3.定义成员变量，接受监听对象
    private SetPositionListeren mListeren;
    //1.回调接口
    public interface SetPositionListeren {
        int setPosition();
    }
    //2.暴露接口，设置监听
    public void setSetpositionListeren(SetPositionListeren listeren){
        mListeren = listeren;
    }



}
