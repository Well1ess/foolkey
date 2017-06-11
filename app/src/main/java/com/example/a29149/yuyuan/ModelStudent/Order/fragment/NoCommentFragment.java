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
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.NoJudgedOrderAdapter;
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
 * 未评价订单的Fragment
 */
public class NoCommentFragment extends AbstractFragment {
    private static final String TAG = "NoCommentFragment";

    //缓存页面布局
    private View view;

    private Context mContext;
    private MyListView mCourse; //课程
    private MyListView mReward; //悬赏
    private MyListView mRecommend; // 推荐
    private List<OrderBuyCourseAsStudentDTO> rewardList = new ArrayList();//悬赏列表
    private List<OrderBuyCourseAsStudentDTO> courseList = new ArrayList();//课程列表

    //悬赏的Adapter
    private NoJudgedOrderAdapter rewardAdapter;
    //课程的Adapter
    private NoJudgedOrderAdapter courseAdapter;

    /**
     * The Shape loading dialog.
     */
    public  ShapeLoadingDialog shapeLoadingDialog;
    private int pageNo = 1;//页数
    //老师身份，根据状态获取订单的controller
    private GetOrderBuyCourseAsTeacherByOrderStatesController getOrderBuyCourseAsTeacherByOrderStatesController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //如果已经有了这个碎片布局，就直接返回，不需要重新绘制
        if(view != null){
            return view;
        }

        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_viewpager_nocomment, null);
        //加载动画
        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);


        //刚开始请求第一页
        pageNo = 1;
        //请求数据
        requestData(pageNo);
        //TODO 这里立即评价还没有解决
        //课程的ListView,绑定UI
        mCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        //设置监听事件
        mCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 课程的立即评价还没有解决
                //查看某个具体的课程订单
                Intent toOrderInfo = new Intent(mContext, OrderInfoActivity.class);
                //新建Bundle，放置具体的DTO
                Bundle bundle = new Bundle();
                //从类变量的List里获取具体的DTO
                bundle.putSerializable("DTO", courseList.get(position));
                //将Bundle放置在intent里，并开启新Activity
                toOrderInfo.putExtras( bundle );
                startActivity( toOrderInfo );
                Log.i(TAG, "onItemClick: 91 " + position);
            }
        });
        //悬赏的ListView
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mReward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //未评价的adapter
                Intent toOrderInfo = new Intent(mContext, OrderInfoActivity.class);
                //新建Bundle，放置具体的DTO
                Bundle bundle = new Bundle();
                //从类变量的List里获取具体的DTO
                bundle.putSerializable("DTO", rewardList.get(position));
                //将Bundle放置在intent里，并开启新Activity
                toOrderInfo.putExtras( bundle );
                startActivity( toOrderInfo );

            }
        });
        mRecommend = (MyListView) view.findViewById(R.id.lv_recommend);
        mRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 推荐没做
            }
        });

        return view;
    }


    /**
     * 根据不同的身份，请求数据
     * @param pageNo
     */
    private void requestData(int pageNo) {
        String userRole = GlobalUtil.getInstance().getUserRole();
        switch (userRole){
            case "student":
                new StudentRequestNoCommentOrderAction(pageNo).execute();
                break;
            //其他身份，都是广义上的老师
            default:
                new TeacherRequestNoCommentOrderAction(pageNo).execute();
                break;
        }
    }

    /**
     * 根据order的Id，从list里移除一个dto
     * @return
     */
    private boolean removeOrderById(List <OrderBuyCourseAsStudentDTO> target, long id){
        List <OrderBuyCourseAsStudentDTO> list = target;
        for (OrderBuyCourseAsStudentDTO dto : list){
            if ( dto.getOrderDTO().getId() == id ){
                list.remove(dto);
                return true;
            }
        }
        return false;
    }

    /**
     * 通知adapter刷新数据
     */
    public void updateOrderList(){
        rewardAdapter.notifyDataSetChanged();
        courseAdapter.notifyDataSetChanged();
    }

    /**
     * 获取悬赏的Adapter
     * 如果要操纵数据源，通过Adapter来操作
     * @return
     */
    public NoJudgedOrderAdapter getRewardAdapter() {
        return rewardAdapter;
    }

    /**
     * 获取课程的Adapter
     * 如果要操纵数据源，通过Adapter来操作
     * @return
     */
    public NoJudgedOrderAdapter getCourseAdapter() {
        return courseAdapter;
    }

    /**
     * 学生：请求已上课未评价的订单Action
     */
    public class StudentRequestNoCommentOrderAction extends AsyncTask<String, Integer, String> {

        /**
         * The Page no.
         */
        int pageNo;

        /**
         * Instantiates a new Student request no comment order action.
         *
         * @param pageNo the page no
         */
        public StudentRequestNoCommentOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {
            return GetSpecificStateOrderController.execute(
                    OrderStateEnum.结束上课.toString(),
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
                    //如果成功
                    if (resultFlag.equals("success")) {
                        //获取所有我拥有的悬赏信息DTO
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
                                //绑定未评价课程的adapter
                                courseAdapter = new NoJudgedOrderAdapter( courseList, mContext );
                                mCourse.setAdapter( courseAdapter );

                                //绑定未评价悬赏的adapter
                                rewardAdapter = new NoJudgedOrderAdapter( rewardList, mContext );
                                mReward.setAdapter( rewardAdapter );
                            }
                        }, 1000);
                    }else {
                        //获取失败
                        Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
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
     * 老师：请求未评论的订单Action
     */
    public class TeacherRequestNoCommentOrderAction extends AsyncTask<String, Integer, String> {

        /**
         * The Page no.
         */
        int pageNo;

        /**
         * Instantiates a new Teacher request no comment order action.
         *
         * @param pageNo the page no
         */
        public TeacherRequestNoCommentOrderAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {

            getOrderBuyCourseAsTeacherByOrderStatesController = new GetOrderBuyCourseAsTeacherByOrderStatesController();
            getOrderBuyCourseAsTeacherByOrderStatesController.setPageNo(pageNo+"");
            getOrderBuyCourseAsTeacherByOrderStatesController.setOrderStateEnum(OrderStateEnum.结束上课.toString());
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
                    courseAdapter = new NoJudgedOrderAdapter( courseList, mContext );
                    mCourse.setAdapter( courseAdapter );

                    //绑定未评价悬赏的adapter
                    rewardAdapter = new NoJudgedOrderAdapter( rewardList, mContext );
                    mReward.setAdapter( rewardAdapter );
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
