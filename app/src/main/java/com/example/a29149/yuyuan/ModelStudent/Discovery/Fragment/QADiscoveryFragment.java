package com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment;


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

import com.example.a29149.yuyuan.DTO.CourseTeacherDTO;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.QAActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.QAListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.course.course.GetPopularController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;


public class QADiscoveryFragment extends Fragment {

    private View view;

    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mDynamicListView;
    private QAListAdapter mQAListAdapter;

    //记录请求的页数
    int pageNo = 1;

    public QADiscoveryFragment() {

    }


    public static QADiscoveryFragment newInstance() {
        QADiscoveryFragment fragment = new QADiscoveryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null) {

            view = inflater.inflate(R.layout.fragment_qadiscovery, container, false);
            AnnotationUtil.setClickListener(this, view);
            AnnotationUtil.injectViews(this, view);

            //初始化listview
            mQAListAdapter = new QAListAdapter(getContext());
            mDynamicListView.setAdapter(mQAListAdapter);
            mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toCourseActivity = new Intent(getActivity(), QAActivity.class);
                    startActivity(toCourseActivity);
                }
            });

            //设置动态加载
            mDynamicListView.setOnLoadingListener(new DynamicListView.onLoadingListener() {
                @Override
                public void setLoad() {

                    mDynamicListView.onLoadFinish();
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
                        }
                    });
        }
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

    public class GetQA extends AsyncTask<String, Integer, String> {

        public GetQA() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return GetPopularController.execute(
                    pageNo + "",
                    params[0]
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

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<CourseTeacherDTO>>() {
                        }.getType();
                        List<CourseTeacherDTO> courseTeacherDTOs = new Gson().fromJson(jsonObject.getString("courseTeacherDTOS"), type);

                        //若>1则表示分页存取
                        if (pageNo == 1) {
                            //GlobalUtil.getInstance().setCourseTeacherDTOs(courseTeacherDTOs);
                        } else if (pageNo > 1) {
                            //GlobalUtil.getInstance().getCourseTeacherDTOs().addAll(courseTeacherDTOs);
                            mDynamicListView.onLoadFinish();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pageNo++;
                                mQAListAdapter.notifyDataSetChanged();
                                MainStudentActivity.shapeLoadingDialog.dismiss();

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
