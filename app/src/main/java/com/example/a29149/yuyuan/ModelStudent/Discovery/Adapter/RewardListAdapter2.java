package com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
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
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;

/**
 * Created by 张丽华 on 2017/4/26.
 * Description:发现界面的悬赏列表适配器,改造数据源
 */
//TODO 这里获取数据依然要依靠全局变量
public class RewardListAdapter2 extends BaseAdapter {

    private static final String TAG = "RewardListAdapter";

    private Context mContext;

    private RequestManager glide;

    private List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOs;//悬赏列表数据

    public RewardListAdapter2(Context context) {
        this.mContext = context;
    }

    //设置数据源
    public void setData(List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOs) {
        this.rewardWithStudentSTCDTOs = rewardWithStudentSTCDTOs;
    }

    /**
     * 刷新列表
     */
    public void updateList() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rewardWithStudentSTCDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return rewardWithStudentSTCDTOs.get(position);
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
        RewardDTO rewardDTO = rewardWithStudentSTCDTOs.get(position).getRewardDTO();
        StudentDTO studentDTO = rewardWithStudentSTCDTOs.get(position).getStudentDTO();
        //设置数据
        viewHolder.title.setText(rewardDTO.getTopic());
        viewHolder.money.setText(rewardDTO.getPrice()+"");
        viewHolder.label.setText(rewardDTO.getTechnicTagEnum().toString());
        //以前显示标签，但我觉得还是显示名字好
//        viewHolder.studentKind.setText(rewardDTO.getStudentBaseEnum().toString());
        viewHolder.studentKind.setText( studentDTO.getNickedName() + "");

        //加载图片
//        Log.d(TAG, "getView: " + PictureInfoBO.getOnlinePhoto(studentDTO.getUserName()));
        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName() ) )
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into( viewHolder.head );
        //头像设置跳转个人界面的监听器
        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStudentInfo = new Intent(mContext, StudentInfoActivity.class);
                toStudentInfo.putExtra("position", position);
                mContext.startActivity(toStudentInfo, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
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

            studentKind = (TextView) view.findViewById(R.id.tv_studentBaseEnum);
        }

    }
}
