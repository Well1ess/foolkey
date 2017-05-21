package com.example.a29149.yuyuan.Model.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseStudentPopularDTO;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Model.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.Model.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.course.reward.GetPopularRewardController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RewardDiscoveryFragment extends Fragment {

    //下拉刷新的Layout
    @ViewInject(R.id.slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;
    private RewardListAdapter mListAdapter;

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
        View view = inflater.inflate(R.layout.fragment_reward_discovery, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        //list初始化
        mListAdapter = new RewardListAdapter(getContext());
        mRewardList.setAdapter(mListAdapter);
        mRewardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toCourseActivity = new Intent(getActivity(), RewardActivity.class);
                toCourseActivity.putExtra("position",position);
                startActivity(toCourseActivity);
            }
        });

        //设置列表动态加载
        mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                //TODO:网络传输
                GetReward getReward = new GetReward(++pageNo);
                getReward.execute();
            }
        });

        //设置列表下拉时的刷新
        mSlideLayout.setRotateView(view.findViewById(R.id.refresh));
        mSlideLayout.setOnSlideRefreshListener(
                new SlideRefreshLayout.onSlideRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //TODO:网络通信
                        //获取主页的热门课程
                        if (MainActivity.shapeLoadingDialog != null) {
                            MainActivity.shapeLoadingDialog.show();
                        }
                        //由于是刷新，所以首先清空所有数据

                        pageNo = 1;
                        List<CourseStudentPopularDTO> courseStudentPopularDTOs = new ArrayList<CourseStudentPopularDTO>();

                        GlobalUtil.getInstance().setCourseStudentPopularDTOs(courseStudentPopularDTOs);
                        GetReward getReward = new GetReward(pageNo);
                        getReward.execute();
                    }
                });

        //TODO：数据初始化
        GetReward getReward = new GetReward(1);
        getReward.execute();

        return view;
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

            return GetPopularRewardController.execute( pageNo + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        log.d(this, jsonObject.getString("rewardCourseDTOS"));

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<CourseStudentPopularDTO>>() {
                        }.getType();
                        List<CourseStudentPopularDTO> courseStudentDTOS = new Gson().fromJson(jsonObject.getString("rewardCourseDTOS"), type);

                        //若>1则表示分页存取
                        if (pageNo == 1) {
                            GlobalUtil.getInstance().setCourseStudentPopularDTOs(courseStudentDTOS);
                        } else if (pageNo > 1) {
                            GlobalUtil.getInstance().getCourseStudentPopularDTOs().addAll(courseStudentDTOS);
                            mRewardList.onLoadFinish();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //pageNo++;
                                mListAdapter.notifyDataSetChanged();
                                MainActivity.shapeLoadingDialog.dismiss();

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


}
