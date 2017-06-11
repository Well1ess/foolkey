package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.StudentJudgeCourseActivity;
import com.example.a29149.yuyuan.ModelTeacher.Order.JudgeStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 课程而未评论listView的Adapter
 */
@Deprecated
public class MyListViewNoConmmentClassAdapter extends BaseAdapter {
    private Context mContext;



    private List<OrderBuyCourseAsStudentDTO> courseNoCommentList = new ArrayList<>();//完成课程但还未评价订单

    private OrderBuyCourseAsStudentDTO mOrderBuyCourseAsStudentDTO;

    private RequestManager glide;

    public MyListViewNoConmmentClassAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> courseNoCommentList) {
        if (courseNoCommentList != null)
            this.courseNoCommentList = courseNoCommentList;
        else
            this.courseNoCommentList = new ArrayList<>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //使用了和评价悬赏相同的ViewHolder
        MyListViewNoCommentRewardAdapter.ViewHolder viewHolder;
        View view;
        //使用viewHolder来优化展示效率
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.listview_item_nocommnent_reward, null);
            viewHolder = new MyListViewNoCommentRewardAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (MyListViewNoCommentRewardAdapter.ViewHolder) view.getTag();
        }

        //获取本item的信息
        /**
         * 最主要的展示信息，就是在这里了
         */
        mOrderBuyCourseAsStudentDTO = courseNoCommentList.get(position);

        //获取各种子dto
        StudentDTO mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        TeacherDTO mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        OrderBuyCourseDTO mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();
        CourseAbstract courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();

        //设置文字信息展示
        viewHolder.mNickedName.setText(StringUtil.subString(mStudentDTO.getNickedName(), 10));
        viewHolder.mNumber.setText("时长:" + mOrderBuyCourseDTO.getNumber().toString() + "h");
        viewHolder.mAmount.setText("价格:" + courseDTO.getPrice().toString());


        //评论按键设置点击监听
        //注意，这个监听事件必须放在getView里，不然【立即评价】按钮会出现问题
        viewHolder.mJudgeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalUtil.getInstance().getUserRole().equals("student"))
                    //学生评价课程
                    judgeCourse(mOrderBuyCourseAsStudentDTO );
                else
                    // 老师评价订单，即评价学生
                    teacherJudgeStudent(mOrderBuyCourseAsStudentDTO);
            }
        });

        //加载图片
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(mStudentDTO.getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(viewHolder.mPhoto);

        return view;
    }


    /**
     * 学生评价课程
     * @param orderBuyCourseAsStudentDTO 包含了综合信息的DTO
     */
    private void judgeCourse(OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO) {
        //跳转到课程订单评价
        Intent intent = new Intent(mContext, StudentJudgeCourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 老师评价学生
     * @param orderBuyCourseAsStudentDTO 包含了综合信息的DTO
     */
    private void teacherJudgeStudent( OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO ){
        //TODO 修改下面这个activity
        Intent intent = new Intent(mContext, JudgeStudentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


}
