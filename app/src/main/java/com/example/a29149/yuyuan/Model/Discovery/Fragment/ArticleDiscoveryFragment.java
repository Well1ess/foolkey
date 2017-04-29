package com.example.a29149.yuyuan.Model.Discovery.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Model.Discovery.Activity.ArticleActivity;
import com.example.a29149.yuyuan.Model.Discovery.Adapter.ArticleListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;

public class ArticleDiscoveryFragment extends Fragment {

    @ViewInject(R.id.content)
    private DynamicListView mDynamicListView;
    private ArticleListAdapter mArticleAdapter;

    @ViewInject(R.id.slide_layout)
    private SlideRefreshLayout mSlideRefreshLayout;

    @ViewInject(R.id.refresh)
    private ImageView mRefreshIcon;

    public ArticleDiscoveryFragment() {

    }

    public static ArticleDiscoveryFragment newInstance() {
        ArticleDiscoveryFragment fragment = new ArticleDiscoveryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_discovery, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        //list初始化
        mArticleAdapter = new ArticleListAdapter(getContext());
        mDynamicListView.setAdapter(mArticleAdapter);
        mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toArticleActivity = new Intent(getActivity(), ArticleActivity.class);
                startActivity(toArticleActivity);
            }
        });

        //初始化动态加载
        mDynamicListView.setOnLoadingListener(new DynamicListView.onLoadingListener() {
            @Override
            public void setLoad() {
                mDynamicListView.onLoadFinish();
            }
        });

        //下拉滑动
        mSlideRefreshLayout.setRotateView(mRefreshIcon);
        mSlideRefreshLayout.setOnSlideRefreshListener(new SlideRefreshLayout.onSlideRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Article", Toast.LENGTH_SHORT).show();
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
