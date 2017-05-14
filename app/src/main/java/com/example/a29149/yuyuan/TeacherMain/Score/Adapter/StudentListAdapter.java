package com.example.a29149.yuyuan.TeacherMain.Score.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;

import java.util.List;

/**
 * Created by 张丽华 on 2017/5/14.
 * Description:
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.Holder> {

    //测试的时候由构造函数传入数据，真正使用的时候可以在GlobalUtil里获取数据
    private List<String> mData;

    private Context mContext;

    private final int START_COURSE = 0;
    private final int END_COURSE = 1;

    //开始和结束的接口回调函数
    private StartCourse startCourse;
    private EndCourse endCourse;

    public StudentListAdapter(List<String> data, Context context) {
        mData = data;
        mContext = context;

        if (data.size() % 2 != 0)
            throw new IllegalArgumentException("数据必须为偶数！");

    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return START_COURSE;
        else
            return END_COURSE;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == START_COURSE)
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.recycleview_start_course, parent, false), viewType);
        else if (viewType == END_COURSE)
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.recycleview_end_course, parent, false), viewType);
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (holder != null) {
            if (getItemViewType(position) == START_COURSE)
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (startCourse != null)
                            startCourse.onStartCourse();
                    }
                });
            else if (getItemViewType(position) == END_COURSE)
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (endCourse != null)
                            endCourse.onEndCourse();
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setStartCourse(StartCourse startCourse) {
        this.startCourse = startCourse;
    }

    public void setEndCourse(EndCourse endCourse) {
        this.endCourse = endCourse;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public ImageView head;
        public TextView button;

        public Holder(View itemView, int viewType) {
            super(itemView);
            head = (ImageView) itemView.findViewById(R.id.head);
            button = (TextView) itemView.findViewById(R.id.button);

        }
    }

    public interface StartCourse {
        void onStartCourse();
    }

    public interface EndCourse {
        void onEndCourse();
    }
}
