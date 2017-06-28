package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.a29149.yuyuan.AbstractObject.UserPhotoAdapter;
import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;

/**
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  展示用户头像的子类
 * 此处用来，学生查看老师对其悬赏的申请，一个老师就是一个头像
 * Created by geyao on 2017/6/15.
 */
@Deprecated
public class TeacherApplicationAdapter extends UserPhotoAdapter<ApplicationRewardWithTeacherSTCDTO> {

    /**
     * @param dataList 数据
     * @param context  上下文
     *
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public TeacherApplicationAdapter(List<ApplicationRewardWithTeacherSTCDTO> dataList, Context context) {
        super(dataList, context);
    }

    /**
     * 构造函数
     *
     * @param mContext 上下文
     */
    public TeacherApplicationAdapter(Context mContext) {
        super(mContext);
    }

    /**
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  子类来给昵称、头像赋值，或者编写逻辑
     *
     * @param photoViewHolder ViewHolder
     * @param position        位置
     */
    @Override
    protected void setUI(UserPhotoAdapter.PhotoViewHolder photoViewHolder, int position) {
        //设定老师姓名
        photoViewHolder.mNickedName.setText(getDataList().get(position).getTeacherAllInfoDTO().getNickedName());
        //绑定上下文
        glide = Glide.with(mContext);
        //设定老师头像
        glide.load(PictureInfoBO.getOnlinePhoto(getDataList().get(position).getTeacherAllInfoDTO().getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(photoViewHolder.mPhoto);
        photoViewHolder.mPrestige.setText(getDataList().get(position).getTeacherAllInfoDTO().getPrestige() + "");
        photoViewHolder.mPrestige.setVisibility(View.VISIBLE);
    }

    /**
     * @param data 目标数据
     *
     * @return 结果
     *
     * @discription 移出目标数据
     * @author 29149
     * @time 2017/6/10 15:16
     */
    @Override
    public boolean remove(ApplicationRewardWithTeacherSTCDTO data) {
        return super.remove(data);
    }
}
