package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment.RewardDiscoveryFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.GetSearchResultEvent;
import com.example.a29149.yuyuan.Search.SearchAction;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;

public class RewardSearchFragment extends Fragment {

    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;

    private RewardListAdapter mListAdapter;

    //记录请求的页数
    int pageNo = 1;

    String keyValue;

    public RewardSearchFragment() {

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
                toCourseActivity.putExtra("position", position);
                startActivity(toCourseActivity);
            }
        });

        //设置列表动态加载
        mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                //TODO:网络传输
                SearchAction searchAction = new SearchAction((SearchActivity) getActivity());
                searchAction.execute("reward", pageNo+"", keyValue);
                pageNo++;
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
                        GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().clear();
                        SearchAction searchAction = new SearchAction((SearchActivity) getActivity());
                        searchAction.execute("reward", pageNo + "", keyValue);
                        pageNo++;
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

    public void getResult(GetSearchResultEvent searchResultEvent) {
        keyValue = searchResultEvent.getKeyValue();
        if (searchResultEvent.isResult()) {
            mListAdapter.notifyDataSetChanged();
            pageNo++;
        }
    }

}
