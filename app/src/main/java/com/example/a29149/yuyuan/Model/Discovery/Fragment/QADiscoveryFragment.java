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

import com.example.a29149.yuyuan.DTO.CourseTeacherDTO;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Model.Discovery.Activity.QAActivity;
import com.example.a29149.yuyuan.Model.Discovery.Adapter.QAListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;


public class QADiscoveryFragment extends Fragment {

    //下拉刷新的Layout
    @ViewInject(R.id.slide_layout)
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

        View view = inflater.inflate(R.layout.fragment_qadiscovery, container, false);
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
                    }
                });

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
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pageNo", pageNo + "");
                jsonObject.put("technicTagEnum", params[0]);

                java.net.URL url = new java.net.URL(URL.getGetHotCourseURL(jsonObject.toString()));
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getGetHotCourseURL(jsonObject.toString()));
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("GET");
                con.setRequestProperty("contentType", "GBK");


                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return sb.toString();
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
                            GlobalUtil.getInstance().setCourseTeacherDTOs(courseTeacherDTOs);
                        } else if (pageNo > 1) {
                            GlobalUtil.getInstance().getCourseTeacherDTOs().addAll(courseTeacherDTOs);
                            mDynamicListView.onLoadFinish();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pageNo++;
                                mQAListAdapter.notifyDataSetChanged();
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
