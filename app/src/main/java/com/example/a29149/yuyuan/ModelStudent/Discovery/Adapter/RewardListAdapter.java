package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
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
 * Created by 张丽华 on 2017/4/26.
 * Description:发现界面的悬赏列表适配器
 */
//TODO 这里获取数据依然要依靠全局变量
public class RewardListAdapter extends BaseAdapter {

    private static final String TAG = "RewardListAdapter";

    private Context mContext;

    private RequestManager glide;

    //悬赏列表
    private List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOList;

    public RewardListAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 刷新列表
     */
    public void updateList() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rewardWithStudentSTCDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardWithStudentSTCDTOList.get(position);
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
            view = View.inflate(mContext, R.layout.listview_reward, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //获取当前的reward
//        RewardDTO rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();
//        StudentDTO studentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();
        Log.d(TAG, "getView: rewardListSize" + rewardWithStudentSTCDTOList.size());
        RewardDTO rewardDTO = rewardWithStudentSTCDTOList.get(position).getRewardDTO();
        final StudentDTO studentDTO = rewardWithStudentSTCDTOList.get(position).getStudentDTO();
        //设置数据
        viewHolder.title.setText(StringUtil.subString(rewardDTO.getTopic(), 60));
        viewHolder.money.setText(rewardDTO.getPrice() + "");
        viewHolder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        //以前显示标签，但我觉得还是显示名字好
//        viewHolder.studentKind.setText(rewardDTO.getStudentBaseEnum().toString());
        viewHolder.studentKind.setText(studentDTO.getNickedName() + "");

        //加载图片
//        Log.d(TAG, "getView: " + PictureInfoBO.getOnlinePhoto(studentDTO.getUserName()));
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
//                startActivity( intent );
                mContext.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());



//                Intent toStudentInfo = new Intent(mContext, StudentInfoActivity.class);
//                toStudentInfo.putExtra("position", position);
//                mContext.startActivity(toStudentInfo, ActivityOptions
//                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
            }
        });
        return view;
    }

    //使用ViewHolder来提高效率
    private static class ViewHolder {
        public TextView title;
        public ImageView head;
        public TextView money;
        public TextView label;
        public TextView studentKind;
//        public TextView prestige;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.tv_title);
            head = (ImageView) view.findViewById(R.id.iv_photo);
            money = (TextView) view.findViewById(R.id.tv_price);
            label = (TextView) view.findViewById(R.id.tv_technicTagEnum);
//            prestige = (TextView) view.findViewById(R.id.prestige);

            studentKind = (TextView) view.findViewById(R.id.tv_nickedName);
        }

    }

    //设置列表数据
    public void setData(List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOList) {
        if (rewardWithStudentSTCDTOList != null) {
            this.rewardWithStudentSTCDTOList = rewardWithStudentSTCDTOList;
        } else {
            this.rewardWithStudentSTCDTOList = new ArrayList<>();
        }
    }

    public List<RewardWithStudentSTCDTO> getDataList() {
        return rewardWithStudentSTCDTOList;
    }
}
