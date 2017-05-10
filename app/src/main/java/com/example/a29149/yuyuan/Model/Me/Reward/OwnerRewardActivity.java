package com.example.a29149.yuyuan.Model.Me.Reward;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;

//拥有的悬赏
public class OwnerRewardActivity extends AppCompatActivity {

    //悬赏列表
    @ViewInject(R.id.reward_list)
    private ListView mRewardList;

    //适配器
    private OwnerRewardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_reward);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        mAdapter = new OwnerRewardListAdapter(this);
        mRewardList.setAdapter(mAdapter);

    }

    @OnClick(R.id.bt_return)
    public void setReturnListener(View view)
    {
        this.finish();
    }


}
