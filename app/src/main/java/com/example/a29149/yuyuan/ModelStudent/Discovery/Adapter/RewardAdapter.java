package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.AbstractObject.YYBaseAdapter;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.ModelStudent.Me.info.StudentInfoActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.StringUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 悬赏展示的Adapter
 * 继承自YYBaseAdapter
 * Created by geyao on 2017/6/12.
 */

public class RewardAdapter extends YYBaseAdapter<RewardWithStudentSTCDTO> {
    private static final String TAG = "RewardAdapter";

    private RequestManager glide;

    public RewardAdapter(List<RewardWithStudentSTCDTO> dataList, Context context) {
        super(dataList, context);
    }

    public RewardAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View createView(int position, View convertView, LayoutInflater layoutInflater) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.listview_reward, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //获取当前的reward
        RewardDTO rewardDTO = getDataList().get(position).getRewardDTO();
        final StudentDTO studentDTO = getDataList().get(position).getStudentDTO();
        //设置数据
        viewHolder.title.setText(StringUtil.subString(rewardDTO.getTopic(), 60));
        viewHolder.money.setText(rewardDTO.getPrice() + "");
        viewHolder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        viewHolder.studentKind.setText(studentDTO.getNickedName() + "");

        //加载图片
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(viewHolder.head);
        //头像设置跳转个人界面的监听器
        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看某个悬赏发布人的具体信息
                Intent intent = new Intent(mContext, StudentInfoActivity.class);
                //新建Bundle，放置具体的DTO
                Bundle bundle = new Bundle();
                //从类变量的List里获取具体的DTO
                bundle.putSerializable("DTO", studentDTO);
                //将Bundle放置在intent里，并开启新Activity
                intent.putExtras(bundle);
                mContext.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
            }
        });
        return view;
    }

    /**
     * 添加数据的时候，检测是否已经存在
     * @param data 目标数据
     * @return
     */
    @Override
    public boolean addData(RewardWithStudentSTCDTO data) {
        if (dataList == null)
            dataList = new ArrayList<>();
        boolean result = addData(dataList.size(), data);
        notifyDataSetChanged();
        return result;
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  悬赏适配器用来更新某个特定悬赏的函数，只要id一致就会更换
     * @param rewardDTO
     */
    @Override
    public void updateData(AbstractDTO rewardDTO) {
        for (RewardWithStudentSTCDTO dto : getDataList()){
            if (dto.getRewardDTO().getId().equals( ((RewardDTO)rewardDTO).getId() ) ){
                dto.setRewardDTO( (RewardDTO) rewardDTO );
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 向指定位置添加数据
     * 要求不为空
     * @param position 位置
     * @param data     目标数据
     * @return
     */
    @Override
    public boolean addData(int position, RewardWithStudentSTCDTO data) {
        if (dataList == null || data == null){
            dataList = new ArrayList<>();
            return false;
        }
        if (dataList.contains(data))
            return false;
        else {
            dataList.add(data);
            notifyDataSetChanged();
            return true;
        }
    }



    //使用ViewHolder来提高效率
    private static class ViewHolder {
        public TextView title;
        public ImageView head;
        public TextView money;
        public TextView label;
        public TextView studentKind;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.tv_title);
            head = (ImageView) view.findViewById(R.id.iv_photo);
            money = (TextView) view.findViewById(R.id.tv_price);
            label = (TextView) view.findViewById(R.id.tv_technicTagEnum);
            studentKind = (TextView) view.findViewById(R.id.tv_nickedName);
        }
    }
}
