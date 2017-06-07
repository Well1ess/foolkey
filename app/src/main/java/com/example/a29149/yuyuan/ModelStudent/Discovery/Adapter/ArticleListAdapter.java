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

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;

/**
 * Created by 张丽华 on 2017/4/26.
 * Description:
 */

public class ArticleListAdapter extends BaseAdapter {

    private Context mContext;

    public ArticleListAdapter(Context context) {
        this.mContext = context;
    }

    public void updateList() {
        this.notifyDataSetChanged();
        log.d(this, GlobalUtil.getInstance().getContent().size());
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getContent().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getContent().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_article, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(GlobalUtil.getInstance().getContent().get(position));
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

        ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.acticle_title);
            head = (ImageView) view.findViewById(R.id.iv_photo);
        }

    }
}
