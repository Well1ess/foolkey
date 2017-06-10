package com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.course.reward.GetPopularController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//TODO 这里的数据传输依然依靠全局变量
public class RewardDiscoveryFragment extends AbstractFragment {
    private static final String TAG = "RewardDiscoveryFragment";

    //缓存当前view，方便再次切换到这个view时，不需要执行onCreateView方法
    private View view;

    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;
    private RewardListAdapter mListAdapter;


//    @ViewInject(R.id.content)
//    private RecyclerView recyclerView;
//    private RewardRecyclerViewAdapter recyclerViewAdapter;

    //记录请求的页数
    int pageNo = 1;

    public RewardDiscoveryFragment() {

    }

    public static RewardDiscoveryFragment newInstance() {
        RewardDiscoveryFragment fragment = new RewardDiscoveryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 判断view有没有创建，如果创建了，则不需要重新加载。
        if (view == null) {
            //把跳跳跳动画放在这里，目的是第一次创建view时，才会执行动画
            MainStudentActivity.shapeLoadingDialog.show();
            view = inflater.inflate(R.layout.fragment_reward_discovery, container, false);
            AnnotationUtil.injectViews(this, view);
            AnnotationUtil.setClickListener(this, view);

            //list初始化
            mListAdapter = new RewardListAdapter(getContext());
            //给listView设置adapter
            mRewardList.setAdapter(mListAdapter);
            //给viewList设置点击监听器
            mRewardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toCourseActivity = new Intent(getActivity(), RewardActivity.class);
                    toCourseActivity.putExtra("position", position);
                    startActivity(toCourseActivity);
                }
            });

            //设置列表动态加载
            mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
                @Override
                public void setLoad() {
                    //TODO:网络传输
                    GetReward getReward = new GetReward(++pageNo);
                    Log.d(TAG, "setLoad: pageNo : " + pageNo);
                    getReward.execute();
                }
            });

            //设置列表下拉时的刷新
            mSlideLayout.setRotateView(view.findViewById(R.id.iv_refresh));
            mSlideLayout.setOnSlideRefreshListener(
                    new SlideRefreshLayout.onSlideRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //TODO:网络通信
                            //获取主页的热门课程
                            if (MainStudentActivity.shapeLoadingDialog != null) {
                                MainStudentActivity.shapeLoadingDialog.show();
                            }
                            //由于是刷新，所以首先清空所有数据

                            pageNo = 1;
                            List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOs = new ArrayList<RewardWithStudentSTCDTO>();

                            GlobalUtil.getInstance().setRewardWithStudentSTCDTOs(rewardWithStudentSTCDTOs);
                            GetReward getReward = new GetReward(pageNo);
                            getReward.execute();
                        }
                    });

            //TODO：数据初始化
            GetReward getReward = new GetReward(1);
            getReward.execute();
        }
        return view;

    }


    /**
     * 刷新数据，主要用于删除悬赏时，列表里也要删除
     */
    public void updateRewardList() {
        //调用adapter的notifyDateChanged方法
        mListAdapter.updateList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //根据页码获取热门悬赏
    public class GetReward extends AsyncTask<String, Integer, String> {
        int pageNo;

        public GetReward(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {

            return GetPopularController.execute(pageNo + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    log.d(this, result);
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        log.d(this, jsonObject.getString("rewardCourseDTOS"));

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<RewardWithStudentSTCDTO>>() {
                        }.getType();
                        List<RewardWithStudentSTCDTO> courseStudentDTOS = new Gson().fromJson(jsonObject.getString("rewardCourseDTOS"), type);

                        //若>1则表示分页存取
                        if (pageNo == 1) {
                            GlobalUtil.getInstance().setRewardWithStudentSTCDTOs(courseStudentDTOS);
                        } else if (pageNo > 1) {
                            List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOs = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs();
                            rewardWithStudentSTCDTOs.addAll(courseStudentDTOS);
                            GlobalUtil.getInstance().setRewardWithStudentSTCDTOs(rewardWithStudentSTCDTOs);
                            mRewardList.onLoadFinish();
                        }
//                        //如果分页请求结果为空，说明没有请求到下一页，设置页码为当前页
//                        if (courseStudentDTOS.size() != 0) {
//                            setPageNo(pageNo + 1);
//                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //pageNo++;
                                mListAdapter.notifyDataSetChanged();
                                MainStudentActivity.shapeLoadingDialog.dismiss();

                            }
                        }, 1000);

                    } else {
                        Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
