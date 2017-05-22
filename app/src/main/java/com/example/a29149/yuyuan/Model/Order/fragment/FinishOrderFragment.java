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
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewFinishRewardAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewFinishCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.student.GetSpecificStateOrderController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 已完成订单的Fragment
 */

public class FinishOrderFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyCourse;
    private MyListView mReward;
    private MyListView mRecommand;
    private List<Map<String, Object>> courseNoPayList = new ArrayList<>();

    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表

    public ShapeLoadingDialog shapeLoadingDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_all_order, null);

        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        loadData();

        mBuyCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);


        mBuyCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei", "你点击了" + position);
            }
        });
        mReward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei", "你点击了" + position);
            }
        });
        mRecommand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei", "你点击了" + position);
            }
        });


        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
        mRecommand.setAdapter(myListViewRecommandAdapter);
        return view;
    }


    private void loadData() {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            requestData(1);
        }
    }

    //请求数据
    private void requestData(int pageNo) {
        new RequestNoPayCourseAction(pageNo).execute();
    }

    /**
     * 请求已完成的订单Action
     */
    public class RequestNoPayCourseAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public RequestNoPayCourseAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {

            return GetSpecificStateOrderController.execute(
                    OrderStateEnum.已评价.toString(),
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


                    Log.i("malei", orderBuyCourseAsStudentDTOs.toString());
                    if (resultFlag.equals("success")) {
                        Toast.makeText(mContext, "获取已完成订单成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                MyListViewFinishRewardAdapter myListViewFinishRewardAdapter = new MyListViewFinishRewardAdapter(mContext);
                                myListViewFinishRewardAdapter.setData(courseList);
                                mBuyCourse.setAdapter(myListViewFinishRewardAdapter);

                                MyListViewFinishCourseAdapter myListViewFinishCourseAdapter = new MyListViewFinishCourseAdapter(mContext);
                                myListViewFinishCourseAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewFinishCourseAdapter);

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
