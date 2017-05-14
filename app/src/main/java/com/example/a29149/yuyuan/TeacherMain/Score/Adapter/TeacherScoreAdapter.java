package com.example.a29149.yuyuan.TeacherMain.Score.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.TeacherMain.Score.MoreStudent.MoreStudentActivity;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;


/**
 * Created by 张丽华 on 2017/3/27.
 * 课程或者悬赏下的学生列表,
 * 测试，已废弃
 */

public class TeacherScoreAdapter extends BaseAdapter {

    private Context mContext;

    public TeacherScoreAdapter(Context context) {
        this.mContext = context;
    }

    public void updateList() {
        this.notifyDataSetChanged();
        log.d(this, GlobalUtil.getInstance().getTeacherUIScore().size());
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getTeacherUIScore().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getTeacherUIScore().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_teacher_score_student, null);
        }

        convertView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTeacherIndexActivity = new Intent(mContext, MoreStudentActivity.class);
                mContext.startActivity(toTeacherIndexActivity, ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, v, "shareHead").toBundle());
            }
        });

        return convertView;
    }

}
