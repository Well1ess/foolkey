package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.ModelStudent.Me.info.StudentInfoActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * 把悬赏的listView换成RecyclerView
 * 试试
 * Created by geyao on 2017/6/6.
 */
@Deprecated
public class RewardRecyclerViewAdapter extends RecyclerView.Adapter<RewardViewHolder>{
    private Context mContext;

    private RequestManager glide;


    /**
     * 构造函数
     */
    public RewardRecyclerViewAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.recyclerview_reward, parent, false);
        RewardViewHolder holder = new RewardViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RewardViewHolder holder, final int position) {

        //获取当前的reward
        RewardDTO rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();
        StudentDTO studentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();



        holder.title.setText(rewardDTO.getTopic());
        holder.money.setText(rewardDTO.getPrice()+"");
        holder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        //以前显示标签，但我觉得还是显示名字好
//        viewHolder.studentKind.setText(rewardDTO.getStudentBaseEnum().toString());
        holder.studentKind.setText( studentDTO.getNickedName() + "");

        //加载图片
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into( holder.head );
        holder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStudentInfo = new Intent(mContext, StudentInfoActivity.class);
                toStudentInfo.putExtra("position", position);
                mContext.startActivity(toStudentInfo, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().size();
    }
}
