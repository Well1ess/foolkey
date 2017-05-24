package com.example.a29149.yuyuan.ModelStudent.Order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.ModelStudent.Order.global.GlobalValue;
import com.example.a29149.yuyuan.R;

import java.util.List;
import java.util.Map;


/**
 * Created by MaLei on 2017/4/24.
 * Email:ml1995@mail.ustc.edu.cn
 * 发布悬赏listView的Adapter
 */

public class MyListViewRewardAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private  View view ;
    private ImageView mTeacherPhone;//老师头像
    private TextView mTeacherNameAndCourseName;//老师姓名和课程名
    private TextView mExceptTime;//预计时长
    private TextView mTeacherCharge;//老师收费
    private TextView mStudentRewardState;//学生悬赏状态：是否申请
    private TextView mPay;//学生点击付款按钮
    private List<Map<String,Object>> courseNoPayList;//已购买课程但还未付款订单

    public MyListViewRewardAdapter(Context context)
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
        view=View.inflate(mContext, R.layout.listview_item_reward,null);
        initView();
        final RadioButton rb_bug = (RadioButton) view.findViewById(R.id.rb_buy);
        //设置订单状态监听
        final GlobalValue globalValue = new GlobalValue();
        rb_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = globalValue.isCheck();
                if(isCheck)
                {
                    if(v==rb_bug)rb_bug.setChecked(false);
                }
                else
                {
                    if(v==rb_bug)rb_bug.setChecked(true);
                }
                globalValue.setCheck(!isCheck);
            }
        });
        return view;
    }

    private void initView() {
        mTeacherPhone = (ImageView) view.findViewById(R.id.iv_teacherPhone);
        mTeacherNameAndCourseName = (TextView) view.findViewById(R.id.tv_teacherNameAndCourseName);
        mExceptTime = (TextView) view.findViewById(R.id.tv_exceptTime);
        mTeacherCharge = (TextView) view.findViewById(R.id.teacherCharge);
        mStudentRewardState = (TextView) view.findViewById(R.id.tv_studentRewardState);
        mPay = (TextView) view.findViewById(R.id.tv_pay);
        mPay.setOnClickListener(this);
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
