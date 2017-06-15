package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 * GridView的适配器，即申请该条悬赏的老师信息适配器
 */
@Deprecated
public class TeacherReplyListAdapter extends BaseAdapter
{

    private RequestManager glide;

    private Context mContext;
    private List<ApplicationRewardWithTeacherSTCDTO> applicationRewardWithTeacherSTCDTOList;

    public TeacherReplyListAdapter(Context context, List<ApplicationRewardWithTeacherSTCDTO> strings)
    {
        this.mContext = context;
        this.applicationRewardWithTeacherSTCDTOList = strings;
    }

    public void update()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return applicationRewardWithTeacherSTCDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return applicationRewardWithTeacherSTCDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.gridview_reply_item_layout, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        glide = Glide.with(mContext);
        glide.load(PictureInfoBO.getOnlinePhoto(applicationRewardWithTeacherSTCDTOList.get(position).getTeacherAllInfoDTO().getUserName()))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform(mContext))
                .into(myViewHolder.tv);

//        myViewHolder.mPhoto.setText(applicationRewardWithTeacherSTCDTOList.get(position).getTeacherAllInfoDTO().getNickedName());
        myViewHolder.teacherName.setText(applicationRewardWithTeacherSTCDTOList.get(position).getTeacherAllInfoDTO().getNickedName());
        return convertView;
    }

    static class MyViewHolder
    {

        ImageView tv;
        TextView teacherName;

        public MyViewHolder(View view)
        {
            tv = (ImageView) view.findViewById(R.id.iv_photo);
            teacherName = (TextView) view.findViewById(R.id.tv_teacherName);
        }
    }
}

