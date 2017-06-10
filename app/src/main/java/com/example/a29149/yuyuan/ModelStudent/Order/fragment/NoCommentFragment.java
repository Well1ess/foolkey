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

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.ModelStudent.Order.activity.OrderInfoActivity;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyListViewNoCommentRewardAdapter;
import com.example.a29149.yuyuan.ModelStudent.Order.adapter.MyListViewNoConmmentClassAdapter;
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
    //Adapter
    private MyListViewNoCommentRewardAdapter myListViewNoCommentRewardAdapter; //悬赏的adapter
    private MyListViewNoConmmentClassAdapter myListViewNoConmmentClassAdapter; //课程的adapter

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
        loadData(pageNo);
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
                Log.i("malei","你点击了"+position);
            }
        });


        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);
        mRecommend.setAdapter(myListViewRecommandAdapter);
        return view;
    }

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
                new StudentRequestNoCommentOrderAction(pageNo).execute();
                break;
            //其他身份，都是广义上的老师
            default:
                new TeacherRequestNoCommentOrderAction(pageNo).execute();
                break;
        }
    }

    /**
     * 获取悬赏adapter的数据源
     * 如果更新悬赏订单，要改动这里的list
     *
     * @return reward list
     */
    public List<OrderBuyCourseAsStudentDTO> getRewardList() {
        return rewardList;
    }

    /**
     * 获取课程adapter的数据源
     * 如果更新课程订单，要改动这里的list
     *
     * @return course list
     */
    public List<OrderBuyCourseAsStudentDTO> getCourseList() {
        //TODO
        return courseList;
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
     * 从悬赏的数据源中，根据id删除一个订单
     * 如果删除成功了，会自动通知adapter更新数据
     *
     * @param id the id
     *
     * @return boolean
     */
    public boolean removeRewardById(long id){
        boolean flag = removeOrderById(getRewardList(), id);
        if( flag == true ){
            myListViewNoCommentRewardAdapter.notifyDataSetChanged();
        }
        return flag;
    }

    /**
     * 从课程的数据源中，根据id删除一个订单
     * 如果删除成功了，会自动通知adapter更新数据
     *
     * @param id the id
     *
     * @return boolean
     */
    public boolean removeCourseById(long id){
        boolean flag = removeOrderById(getCourseList(), id);
        if( flag == true ){
            myListViewNoConmmentClassAdapter.notifyDataSetChanged();
        }
        return flag;
    }

    /**
     * 通知adapter刷新数据
     */
    public void updateOrderList(){
        myListViewNoCommentRewardAdapter.notifyDataSetChanged();
        myListViewNoConmmentClassAdapter.notifyDataSetChanged();
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
        protected void onPreExecute() {
            super.onPreExecute();
//            shapeLoadingDialog.show();
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
                    //存储所有我拥有的悬赏信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsStudentDTO> orderBuyCourseAsStudentDTOs = new Gson().fromJson(jsonObject.getString("orderList"), type);
                    GlobalUtil.getInstance().setOrderBuyCourseAsStudentDTOs(orderBuyCourseAsStudentDTOs);
                    //Log.i("malei", "commentRewardActivity="+GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().toString());

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
//                        Toast.makeText(mContext, "获取成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                myListViewNoConmmentClassAdapter = new MyListViewNoConmmentClassAdapter(mContext);
                                myListViewNoConmmentClassAdapter.setData(courseList);
                                mCourse.setAdapter(myListViewNoConmmentClassAdapter);


                                myListViewNoCommentRewardAdapter = new MyListViewNoCommentRewardAdapter(mContext);
                                myListViewNoCommentRewardAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewNoCommentRewardAdapter);



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
        protected void onPreExecute() {
            super.onPreExecute();
//            shapeLoadingDialog.show();
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
                    GlobalUtil.getInstance().setOrderBuyCourseAsStudentDTOs(orderBuyCourseAsStudentDTOs);

                    rewardList.clear();
                    courseList.clear();
                    System.out.println(this.getClass() + "   \n " + orderBuyCourseAsStudentDTOs);
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
//                        Toast.makeText(mContext, "获取未上课成功！", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                myListViewNoConmmentClassAdapter = new MyListViewNoConmmentClassAdapter(mContext);
                                mCourse.setAdapter(myListViewNoConmmentClassAdapter);
                                myListViewNoConmmentClassAdapter.setData(courseList);


                                MyListViewNoCommentRewardAdapter myListViewNoCommentRewardAdapter = new MyListViewNoCommentRewardAdapter(mContext);
                                myListViewNoCommentRewardAdapter.setData(rewardList);
                                mReward.setAdapter(myListViewNoCommentRewardAdapter);

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
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
