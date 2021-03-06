package com.example.a29149.yuyuan.ModelStudent.Order.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.OrderInfoActivity;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.NoClassOrderAdapter;
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

/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 未开始上课的Fragment
 */

public class NoClassFragment extends AbstractFragment {

    //缓存页面布局
    private View view;

    private Context mContext;
    private MyListView mCourse; //未上课的课程
    private MyListView mRecommend; //推荐
    private MyListView mReward; // 未上课的悬赏
    private List<OrderBuyCourseAsStudentDTO> rewardList = new ArrayList();//悬赏列表
    private List<OrderBuyCourseAsStudentDTO> courseList = new ArrayList();//课程列表

    private Object object;
    public ShapeLoadingDialog shapeLoadingDialog;
    private int pageNo = 1;//页数

    //未上课悬赏的Adapter
    private NoClassOrderAdapter rewardAdapter;
    //未上课课程的Adapter
    private NoClassOrderAdapter courseAdapter;

    private GetOrderBuyCourseAsTeacherByOrderStatesController getOrderBuyCourseAsTeacherByOrderStatesController; // 作为老师获取课程订单的controller

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //如果已经有了这个碎片布局，就直接返回，不需要重新绘制
        if(view != null){
            return view;
        }

        mContext = getContext();
        // 绑定UI
        view = inflater.inflate(R.layout.fragment_viewpager_noclass,null);
        //加载动画
        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        //刚开始请求第一页
        pageNo = 1;
        //加载数据
        loadData(pageNo);
        //绑定UI
        mCourse = (MyListView) view.findViewById(R.id.lv_noStartCourse);
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mRecommend = (MyListView) view.findViewById(R.id.lv_recommend);

        //设定监听事件
        mCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击课程，进入课程详情
                //获取他点击的课程
                OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = courseList.get(position);
                //这里过去的依然是悬赏的订单界面
                Intent toOrderInfo = new Intent(mContext, OrderInfoActivity.class);
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
                //获取他点击的悬赏
                OrderBuyCourseAsStudentDTO orderBuyCourseAsStudentDTO = rewardList.get(position);
                //新建意图
                Intent toOrderInfo = new Intent(mContext, OrderInfoActivity.class);
                //在bundle里直接放置DTO
                Bundle bundle = new Bundle();
                bundle.putSerializable("DTO", orderBuyCourseAsStudentDTO);
                toOrderInfo.putExtras(bundle);
                //跳转到订单详情的Activity
                startActivity(toOrderInfo);
            }
        });
        mRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击推荐
                //TODO 推荐没做
            }
        });
        return view;
    }
    /**
     * 加载数据
     * @param pageNo
     */
    private void loadData(int pageNo) {
        requestData(pageNo);
    }

    //请求数据
    private void requestData(int pageNo) {
        String userRole = GlobalUtil.getInstance().getUserRole();
        switch (userRole){
            case "student":
                new StudentRequestNoClassOrderAction(pageNo).execute();
                break;
            //其他身份，都是广义上的老师
            default:
                new TeacherRequestNoClassOrderAction(pageNo).execute();
                break;
        }
    }
    /**
     * 学生：请求已付款未上课的订单Action
     */
    public class StudentRequestNoClassOrderAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public StudentRequestNoClassOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {
            return GetSpecificStateOrderController.execute(
                    OrderStateEnum.同意上课.toString(),
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

                    if (resultFlag.equals("success")) {
                        //存储所有我拥有的悬赏信息DTO
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                        }.getType();
                        List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = new Gson().fromJson(jsonObject.getString("orderList"), type);
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

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //未上课课程绑定Adapter
                                courseAdapter = new NoClassOrderAdapter(courseList, mContext);
                                mCourse.setAdapter( courseAdapter );
                                //未上课悬赏绑定Adapter
                                rewardAdapter = new NoClassOrderAdapter(rewardList, mContext);
                                mReward.setAdapter( rewardAdapter );

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    /**
     * 老师：请求已付款未上课的订单Action
     */
    public class TeacherRequestNoClassOrderAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public TeacherRequestNoClassOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }
        @Override
        protected String doInBackground(String... params) {
            getOrderBuyCourseAsTeacherByOrderStatesController = new GetOrderBuyCourseAsTeacherByOrderStatesController();
            getOrderBuyCourseAsTeacherByOrderStatesController.setPageNo(pageNo+"");
            getOrderBuyCourseAsTeacherByOrderStatesController.setOrderStateEnum(OrderStateEnum.同意上课.toString());
            getOrderBuyCourseAsTeacherByOrderStatesController.execute();
            return  getOrderBuyCourseAsTeacherByOrderStatesController.getResult();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String resultFlag = getOrderBuyCourseAsTeacherByOrderStatesController.getResult();
            if (resultFlag.equals("success")) {
                try {
                    //存储所有我拥有的悬赏信息DTO
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = getOrderBuyCourseAsTeacherByOrderStatesController.getOrderList();
                    Log.i("malei",getClass() + " 294 rewardList=  "+getOrderBuyCourseAsTeacherByOrderStatesController.getOrderList().toString());
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //未上课课程绑定Adapter
                            courseAdapter = new NoClassOrderAdapter(courseList, mContext);
                            mCourse.setAdapter( courseAdapter );
                            //未上课悬赏绑定Adapter
                            rewardAdapter = new NoClassOrderAdapter(rewardList, mContext);
                            mReward.setAdapter( rewardAdapter );

                        }
                    }, 1000);

                } catch (Exception e) {
                    e.printStackTrace();
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
