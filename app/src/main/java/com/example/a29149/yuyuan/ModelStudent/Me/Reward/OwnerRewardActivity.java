package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.course.reward.GetWithApplicationController;
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
public class OwnerRewardActivity extends AppCompatActivity {

    //悬赏列表
    @ViewInject(R.id.reward_list)
    private ListView mRewardList;

    //适配器
    private OwnerRewardListAdapter mAdapter;

    public static ShapeLoadingDialog shapeLoadingDialog;

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

    @OnClick(R.id.bt_return)
    public void setReturnListener(View view)
    {
        this.finish();
    }

    //查看我发布的悬赏
    private void applyReward() {
        new ApplyRewardAction(1).execute();
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
                        Toast.makeText(OwnerRewardActivity.this, "获取悬赏成功！", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(OwnerRewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OwnerRewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
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
