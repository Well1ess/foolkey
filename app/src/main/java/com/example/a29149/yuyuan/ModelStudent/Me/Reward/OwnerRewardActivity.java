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
    private OwnerRewardListAdapter mAdapter = new OwnerRewardListAdapter(OwnerRewardActivity.this);

    //加载动画
    public static ShapeLoadingDialog shapeLoadingDialog;

    //加载动画
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

        //绑定listView与adapter
        mRewardList.setAdapter( mAdapter );

        loadData();

//        AppManager.getInstance().addActivity(this);


    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            applyReward();
        }
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
//        new ApplyRewardAction(1).execute();
        action = new GetRewardApplicationAction( pageNo );
        action.setOnAsyncTask(new YYBaseAction.OnAsyncTask<List<ApplicationStudentRewardAsStudentSTCDTO>>() {
            @Override
            public void onSuccess(List<ApplicationStudentRewardAsStudentSTCDTO> data) {
                Toast.makeText(OwnerRewardActivity.this, SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail() {
                Toast.makeText(OwnerRewardActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNull() {
                Toast.makeText(OwnerRewardActivity.this, NULL_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        });
        action.execute();
    }
    /**
     * 获取我的悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public ApplyRewardAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            //只会获取我的未解决的悬赏，内含部分申请的老师
            return GetWithApplicationController.execute( pageNo + "");


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    //存储所有我拥有的悬赏信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<ApplicationStudentRewardAsStudentSTCDTO>>() {
                    }.getType();
                    List < ApplicationStudentRewardAsStudentSTCDTO > applicationStudentRewardAsStudentSTCDTOs;
                    try {
                         applicationStudentRewardAsStudentSTCDTOs = new Gson().fromJson(jsonObject.getString("applicationStudentRewardAsStudentSTCDTOS"), type);
                    }catch (JSONException e){
                        e.printStackTrace();
                        applicationStudentRewardAsStudentSTCDTOs = new ArrayList<>();
                    }

                    GlobalUtil.getInstance().setApplicationStudentRewardAsStudentSTCDTOs(applicationStudentRewardAsStudentSTCDTOs);

                    if (resultFlag.equals("success")) {
//                        Toast.makeText(OwnerRewardActivity.this, "获取悬赏成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerRewardListAdapter(OwnerRewardActivity.this);
                                mRewardList.setAdapter(mAdapter);
                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(OwnerRewardActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OwnerRewardActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
