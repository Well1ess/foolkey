package com.example.a29149.yuyuan.Model.Index.Fragment;

import android.app.ActivityOptions;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseTeacherPopularDTO;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Model.Index.Adapter.IndexContentAdapter;
import com.example.a29149.yuyuan.Model.Index.Course.CourseActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.course.course.GetPopularController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;


public class ClassesFragment extends Fragment {

    @ViewInject(R.id.slide_layout)
    private SlideRefreshLayout mSlideLayout;

    @ViewInject(R.id.content)
    private DynamicListView mDynamicList;
    private IndexContentAdapter mContentAdapter;

    //空列表显示
    @ViewInject(R.id.empty)
    private ImageView iv_empty;

    //暂存当前的类别
    TechnicTagEnum mTechnicTagEnum;

    private int pageNo = 1;

    public ClassesFragment() {
    }

    public static ClassesFragment newInstance(int index) {
        ClassesFragment fragment = new ClassesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        AnnotationUtil.setClickListener(this, view);
        AnnotationUtil.injectViews(this, view);
        //存储当前标签
        GlobalUtil.getInstance().setTechnicTagEnum(mTechnicTagEnum);

        mContentAdapter = new IndexContentAdapter(getContext(),mTechnicTagEnum);
        mDynamicList.setAdapter(mContentAdapter);

        //设置课程推荐列表的监听
        setContentListListener();

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
                        GlobalUtil.getInstance().getContent().clear();
                        GetHotCourseAction action = new GetHotCourseAction(pageNo);
                        action.execute(mTechnicTagEnum.toString());
                    }
                });

        //设置列表滑动到底部时的刷新
        mDynamicList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                //TODO:网络通信
                if (MainActivity.shapeLoadingDialog != null) {
                    MainActivity.shapeLoadingDialog.show();
                }
                //不用清空数据
                pageNo++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetHotCourseAction action = new GetHotCourseAction(pageNo);
                        action.execute(mTechnicTagEnum.toString());
                    }
                }, 200);
            }
        });

        return view;
    }

    public void resetContent(TechnicTagEnum technicTagEnum) {
        mTechnicTagEnum = technicTagEnum;
//        //TODO：网络通信
//        //获取主页的热门课程
//        if (MainActivity.shapeLoadingDialog != null) {
//            MainActivity.shapeLoadingDialog.show();
//        }
        //首先清空数据
        GlobalUtil.getInstance().getContent().clear();


        //请求数据
        pageNo = 1;
        GetHotCourseAction action = new GetHotCourseAction(pageNo);
        action.execute(technicTagEnum.toString());
    }

    private void setContentListListener() {
        mDynamicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toCourseActivity = new Intent(getActivity(), CourseActivity.class);
                toCourseActivity.putExtra("position", position);
                startActivity(toCourseActivity, ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(), view, "shareContent").toBundle());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GetHotCourseAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public GetHotCourseAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected String doInBackground(String... params) {

            return GetPopularController.execute(
                    pageNo + "",
                    params[0] + ""
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

                    if (resultFlag.equals("success")) {

                        log.d(this, jsonObject.getString("courseTeacherDTOS"));

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<CourseTeacherPopularDTO>>() {
                        }.getType();
                        List<CourseTeacherPopularDTO> courseTeacherDTOs = new Gson().fromJson(jsonObject.getString("courseTeacherDTOS"), type);

                        //若>1则表示分页存取
                        if (pageNo == 1) {
                            GlobalUtil.getInstance().setCourseTeacherPopularDTOs(courseTeacherDTOs,mTechnicTagEnum);
                            if (courseTeacherDTOs.size()==0)
                                iv_empty.setVisibility(View.VISIBLE);
                        } else if (pageNo > 1) {
                            GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).addAll(courseTeacherDTOs);
                            mDynamicList.onLoadFinish();
                        }

                        log.d(ClassesFragment.this, GlobalUtil.getInstance().getCourseTeacherPopularDTOs(mTechnicTagEnum).size());

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //pageNo++;
                                mContentAdapter.notifyDataSetChanged();
                                MainActivity.shapeLoadingDialog.dismiss();

                            }
                        }, 1000);

                    } else {
                        Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
