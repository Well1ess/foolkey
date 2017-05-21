package com.example.a29149.yuyuan.Model.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;

/**
 * Created by 张丽华 on 2017/4/26.
 * Description:发现界面的悬赏列表适配器
 */

public class RewardListAdapter extends BaseAdapter {

    private Context mContext;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        if (rewardDTO.getCreatorId().equals( GlobalUtil.getInstance().getStudentDTO().getId()) ){
            //如果这个悬赏是自己的，则不显示
//            return null;
        }

        viewHolder.title.setText(rewardDTO.getTopic());
        viewHolder.money.setText(rewardDTO.getPrice()+"");
        viewHolder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        viewHolder.studentKind.setText(rewardDTO.getStudentBaseEnum().toString());

        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTeacherIndexActivity = new Intent(mContext, OriginIndexActivity.class);
                mContext.startActivity(toTeacherIndexActivity, ActivityOptions
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

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.reward_title);
            head = (ImageView) view.findViewById(R.id.head);
            money = (TextView) view.findViewById(R.id.reward_money);
            label = (TextView) view.findViewById(R.id.label);
            studentKind = (TextView) view.findViewById(R.id.student_kind);
        }

    }
}
