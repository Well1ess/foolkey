package com.example.a29149.yuyuan.Model.Me.Reward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class TeacherReplyListAdapter extends BaseAdapter
{

    private Context mContext;
    private List<String> strings;

    public TeacherReplyListAdapter(Context context, List<String> strings)
    {
        this.mContext = context;
        this.strings = strings;
    }

    public void update()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
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
        myViewHolder.tv.setText(strings.get(position));
        return convertView;
    }

    static class MyViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}

