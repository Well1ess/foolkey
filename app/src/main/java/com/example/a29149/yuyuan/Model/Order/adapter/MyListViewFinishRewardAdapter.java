package com.example.a29149.yuyuan.Model.Order.adapter;

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
 * 已完成的悬赏listView的Adapter
 */

public class MyListViewFinishRewardAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private  View view ;
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mExceptTime;//预计时长
    private TextView mTeacherCharge;//老师收费
    private TextView mStudentRewardState;//学生悬赏状态：是否申请
    private TextView mPay;//学生点击付款按钮
    private List<Map<String,Object>> courseNoPayList;//已购买课程但还未付款订单

    public MyListViewFinishRewardAdapter(Context context)
    {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
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
        view=View.inflate(mContext, R.layout.listview_item_finish_reward,null);
        initView();
        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mExceptTime = (TextView) view.findViewById(R.id.tv_exceptTime);
        mTeacherCharge = (TextView) view.findViewById(R.id.teacherCharge);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_pay:
                pay();
        }
    }

    private void pay() {
        Toast.makeText(mContext, "支付未付款的悬赏", Toast.LENGTH_SHORT).show();
    }
}
