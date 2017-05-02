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
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Model.Index.Adapter.IndexContentAdapter;
import com.example.a29149.yuyuan.Model.Index.Course.CourseActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


public class ClassesFragment extends Fragment {

    @ViewInject(R.id.slide_layout)
    private SlideRefreshLayout mSlideLayout;

    @ViewInject(R.id.content)
    private DynamicListView mDynamicList;
    private IndexContentAdapter mContentAdapter;

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

        mContentAdapter = new IndexContentAdapter(getContext());
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
                        if (MainActivity.shapeLoadingDialog != null)
                        {
                            MainActivity.shapeLoadingDialog.show();
                        }
                        //由于是刷新，所以首先清空所有数据
                        GlobalUtil.getInstance().getContent().clear();
                        mContentAdapter.notifyDataSetChanged();
                        GetHotCourseAction action = new GetHotCourseAction();
                        action.execute();
                    }
                });

        //设置列表滑动到底部时的刷新
        mDynamicList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                //TODO:网络通信

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GlobalUtil.getInstance().getContent().add("延迟加载出的数据，只加载一个");
                        mContentAdapter.notifyDataSetChanged();
                        mDynamicList.onLoadFinish();
                    }
                }, 3000);
            }
        });

        return view;
    }

    public void resetContent(){

        //TODO：网络通信
        //获取主页的热门课程
        if (MainActivity.shapeLoadingDialog != null)
        {
            //MainActivity.shapeLoadingDialog.show();
        }
        //首先清空数据
        GlobalUtil.getInstance().getContent().clear();
        GlobalUtil.getInstance().getContent().add("tttt");

        //请求数据
        GetHotCourseAction action = new GetHotCourseAction();
        action.execute();
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

        public GetHotCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                java.net.URL url = new java.net.URL(URL.getPublicKeyURL());
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getPublicKeyURL());
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                GlobalUtil.getInstance().getContent().add("从网络端获取的初始值");
                                mContentAdapter.notifyDataSetChanged();

                                MainActivity.shapeLoadingDialog.dismiss();

                            }
                        }, 100);

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
