package com.example.a29149.yuyuan.Model.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.Model.Discovery.Activity.QAActivity;
import com.example.a29149.yuyuan.Model.Discovery.Adapter.QAListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.DynamicListView;


public class QADiscoveryFragment extends Fragment {

    @ViewInject(R.id.content)
    private DynamicListView mDynamicListView;
    private QAListAdapter mQAListAdapter;

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

}
