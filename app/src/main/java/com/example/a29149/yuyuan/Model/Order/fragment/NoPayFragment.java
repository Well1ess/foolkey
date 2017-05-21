package com.example.a29149.yuyuan.Model.Order.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoPayClassAdapter;
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
import java.util.Map;


/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 未付款的Fragment
 */

public class NoPayFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyCourse;
    private MyListView mRecommand;
    private List<Map<String,Object>> courseNoPayList = new ArrayList<>();
    private List rewardList = new ArrayList();//悬赏列表
    private List courseList = new ArrayList();//课程列表

    public  ShapeLoadingDialog shapeLoadingDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_nopay, null);

        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        mBuyCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mBuyCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });

        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);
        mRecommand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });


        loadData();


        return view;
    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            requestData();
        }
    }

    //请求数据
    private void requestData() {
        new RequestNoPayCourseAction().execute();
    }
    /**
     * 请求以购买未付款的订单Action
     */
    public class RequestNoPayCourseAction extends AsyncTask<String, Integer, String> {

        public RequestNoPayCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println();
            System.out.println(this.getClass() + "这里的页码是写死的\n");
            return GetSpecifiStateOrderController.execute(
                    OrderStateEnum.未付款.toString(),
                    "1"
            );
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);


                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = new Gson().fromJson(jsonObject.getString("orderList"), type);
                    //存储学生信息DTO
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

                    String resultFlag = jsonObject.getString("result");
                    Log.i("malei","NoPay    "+resultFlag);
                    if (resultFlag.equals("success")) {
                        Toast.makeText(mContext, "获取未付款订单成功！", Toast.LENGTH_SHORT).show();
                        MyListViewNoPayClassAdapter myListViewNoPayClassAdapter = new MyListViewNoPayClassAdapter(mContext);
                        myListViewNoPayClassAdapter.setData(courseList);

                        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
                        mBuyCourse.setAdapter(myListViewNoPayClassAdapter);

                        mRecommand.setAdapter(myListViewRecommandAdapter);

                        shapeLoadingDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                    Log.i("malei","NoPay fail");
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
