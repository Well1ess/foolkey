package com.example.a29149.yuyuan.ModelTeacher.TeacherMain.Score.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.ModelTeacher.Index.course.OwnerCourseListAdapter;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.teacher.home.GetAgreedOnClassOrderCourseByTeacherController;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaLei on 2017/5/14.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师发布的课程
 */
public class OwnerCourseTeacherFragment extends Fragment {

    //课程列表
    private ListView mCourseList;

    //适配器
    private OwnerCourseListAdapter mAdapter;

    public static ShapeLoadingDialog shapeLoadingDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_course_teacher,null);

        mCourseList = (ListView) view.findViewById(R.id.course_list);

        shapeLoadingDialog = new ShapeLoadingDialog(getContext());
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        loadData();

        return view;
    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            applyCourse();
        }
    }


    //获取我的课程
    private void applyCourse() {
        new ApplyCourseAction(1).execute();
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

            return GetAgreedOnClassOrderCourseByTeacherController.execute( pageNo + "");

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
                    List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs;
                    try {
                        orderBuyCourseAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyCourseAsTeacherSTCDTOS"), type);
                    }catch (JSONException e){
                        //若没有课程，就是个空的
                        orderBuyCourseAsTeacherSTCDTOs = new ArrayList<>();
                    }
                    GlobalUtil.getInstance().setOrderBuyCourseAsTeacherSTCDTOs(orderBuyCourseAsTeacherSTCDTOs);
                    Log.i("malei", orderBuyCourseAsTeacherSTCDTOs.toString());

                    if (resultFlag.equals("success")) {
                        Toast.makeText(getContext(), "获取课程成功！", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerCourseListAdapter(getContext());
                                mCourseList.setAdapter(mAdapter);
                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
