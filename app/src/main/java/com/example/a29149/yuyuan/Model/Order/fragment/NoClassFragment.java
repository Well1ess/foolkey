package com.example.a29149.yuyuan.Model.Order.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoClassCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoClassRewardAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoCommentRewardAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoConmmentClassAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoStartCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.student.GetSpecifiStateOrderController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 未开始上课的Fragment
 */

public class NoClassFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyedCourse;
    private MyListView mRecommand;
    private MyListView mReward;
    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表

    public ShapeLoadingDialog shapeLoadingDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_noclass,null);


        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        loadData();

        mBuyedCourse = (MyListView) view.findViewById(R.id.lv_noStartCourse);
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);


        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
        mRecommand.setAdapter(myListViewRecommandAdapter);
        return view;
    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            requestData(1);
        }
    }

    //请求数据
    private void requestData(int pageNo) {
        new RequestNoClassCourseAction(pageNo).execute();
    }
    /**
     * 请求已付款未上课悬赏的订单Action
     */
    public class RequestNoClassCourseAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public RequestNoClassCourseAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {

            return GetSpecifiStateOrderController.execute(
                    OrderStateEnum.同意上课.toString(),
                    pageNo + ""
            );

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
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = new Gson().fromJson(jsonObject.getString("orderList"), type);
                    GlobalUtil.getInstance().setOrderBuyCourseAsStudentDTOs(orderBuyCourseAsStudentDTOs);

                    rewardList.clear();
                    courseList.clear();
                    for (OrderBuyCourseAsStudentDTO dto : orderBuyCourseAsStudentDTOs) {
                        switch (dto.getOrderDTO().getCourseTypeEnum()) {
                            case 学生悬赏: {
                                rewardList.add(dto);
                            }
                            break;
                            case 老师课程: {
                                courseList.add(dto);
                            }
                            break;
                        }
                    }


                    Log.i("malei",orderBuyCourseAsStudentDTOs.toString());
                    if (resultFlag.equals("success")) {
                        Toast.makeText(mContext, "获取未上课成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MyListViewNoClassCourseAdapter myListViewNoClassCourseAdapter = new MyListViewNoClassCourseAdapter(mContext);
                                mBuyedCourse.setAdapter(myListViewNoClassCourseAdapter);
                                myListViewNoClassCourseAdapter.setData(courseList);


                                MyListViewNoClassRewardAdapter myListViewNoClassRewardAdapter = new MyListViewNoClassRewardAdapter(mContext);
                                myListViewNoClassRewardAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewNoClassRewardAdapter);

                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
