package com.example.a29149.yuyuan.ModelTeacher.Index;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.a29149.yuyuan.AbstractObject.YYFragmentTabHost;
import com.example.a29149.yuyuan.ModelTeacher.Index.adapter.MenuViewPagerAdapter;
import com.example.a29149.yuyuan.ModelTeacher.Index.course.OwnerCourseTeacherFragment;
import com.example.a29149.yuyuan.ModelTeacher.Index.reward.OwnerRewardTeacherFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.example.a29149.yuyuan.ModelStudent.Discovery.DiscoveryMainFragment.DISCOVERY_OF_FIRST_TAG;
import static com.example.a29149.yuyuan.ModelStudent.Discovery.DiscoveryMainFragment.DISCOVERY_OF_SECOND_TAG;

/**
 * 老师界面主页面
 */


public class TeacherIndexMainFragment extends Fragment {

    private static final String TAG = "TeacherIndexMainFragmen";

    public static final String TEACHER_MAIN_OF_FIRST_TAG = "first";
    public static final String TEACHER_MAIN_OF_SECOND_TAG = "second";

    //按钮组
    @ViewInject(R.id.rg_discovery_main_menu)
    private RadioGroup mMainMenu;

    @ViewInject(R.id.rb_owner_reward_teacher)
    private RadioButton mSearchReward;

    @ViewInject(R.id.rb_owner_course_teacher)
    private RadioButton mSearchScore;


    //指引线
    @ViewInject(R.id.iv_tab_line)
    private ImageView mTabLine;

    //容器
    @ViewInject(android.R.id.tabhost)
    private YYFragmentTabHost mFragmentTabHost;

    @ViewInject(R.id.vp_content_pager)
    private ViewPager mContentViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private int screenWidth = 0;

    public TeacherIndexMainFragment() {

    }


    public static TeacherIndexMainFragment newInstance() {
        TeacherIndexMainFragment fragment = new TeacherIndexMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_main, container, false);
        AnnotationUtil.setClickListener(this, view);
        AnnotationUtil.injectViews(this, view);

        //FragmentTabHost初始化
        mFragmentTabHost.setup(getContext(), getChildFragmentManager(), R.id.vp_content_pager);

        TabSpec tabSpec0 = mFragmentTabHost.newTabSpec(TEACHER_MAIN_OF_FIRST_TAG)
                .setIndicator("0");
        TabSpec tabSpec1 = mFragmentTabHost.newTabSpec(TEACHER_MAIN_OF_SECOND_TAG)
                .setIndicator("1");

        mFragmentTabHost.addTab(tabSpec0, OwnerCourseTeacherFragment.class, null);
        mFragmentTabHost.addTab(tabSpec1, OwnerRewardTeacherFragment.class, null);
        //OwnerCourseTeacherFragment
        //OwnerRewardTeacherFragment

        mMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_owner_course_teacher:
                        mFragmentTabHost.setCurrentTabByTag(DISCOVERY_OF_FIRST_TAG);
                        break;
                    case R.id.rb_owner_reward_teacher:
                        mFragmentTabHost.setCurrentTabByTag(DISCOVERY_OF_SECOND_TAG);
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
        mSearchScore.setTextColor(getResources().getColor(R.color.orange));

        fragmentList.clear();
        fragmentList.add(new OwnerCourseTeacherFragment());
        fragmentList.add(new OwnerRewardTeacherFragment());

        mContentViewPager.setAdapter(new MenuViewPagerAdapter(getChildFragmentManager()));
        mContentViewPager.setOffscreenPageLimit(2);
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
        cp.width = screenWidth / 2 - 160;//设置该控ayoutParams参数
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

//    class MenuViewPagerAdapter extends FragmentPagerAdapter {
//
//        public MenuViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int arg0) {
//            return fragmentList.get(arg0);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//    }

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
            cp.leftMargin = (int) ((positionOffset + position) * screenWidth / 2) + 80;
            mTabLine.setLayoutParams(cp);
        }

        @Override
        public void onPageSelected(int index) {
            if (index == 1) {
                mSearchReward.setChecked(true);
                if (mCurrentRB != mSearchReward) {
                    mSearchReward.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchReward;
                    //TODO:网络通信

                }
            } else if (index == 0) {
                mSearchScore.setChecked(true);
                if (mCurrentRB != mSearchScore) {
                    mSearchScore.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchScore;
                }
            }
            mFragmentTabHost.setCurrentTab(index);
        }
    }
}
