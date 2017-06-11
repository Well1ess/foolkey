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
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 课程而未上课listView的Adapter
 */
@Deprecated
public class MyListViewNoClassCourseAdapter extends BaseAdapter implements OnClickListener{
    private Context mContext;

    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mCourseTitle;//课程标题
    private TextView mCourseCost;//课程价格
    private TextView mCanael;//取消订单
    private List<OrderBuyCourseAsStudentDTO> courseNoStartList;//课程但还未开始订单
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

    private RequestManager glide;

    public MyListViewNoClassCourseAdapter(Context context)
    {
        this.mContext = context;
    }

    //设置列表数据
    public void setData(List<OrderBuyCourseAsStudentDTO> courseNoStartList) {
        if (courseNoStartList == null) {
            this.courseNoStartList = new ArrayList<>();
        }else {
            this.courseNoStartList = courseNoStartList;
        }
    }

    @Override
    public int getCount() {
        if (courseNoStartList == null)
            courseNoStartList = new ArrayList<>();
        return courseNoStartList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseNoStartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        view=View.inflate(mContext, R.layout.listview_item_nostart_course,null);
        this.position = position;

        mOrderBuyCourseAsStudentDTO = courseNoStartList.get(position);

        mStudentDTO = mOrderBuyCourseAsStudentDTO.getStudentDTO();
        mTeacherDTO = mOrderBuyCourseAsStudentDTO.getTeacherDTO();
        mOrderBuyCourseDTO = mOrderBuyCourseAsStudentDTO.getOrderDTO();

        courseDTO = mOrderBuyCourseAsStudentDTO.getCourse();
        initView(view);


        mCourseTitle.setText(StringUtil.subString(courseDTO.getTopic(), 20));
        mCourseCost.setText(StringUtil.subString( courseDTO.getPrice().toString()+ Const.PRICE_NAME , 15));
        mTeacherNameAndCourseName.setText(StringUtil.subString(mStudentDTO.getNickedName(), 10));

        return view;
    }

    private void initView(View view) {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_photo);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_nickedName);
        //mCourseTitle = (TextView) view.findViewById(R.id.tv_courseTitle);
        mCourseCost = (TextView) view.findViewById(R.id.tv_amount);
        mCanael = (TextView) view.findViewById(R.id.tv_judge);
        mCanael.setOnClickListener(this);

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
            case R.id.tv_judge:
                cancelCourse();
        }
    }

    private void cancelCourse() {
//        //跳转到课程订单评价
//        Intent intent = new Intent(mContext, StudentJudgeCourseActivity.class);
//        intent.putExtra("position",position);
//        intent.putExtra("Topic",courseDTO.getTopic());
//        intent.putExtra("TeacherName",mStudentDTO.getNickedName());
//        intent.putExtra("Description",courseDTO.getDescription());
//        intent.putExtra("CoursePrice",courseDTO.getPrice());
//
//        mContext.startActivity(intent);
        Toast.makeText(mContext, "取消该订单", Toast.LENGTH_SHORT).show();
    }
}
