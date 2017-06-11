package com.example.a29149.yuyuan.ModelStudent.Order.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.NoPayOrderAdapter;
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
 * 未付款的Fragment
 */

public class NoPayFragment extends AbstractFragment {

    //缓存页面布局
    private View view;

    private Context mContext;
    private MyListView mCourseListView;  //未付款课程
    private MyListView mRecommendListView;  //推荐
    private List<Map<String,Object>> courseNoPayList = new ArrayList<>();
    @Deprecated
    private List rewardList = new ArrayList();//悬赏列表
    private List<OrderBuyCourseAsStudentDTO> courseList = new ArrayList();//课程列表
    //Adapter
    private NoPayOrderAdapter noPayCourseAdapter;

    public  ShapeLoadingDialog shapeLoadingDialog; // 跳跳跳动画
    private int pageNo = 1;//页数
    private GetOrderBuyCourseAsTeacherByOrderStatesController getOrderBuyCourseAsTeacherByOrderStatesController; // 与后台交互的controller


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //如果已经有了这个碎片布局，就直接返回，不需要重新绘制
        if(view != null){
            return view;
        }

        mContext = getContext();
        //绑定UI
        view = inflater.inflate(R.layout.fragment_viewpager_nopay, null);

        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        //ListView绑定
        mCourseListView = (MyListView) view.findViewById(R.id.lv_buyCourse);
        //设置点击事件
        mCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到订单详情的意图
                Intent toOrderInfo = new Intent(mContext, OrderInfoActivity.class);
                //构造传输的数据
                Bundle bundle = new Bundle();
                bundle.putSerializable("DTO", courseList.get(position));
                toOrderInfo.putExtras(bundle);
                //开启活动
                startActivity( toOrderInfo );
            }
        });
        //推荐的ListView
        mRecommendListView = (MyListView) view.findViewById(R.id.lv_recommend);
        mRecommendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });


        //刚开始请求第一页
        pageNo = 1;
        requestData(pageNo);
        return view;
    }

    //请求数据
    private void requestData(int pageNo) {
        String userRole = GlobalUtil.getInstance().getUserRole();
        switch (userRole){
            case "student":
                new StudentRequestNoPayOrderAction(pageNo).execute();
                break;
            //其他身份，都是广义上的老师
            default:
                new TeacherRequestNoPayOrderAction(pageNo).execute();
                break;

        }
    }

    /**
     * 获取负责填充数据的Adapter
     * @return
     */
    public NoPayOrderAdapter getNoPayCourseAdapter() {
        return noPayCourseAdapter;
    }

    /**
     * 学生：请求已购买未付款的订单Action
     */
    public class StudentRequestNoPayOrderAction extends AsyncTask<String, Integer, String> {
        private int pageNo;
        public StudentRequestNoPayOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        /**
         * 发送请求
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            return GetSpecificStateOrderController.execute(
                    OrderStateEnum.未付款.toString(),
                    pageNo+""
            );
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String resultFlag = jsonObject.getString("result");
                    if (resultFlag.equals("success")) {
                        //请求成功
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                        }.getType();
                        //从JSON转换数据
                        List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = new Gson().fromJson(jsonObject.getString("orderList"), type);
                        rewardList.clear();
                        courseList.clear();
                        //根据不同类型，加到不同的list中
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

                        //构建未付款订单的adapter，并设置数据
                        noPayCourseAdapter  = new NoPayOrderAdapter(courseList, mContext);
                        //ListView设置adapter
                        mCourseListView.setAdapter(noPayCourseAdapter);

                        //TODO 推荐没有做
//                        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
//                        mRecommendListView.setAdapter(myListViewRecommandAdapter);


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
     * 老师：请求未付款的订单Action
     */
    public class TeacherRequestNoPayOrderAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public TeacherRequestNoPayOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        /**
         * 发送请求
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            getOrderBuyCourseAsTeacherByOrderStatesController = new GetOrderBuyCourseAsTeacherByOrderStatesController();
            getOrderBuyCourseAsTeacherByOrderStatesController.setPageNo(pageNo+"");
            getOrderBuyCourseAsTeacherByOrderStatesController.setOrderStateEnum(OrderStateEnum.未付款.toString());
            getOrderBuyCourseAsTeacherByOrderStatesController.execute();
            return  getOrderBuyCourseAsTeacherByOrderStatesController.getResult();

        }

        /**
         * 获取结果
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String resultFlag = getOrderBuyCourseAsTeacherByOrderStatesController.getResult();
            if (resultFlag.equals("success")) {
                try {
                    //获取所有我拥有的悬赏信息DTO
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = getOrderBuyCourseAsTeacherByOrderStatesController.getOrderList();
                    rewardList.clear();
                    courseList.clear();
                    //不同的类型，放置在不同的
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
                    //构建未付款订单的adapter，并设置数据
                    noPayCourseAdapter  = new NoPayOrderAdapter(courseList, mContext);
                    //ListView设置adapter
                    mCourseListView.setAdapter(noPayCourseAdapter);

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
