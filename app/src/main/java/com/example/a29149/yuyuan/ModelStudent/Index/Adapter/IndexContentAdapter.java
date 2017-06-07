package com.example.a29149.yuyuan.ModelStudent.Index.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;


/**
 * Created by 张丽华 on 2017/3/27.
 */

public class IndexContentAdapter extends BaseAdapter {

    private Context mContext;
    private TechnicTagEnum mTechnicTagEnum;

    public IndexContentAdapter(Context context, TechnicTagEnum mTechnicTagEnum) {
        this.mContext = context;
        this.mTechnicTagEnum = mTechnicTagEnum;
    }

    public void updateList() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_course, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).get(position).getCourseTeacherDTO().getTopic());
        viewHolder.content.setText(GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).get(position).getCourseTeacherDTO().getDescription());
        viewHolder.author.setText(GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).get(position).getCourseTeacherDTO().getCreatorId()+"");
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
        public TextView author;
        public TextView content;
        public ImageView head;

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.tv_title);
            author = (TextView) view.findViewById(R.id.tv_nickedName);
            content = (TextView) view.findViewById(R.id.tv_description);
            head = (ImageView) view.findViewById(R.id.iv_photo);
        }

    }
}
