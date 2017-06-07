package com.example.a29149.yuyuan.ModelStudent.Discovery;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment.ArticleDiscoveryFragment;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment.QADiscoveryFragment;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment.RewardDiscoveryFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class DiscoveryMainFragment extends Fragment {

    public static final String DISCOVERY_OF_FIRST_TAG = "first";
    public static final String DISCOVERY_OF_SECOND_TAG = "second";
    public static final String DISCOVERY_OF_THIRD_TAG = "third";

    //按钮组
    @ViewInject(R.id.discovery_main_menu)
    private RadioGroup mMainMenu;

    @ViewInject(R.id.search_reward)
    private RadioButton mSearchReward;

    @ViewInject(R.id.search_QA)
    private RadioButton mSearchQA;

    @ViewInject(R.id.search_article)
    private RadioButton mSearchArticle;

    //指引线
    @ViewInject(R.id.id_tab_line)
    private ImageView mTabLine;

    //容器
    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mFragmentTabHost;

    @ViewInject(R.id.content_pager)
    private ViewPager mContentViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private int screenWidth = 0;

    public DiscoveryMainFragment() {

    }


    public static DiscoveryMainFragment newInstance() {
        DiscoveryMainFragment fragment = new DiscoveryMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discovery_main, container, false);
        AnnotationUtil.setClickListener(this, view);
        AnnotationUtil.injectViews(this, view);

        //FragmentTabHost初始化
        mFragmentTabHost.setup(getContext(), getChildFragmentManager(), R.id.content_pager);

        TabSpec tabSpec0 = mFragmentTabHost.newTabSpec(DISCOVERY_OF_FIRST_TAG)
                .setIndicator("0");
        TabSpec tabSpec1 = mFragmentTabHost.newTabSpec(DISCOVERY_OF_SECOND_TAG)
                .setIndicator("1");
        TabSpec tabSpec2 = mFragmentTabHost.newTabSpec(DISCOVERY_OF_THIRD_TAG)
                .setIndicator("2");

        mFragmentTabHost.addTab(tabSpec0, RewardDiscoveryFragment.class, null);
        mFragmentTabHost.addTab(tabSpec1, QADiscoveryFragment.class, null);
        mFragmentTabHost.addTab(tabSpec2, ArticleDiscoveryFragment.class, null);

        mMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.search_reward:
                        MainStudentActivity.shapeLoadingDialog.show();
                        mFragmentTabHost.setCurrentTabByTag(DISCOVERY_OF_FIRST_TAG);
                        break;
                    case R.id.search_QA:
                        mFragmentTabHost.setCurrentTabByTag(DISCOVERY_OF_SECOND_TAG);
                        break;
                    case R.id.search_article:
                        mFragmentTabHost.setCurrentTabByTag(DISCOVERY_OF_THIRD_TAG);
                        break;
                    default:
                        break;
                }
            }
        });

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int position = mFragmentTabHost.getCurrentTab();
                mContentViewPager.setCurrentItem(position);
            }
        });

        mFragmentTabHost.setCurrentTab(0);
        mSearchReward.setTextColor(getResources().getColor(R.color.orange));

        fragmentList.clear();
        fragmentList.add(new RewardDiscoveryFragment());
        fragmentList.add(new QADiscoveryFragment());
        fragmentList.add(new ArticleDiscoveryFragment());

        mContentViewPager.setAdapter(new MenuAdapter(getChildFragmentManager()));
        mContentViewPager.setOffscreenPageLimit(3);
        mContentViewPager.setOnPageChangeListener(new ViewPagerListener());
        initTabLine();

        return view;
    }

    //初始化指引线
    private void initTabLine() {

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        FrameLayout.LayoutParams cp = (FrameLayout.LayoutParams) mTabLine.getLayoutParams();
        cp.width = screenWidth / 3 - 160;//设置该控ayoutParams参数
        cp.height = 5;

        mTabLine.setLayoutParams(cp);//将修改好的layoutParams设置为该控件的layoutParams*/
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    class MenuAdapter extends FragmentPagerAdapter {

        public MenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    class ViewPagerListener implements ViewPager.OnPageChangeListener {

        //暂存上一个被选中的按钮
        private RadioButton mCurrentRB = mSearchReward;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            FrameLayout.LayoutParams cp = (FrameLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            cp.leftMargin = (int) ((positionOffset + position) * screenWidth / 3) + 80;
            mTabLine.setLayoutParams(cp);
        }

        @Override
        public void onPageSelected(int index) {
            if (index == 0) {
                mSearchReward.setChecked(true);
                if (mCurrentRB != mSearchReward) {
                    mSearchReward.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchReward;
                    //TODO:网络通信

                }
            }  else if (index == 1) {
                mSearchQA.setChecked(true);
                if (mCurrentRB != mSearchQA) {
                    mSearchQA.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchQA;
                }
            } else if (index == 2) {
                mSearchArticle.setChecked(true);
                if (mCurrentRB != mSearchArticle) {
                    mSearchArticle.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchArticle;
                }
            }
            mFragmentTabHost.setCurrentTab(index);
        }
    }

    @OnClick(R.id.et_search)
    public void setSearchEdtListener(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), view, "searchView").toBundle());
    }

}
