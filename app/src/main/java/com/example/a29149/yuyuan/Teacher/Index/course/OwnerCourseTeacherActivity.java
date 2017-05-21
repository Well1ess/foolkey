package com.example.a29149.yuyuan.Teacher.Index.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by MaLei on 2017/5/12.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师拥有的课程
 */

//拥有的课程
public class OwnerCourseTeacherActivity extends AppCompatActivity {

    //课程列表
    @ViewInject(R.id.reward_list)
    private ListView mCourseList;

    //适配器
    private OwnerCourseListAdapter mAdapter;

    public static ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_course_teacher);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        loadData();




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

    //获取我的学生列表
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

            return URL.doWithGetMyCourseURL( pageNo + "");

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
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsTeacherSTCDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyCourseAsTeacherSTCDTOS"), type);
                    GlobalUtil.getInstance().setOrderBuyCourseAsTeacherSTCDTOs(orderBuyCourseAsTeacherSTCDTOs);
                    Log.i("malei",orderBuyCourseAsTeacherSTCDTOs.toString());

                    if (resultFlag.equals("success")) {
                        Toast.makeText(OwnerCourseTeacherActivity.this, "获取课程成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerCourseListAdapter(OwnerCourseTeacherActivity.this);
                                mCourseList.setAdapter(mAdapter);
                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(OwnerCourseTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OwnerCourseTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 获取我的课程请求Action
     */
    public class ApplyCourseAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public ApplyCourseAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return URL.doWithApplyStudentListURL(pageNo + "");

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
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsTeacherSTCDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyCourseAsTeacherSTCDTOS"), type);
                    GlobalUtil.getInstance().setOrderBuyCourseAsTeacherSTCDTOs(orderBuyCourseAsTeacherSTCDTOs);
                    Log.i("malei",orderBuyCourseAsTeacherSTCDTOs.toString());

                    if (resultFlag.equals("success")) {
                        Toast.makeText(OwnerCourseTeacherActivity.this, "获取课程成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerCourseListAdapter(OwnerCourseTeacherActivity.this);
                                mCourseList.setAdapter(mAdapter);
                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(OwnerCourseTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OwnerCourseTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



}
