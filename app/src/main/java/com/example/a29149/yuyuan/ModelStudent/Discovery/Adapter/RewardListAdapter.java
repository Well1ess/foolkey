package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.ModelStudent.Me.info.StudentInfoActivity;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * Created by 张丽华 on 2017/4/26.
 * Description:发现界面的悬赏列表适配器
 */

public class RewardListAdapter extends BaseAdapter {

    private Context mContext;

    private RequestManager glide;

    public RewardListAdapter(Context context) {
        this.mContext = context;
    }

    public void updateList() {
        this.notifyDataSetChanged();
        log.d(this, GlobalUtil.getInstance().getContent().size());
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_reward, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //获取当前的reward
        RewardDTO rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();
        StudentDTO studentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();



        viewHolder.title.setText(rewardDTO.getTopic());
        viewHolder.money.setText(rewardDTO.getPrice()+"");
        viewHolder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        //以前显示标签，但我觉得还是显示名字好
//        viewHolder.studentKind.setText(rewardDTO.getStudentBaseEnum().toString());
        viewHolder.studentKind.setText( studentDTO.getNickedName() + "");

        //加载图片
        glide = Glide.with(mContext);
        boolean isPic = glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName() ) )
                .transform(new GlideCircleTransform(mContext))
                .into( viewHolder.head ).getRequest().isFailed();
        //如果没成功，则加载一张别的图片
//        if (!isPic){
//            glide.load(
//                    PictureInfoBO.getDefaultPicCloudPath(
//                    (int)(Math.random() * PictureInfoBO.defaultPicNum) )   //随机取一张
//                    )
//                    .transform(new GlideCircleTransform(mContext))
//
//                    .into( viewHolder.head );
//        }


        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStudentInfo = new Intent(mContext, StudentInfoActivity.class);
                toStudentInfo.putExtra("position", position);
                mContext.startActivity(toStudentInfo, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        public TextView title;
        public ImageView head;
        public TextView money;
        public TextView label;
        public TextView studentKind;
//        public TextView prestige;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.reward_title);
            head = (ImageView) view.findViewById(R.id.photo_circle);
            money = (TextView) view.findViewById(R.id.reward_money);
            label = (TextView) view.findViewById(R.id.label);
//            prestige = (TextView) view.findViewById(R.id.prestige);

            studentKind = (TextView) view.findViewById(R.id.student_kind);
        }

    }
}
