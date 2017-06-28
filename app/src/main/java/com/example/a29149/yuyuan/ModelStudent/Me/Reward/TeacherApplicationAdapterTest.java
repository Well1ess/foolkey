package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.UserPhotoAdapter;
import com.example.a29149.yuyuan.AbstractObject.YYRecycleViewBaseAdapter;
import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  展示用户头像的子类
 * 此处用来，学生查看老师对其悬赏的申请，一个老师就是一个头像
 * Created by geyao on 2017/6/15.
 */

public class TeacherApplicationAdapterTest extends YYRecycleViewBaseAdapter<ApplicationRewardWithTeacherSTCDTO> {

    private static final String TAG = "TeacherApplicationAdapt";

    //图片加载器
    private RequestManager glide;

    //包括了课程信息，申请信息等
    private ApplicationStudentRewardAsStudentSTCDTO applicationStudentRewardAsStudentSTCDTO;


    /**
     * @param dataList 数据
     * @param context  上下文
     *
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public TeacherApplicationAdapterTest(List<ApplicationRewardWithTeacherSTCDTO> dataList, Context context) {
        super(dataList, context);
    }


    /**
     * @param dataList 数据
     * @param context  上下文
     * @param applicationStudentRewardAsStudentSTCDTO 包含了课程信息，与各个申请的信息，要传到下一个activity里面去
     *
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public TeacherApplicationAdapterTest(List<ApplicationRewardWithTeacherSTCDTO> dataList,
                                         Context context,
                                         ApplicationStudentRewardAsStudentSTCDTO applicationStudentRewardAsStudentSTCDTO) {
        super(dataList, context);
        this.applicationStudentRewardAsStudentSTCDTO = applicationStudentRewardAsStudentSTCDTO;
    }

    /**
     * 构造函数
     *
     * @param mContext 上下文
     */
    public TeacherApplicationAdapterTest(Context mContext) {
        super(mContext);
    }


    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午4:22
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取ViewHolder，感觉返回值是下个函数的参数
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_reply_item_layout, null);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //获取老师信息
        TeacherAllInfoDTO teacherAllInfoDTO = getDataList().get(position).getTeacherAllInfoDTO();
        //转换为本类的viewHolder
        PersonViewHolder viewHolder = (PersonViewHolder) holder;
        //绑定数据
        viewHolder.mNickedName.setText(StringUtil.subString(teacherAllInfoDTO.getNickedName(), 8));
        viewHolder.mPrestige.setText( teacherAllInfoDTO.getPrestige() + "" );

        //加载图片
        glide = Glide.with(mContext);
        glide.load( PictureInfoBO.getOnlinePhoto(teacherAllInfoDTO.getUserName() ) )
                .placeholder(R.drawable.place_holder_grey)
                .transform(new GlideCircleTransform(mContext))
                .into(viewHolder.mPhoto);

        //TODO 点击头像跳转的注入信息
        viewHolder.mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建意图跳转到老师信息页面，决定是否接收
                Intent intent = new Intent(mContext,TeacherInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("parentDTO", applicationStudentRewardAsStudentSTCDTO);
                bundle.putSerializable("courseDTO", applicationStudentRewardAsStudentSTCDTO.getRewardDTO());
                //放置单个老师申请的信息
                bundle.putSerializable("DTO", getDataList().get(position));
                intent.putExtras( bundle );
                mContext.startActivity(intent);
                //FIXME recycleView不能被很好地刷新，只好关闭掉本 activity
                AppManager.getActivity(OwnerRewardActivity.class).finish();
                AppManager.getInstance().removeActivity(OwnerRewardActivity.class);
            }
        });
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/23 下午4:19
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  RecyclerView的专属ViewHolder
     */
    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //头像
        public ImageView mPhoto;
        //昵称
        public TextView mNickedName;
        //声望
        public TextView mPrestige;

        public PersonViewHolder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            mNickedName = (TextView) itemView.findViewById(R.id.tv_teacherName);
            mPrestige = (TextView) itemView.findViewById(R.id.tv_prestige);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            //这里可以设置点击事件
            Toast.makeText(mContext, "点击事件", Toast.LENGTH_SHORT).show();
        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         *
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) {
            //长按点击事件
            Toast.makeText(mContext, "长按事件", Toast.LENGTH_SHORT).show();
            return true;
        }



    }
}
