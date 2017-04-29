package com.example.a29149.yuyuan.Model.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.Model.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.Model.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.DynamicListView;

public class RewardDiscoveryFragment extends Fragment {

    @ViewInject(R.id.content)
    private DynamicListView mRewardList;
    private RewardListAdapter mListAdapter;

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
                startActivity(toCourseActivity);
            }
        });

        //设置列表动态加载
        mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                mRewardList.onLoadFinish();
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
