package com.example.a29149.yuyuan.ModelStudent.Order.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.a29149.yuyuan.ModelStudent.Order.activity.OrderInfoStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyListViewFinishCourseAdapter;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyListViewFinishRewardAdapter;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.ModelStudent.Order.view.MyListView;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.order.student.GetSpecificStateOrderController;
import com.example.a29149.yuyuan.controller.order.teacher.home.GetOrderBuyCourseAsTeacherByOrderStatesController;
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
    private MyListView mCourse;
    private MyListView mReward;
    private MyListView mRecommand;
    private List<Map<String, Object>> courseNoPayList = new ArrayList<>();

    private List<OrderBuyCourseAsStudentDTO> rewardList = new ArrayList();//悬赏列表
    private List<OrderBuyCourseAsStudentDTO> courseList = new ArrayList();//课程列表

    public ShapeLoadingDialog shapeLoadingDialog;

    private int pageNo = 1;//页数
    private GetOrderBuyCourseAsTeacherByOrderStatesController getOrderBuyCourseAsTeacherByOrderStatesController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_all_order, null);

        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);



        mCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);

        //刚开始请求第一页
        pageNo = 1;
        loadData(pageNo);

        //课程
        /**
         * 这里注意，此时的以老师身份过去，依然是以学生视角来展示的
         * FIXME
         * TODO
         */
        //设定监听事件
        mCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击课程，进入课程详情
                //获取他点击的课程
                OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = courseList.get(position);
                //这里过去的依然是悬赏的订单界面
                Intent toOrderInfo = new Intent(mContext, OrderInfoStudentActivity.class);
                //在bundle里直接放置DTO
                Bundle bundle = new Bundle();
                bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
                toOrderInfo.putExtras(bundle);
                //跳转到订单详情的Activity
                startActivity(toOrderInfo);
            }
        });
        mReward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击悬赏，进入悬赏详情
                Log.i("malei", "你点击了" + position);
                //获取他点击的悬赏
                OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = rewardList.get(position);
                //新建意图
                Intent toOrderInfo = new Intent(mContext, OrderInfoStudentActivity.class);
                //在bundle里直接放置DTO
                Bundle bundle = new Bundle();
                bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
                toOrderInfo.putExtras(bundle);
                //跳转到订单详情的Activity
                startActivity(toOrderInfo);
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

/*    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        shapeLoadingDialog.show();
        loadData(pageNo);
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && GlobalUtil.getInstance().getFragmentFresh()) {
            //相当于Fragment的onResume
            //在这里处理加载数据等操作
            //用全局的方式实现回调
            shapeLoadingDialog.show();
            pageNo = 1;
            loadData(pageNo);
            GlobalUtil.getInstance().setFragmentFresh(false);
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (GlobalUtil.getInstance().getFragmentFresh()){

        }else {
            //不刷新页面，不执行
        }
    }


    private void loadData(int pageNo) {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            requestData(pageNo);
        }
    }

    //请求数据
    private void requestData(int pageNo) {
        String userRole = GlobalUtil.getInstance().getUserRole();
        switch (userRole){
            case "student":
                new StudentRequestFinishOrderAction(pageNo).execute();
                break;
            //其他身份，都是广义上的老师
            default:
                new TeacherRequestFinishOrderAction(pageNo).execute();
                break;
        }
    }

    /**
     * 学生：请求已完成的订单Action
     */
    public class StudentRequestFinishOrderAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public StudentRequestFinishOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            shapeLoadingDialog.show();
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
            //log.d(this, result);
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

                    GlobalUtil.getInstance().setOrderRewardList(rewardList);
                    GlobalUtil.getInstance().setOrderCourseList(courseList);


                    Log.i("malei", orderBuyCourseAsStudentDTOs.toString());
                    if (resultFlag.equals("success")) {
//                        Toast.makeText(mContext, "获取已完成订单成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                MyListViewFinishRewardAdapter myListViewFinishRewardAdapter = new MyListViewFinishRewardAdapter(mContext);
                                myListViewFinishRewardAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewFinishRewardAdapter);

                                MyListViewFinishCourseAdapter myListViewFinishCourseAdapter = new MyListViewFinishCourseAdapter(mContext);
                                myListViewFinishCourseAdapter.setData(courseList);
                                mCourse.setAdapter(myListViewFinishCourseAdapter);



                            }
                        }, 1000);
                    }
                } catch (Exception e) {
//                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
                finally {
                    shapeLoadingDialog.dismiss();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 老师：请求已完成的订单Action
     */
    public class TeacherRequestFinishOrderAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public TeacherRequestFinishOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            shapeLoadingDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            getOrderBuyCourseAsTeacherByOrderStatesController = new GetOrderBuyCourseAsTeacherByOrderStatesController();
            getOrderBuyCourseAsTeacherByOrderStatesController.setPageNo(pageNo+"");
            getOrderBuyCourseAsTeacherByOrderStatesController.setOrderStateEnum(OrderStateEnum.已评价.toString());
            getOrderBuyCourseAsTeacherByOrderStatesController.execute();
            return  getOrderBuyCourseAsTeacherByOrderStatesController.getResult();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //log.d(this, result);
            String resultFlag = getOrderBuyCourseAsTeacherByOrderStatesController.getResult();
            if (resultFlag.equals("success")) {
                try {
                    //存储所有我拥有的悬赏信息DTO
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = getOrderBuyCourseAsTeacherByOrderStatesController.getOrderList();
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

                    GlobalUtil.getInstance().setOrderRewardList(rewardList);
                    GlobalUtil.getInstance().setOrderCourseList(courseList);

                    Log.i("malei",orderBuyCourseAsStudentDTOs.toString());
                    if (resultFlag.equals("success")) {
//                        Toast.makeText(mContext, "获取已完成订单成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                MyListViewFinishRewardAdapter myListViewFinishRewardAdapter = new MyListViewFinishRewardAdapter(mContext);
                                myListViewFinishRewardAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewFinishRewardAdapter);

                                MyListViewFinishCourseAdapter myListViewFinishCourseAdapter = new MyListViewFinishCourseAdapter(mContext);
                                myListViewFinishCourseAdapter.setData(courseList);
                                mCourse.setAdapter(myListViewFinishCourseAdapter);

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
//                    Toast.makeText(mContext, "返回结果为fai", Toast.LENGTH_SHORT).show();
                }
                finally {
                    shapeLoadingDialog.dismiss();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
                shapeLoadingDialog.dismiss();
            }


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
