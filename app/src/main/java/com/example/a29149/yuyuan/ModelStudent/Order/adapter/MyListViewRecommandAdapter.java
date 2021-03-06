package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;

import java.util.List;
import java.util.Map;

/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 愚人精选的listView的Adapter
 */

public class MyListViewRecommandAdapter extends BaseAdapter implements View.OnClickListener {
    
    private Context mContext;
    private  View view ;

    private ImageView mTeacherPhone;//老师头像

    private TextView mTeacherName;//老师姓名
    private TextView mTeacherPrestige;
    private TextView mTeacherSlogan;
    private TextView mBuyTime;//购买时长
    private TextView mCourseDescription;//课程描述
    private TextView mFollow;//是否关注
    private List<Map<String,Object>> courseNoPayList;//已购买课程但还未付款订单
    
    public MyListViewRecommandAdapter(Context context)
    {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        view=View.inflate(mContext, R.layout.listview_item_recommand,null);
        initView();
        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_photo);
        mTeacherName = (TextView) view.findViewById(R.id.tv_teacher_name);
        mTeacherSlogan = (TextView) view.findViewById(R.id.teacher_slogan);
        mTeacherPrestige = (TextView) view.findViewById(R.id.teacher_prestige);
        mFollow = (TextView) view.findViewById(R.id.not_follow);
        mFollow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.not_follow:
                follow();
        }
    }

    private void follow() {
        Toast.makeText(mContext, "已关注", Toast.LENGTH_SHORT).show();
    }
}
