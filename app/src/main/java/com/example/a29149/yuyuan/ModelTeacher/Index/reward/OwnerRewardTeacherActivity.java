package com.example.a29149.yuyuan.ModelTeacher.Index.reward;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyRewardAsTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.teacher.home.GetAgreedOnClassOrderRewardByTeacherController;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by MaLei on 2017/5/12.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师拥有的悬赏
 */

//拥有的课程
public class OwnerRewardTeacherActivity extends AbstractAppCompatActivity {

    //课程列表
    @ViewInject(R.id.lv_reward_list)
    private ListView mCourseList;

    //适配器
    private OwnerRewardListAdapter mAdapter;

    public static ShapeLoadingDialog shapeLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_reward_teacher);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        loadData();




    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            applyReward();
        }
    }

    @OnClick(R.id.tv_return)
    public void setReturnListener(View view)
    {
        this.finish();
    }

    //获取我的学生列表
    private void applyReward() {
        new ApplyRewardAction(1).execute();
    }
    /**
     * 获取老师的悬赏请求Action
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
            shapeLoadingDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            return GetAgreedOnClassOrderRewardByTeacherController.execute(pageNo + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    //存储所有我拥有的课程信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyRewardAsTeacherSTCDTO>>() {
                    }.getType();
                    List<OrderBuyRewardAsTeacherSTCDTO> orderBuyRewardAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyRewardAsTeacherSTCDTOS"), type);
                    GlobalUtil.getInstance().setOrderBuyRewardAsTeacherSTCDTOs(orderBuyRewardAsTeacherSTCDTOs);
                    Log.i("malei",orderBuyRewardAsTeacherSTCDTOs.toString());

                    if (resultFlag.equals("success")) {
                        Toast.makeText(OwnerRewardTeacherActivity.this, "获取悬赏成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerRewardListAdapter(OwnerRewardTeacherActivity.this);
                                mCourseList.setAdapter(mAdapter);

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(OwnerRewardTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
                finally {
                    shapeLoadingDialog.show();
                }
            } else {
                Toast.makeText(OwnerRewardTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }




}
