package com.example.a29149.yuyuan.Model.Index.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Model.Index.Course.CourseActivity;
import com.example.a29149.yuyuan.Model.Index.Adapter.IndexContentAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;


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
                        Toast.makeText(getContext(), "刷新", Toast.LENGTH_SHORT).show();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalUtil.getInstance().getContent().clear();
                GlobalUtil.getInstance().getContent().add("从网络端获取的初始值");
                mContentAdapter.notifyDataSetChanged();
                mDynamicList.onLoadFinish();
            }
        }, 100);
    }

    private void setContentListListener() {
        mDynamicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toCourseActivity = new Intent(getActivity(), CourseActivity.class);
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
}
