package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.ArticleActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.ArticleListAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.GetSearchResultEvent;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.AbstractObject.AbstracFragment;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class ArticleSearchFragment extends AbstracFragment {

    //页数
    int pageNo = 2;

    //上滑到底部课更新
    @ViewInject(R.id.content)
    private DynamicListView mDynamicListView;
    private ArticleListAdapter mArticleAdapter;

    //下滑刷新
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideRefreshLayout;

    @ViewInject(R.id.iv_refresh)
    private ImageView mRefreshIcon;

    private String condition = "article";
    private String keyValue;

    public ArticleSearchFragment() {
        super();
    }

    public static ArticleSearchFragment newInstance() {
        ArticleSearchFragment fragment = new ArticleSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_search, container, false);
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
                //TODO:网络传输

                pageNo++;
            }
        });

        //设置列表下拉时的刷新
        mSlideRefreshLayout.setRotateView(view.findViewById(R.id.iv_refresh));
        mSlideRefreshLayout.setOnSlideRefreshListener(
                new SlideRefreshLayout.onSlideRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //TODO:网络通信
                        pageNo = 1;
                        //获取主页的热门课程
                        if (MainStudentActivity.shapeLoadingDialog != null) {
                            MainStudentActivity.shapeLoadingDialog.show();
                        }
                        //由于是刷新，所以首先清空所有数据
                    }
                });
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getResult(GetSearchResultEvent resultEvent) {
        MainStudentActivity.shapeLoadingDialog.dismiss();
        keyValue = resultEvent.getKeyValue();
        if (resultEvent.isResult())
            mArticleAdapter.notifyDataSetChanged();
        else
            Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();

        //如果page不为1则表示为动态刷新
        if (pageNo != 1)
            mDynamicListView.onLoadFinish();

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
