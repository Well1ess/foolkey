package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.action.GetRewardApplicationAction;
import com.example.a29149.yuyuan.controller.course.reward.GetWithApplicationController;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by MaLei on 2017/5/11.
 * Email:ml1995@mail.ustc.edu.cn
 * 我拥有的悬赏
 */
//拥有的悬赏
public class OwnerRewardActivity extends AbstractAppCompatActivity {

    //悬赏列表
    @ViewInject(R.id.lv_reward_list)
    private ListView mRewardList;

    //适配器
    private RewardApplicationAdapter mAdapter;

    //加载动画
    public static ShapeLoadingDialog shapeLoadingDialog;

    //执行网络通讯的action
    private GetRewardApplicationAction action;

    //页码
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_reward);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        //Adapter的新建可能不能放在声明时定义
        mAdapter = new RewardApplicationAdapter(OwnerRewardActivity.this);

        //绑定listView与adapter
        mRewardList.setAdapter( mAdapter );

        loadData();
    }

    private void loadData()
    {
        applyReward();
    }

    @OnClick(R.id.tv_return)
    public void setReturnListener(View view)
    {
        this.finish();
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  执行网络请求
     */
    private void applyReward() {
        //每次都要新建任务
        action = new GetRewardApplicationAction( pageNo );

        //给任务设置结果回调
        action.setOnAsyncTask(new YYBaseAction.OnAsyncTask<List<ApplicationStudentRewardAsStudentSTCDTO>>() {
            @Override
            public void onSuccess(List<ApplicationStudentRewardAsStudentSTCDTO> data) {
                Toast.makeText(OwnerRewardActivity.this, SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
                //设置数据
                mAdapter.setData(data);
                //TODO FIXME 这里没有分页展示！
                //跳跳跳动画消失
                shapeLoadingDialog.dismiss();
            }

            @Override
            public void onFail() {
                Toast.makeText(OwnerRewardActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                //跳跳跳动画消失
                shapeLoadingDialog.dismiss();
            }

            @Override
            public void onNull() {
                Toast.makeText(OwnerRewardActivity.this, NULL_MESSAGE, Toast.LENGTH_SHORT).show();
                //跳跳跳动画消失
                shapeLoadingDialog.dismiss();
            }
        });
        action.execute();

    }
}
