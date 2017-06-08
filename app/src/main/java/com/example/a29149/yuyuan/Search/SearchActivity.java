package com.example.a29149.yuyuan.Search;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.Fragment.ArticleSearchFragment;
import com.example.a29149.yuyuan.Search.Fragment.CourseSearchFragment;
import com.example.a29149.yuyuan.Search.Fragment.QASearchFragment;
import com.example.a29149.yuyuan.Search.Fragment.RewardSearchFragment;
import com.example.a29149.yuyuan.Search.Fragment.TeacherSearchFragment;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.MyEditText;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.a29149.yuyuan.Main.MainStudentActivity.SHOW_OF_FIRST_TAG;
import static com.example.a29149.yuyuan.Main.MainStudentActivity.SHOW_OF_SECOND_TAG;
import static com.example.a29149.yuyuan.Main.MainStudentActivity.SHOW_OF_THIRD_TAG;

public class SearchActivity extends AbstractAppCompatActivity {

    public static final String SEARCH_OF_FIRST_TAG = "first";
    public static final String SEARCH_OF_SECOND_TAG = "second";
    public static final String SEARCH_OF_THIRD_TAG = "third";
    public static final String SEARCH_OF_FOUR_TAG = "four";
    public static final String SEARCH_OF_FIFTH_TAG = "fifth";

    @ViewInject(R.id.et_search)
    private MyEditText mKeyValue;

    //search 按钮组
    @ViewInject(R.id.search_main_menu)
    private RadioGroup mSearchMainMenu;

    @ViewInject(R.id.search_course)
    private RadioButton mSearchCourse;

    @ViewInject(R.id.rb_search_reward)
    private RadioButton mSearchReward;

    @ViewInject(R.id.search_teacher)
    private RadioButton mSearchTeacher;

    @ViewInject(R.id.search_QA)
    private RadioButton mSearchQA;

    @ViewInject(R.id.search_article)
    private RadioButton mSearchArticle;

    @ViewInject(R.id.iv_tab_line)
    private ImageView mTabLine;

    //指标 按钮组
    @ViewInject(R.id.search_sub_menu)
    private RadioGroup mSearchSubMenu;

    @ViewInject(R.id.search_target_multiple)
    private RadioButton mSearchTargetMultiple;

    @ViewInject(R.id.search_target_price)
    private RadioButton mSearchTargetPrice;

    @ViewInject(R.id.search_target_distance)
    private RadioButton mSearchTargetDistance;

    @ViewInject(R.id.search_target_course)
    private RadioButton mSearchTargetCourse;

    @ViewInject(R.id.search_target_teacher)
    private RadioButton mSearchTargetTeacher;

    @ViewInject(R.id.vp_content_pager)
    private ViewPager mContent;
    private RadioButton mCurrentTarget;

    //容器
    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mFragmentTabHost;

    private List<Fragment> fragmentList = new ArrayList<>();

    private int screenWidth = 0;

    //当前搜索什么
    private String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        MainStudentActivity.MIUISetStatusBarLightMode(getWindow(), true);

        //放置数据冗余
        GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().clear();

        //FragmentTabHost初始化
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.vp_content_pager);

        TabSpec tabSpec0 = mFragmentTabHost.newTabSpec(SEARCH_OF_FIRST_TAG)
                .setIndicator("0");
        TabSpec tabSpec1 = mFragmentTabHost.newTabSpec(SEARCH_OF_SECOND_TAG)
                .setIndicator("1");
        TabSpec tabSpec2 = mFragmentTabHost.newTabSpec(SEARCH_OF_THIRD_TAG)
                .setIndicator("2");
        TabSpec tabSpec3 = mFragmentTabHost.newTabSpec(SEARCH_OF_FOUR_TAG)
                .setIndicator("3");
        TabSpec tabSpec4 = mFragmentTabHost.newTabSpec(SEARCH_OF_FIFTH_TAG)
                .setIndicator("4");

        mFragmentTabHost.addTab(tabSpec0, CourseSearchFragment.class, null);
        mFragmentTabHost.addTab(tabSpec1, RewardSearchFragment.class, null);
        mFragmentTabHost.addTab(tabSpec2, TeacherSearchFragment.class, null);
        mFragmentTabHost.addTab(tabSpec3, QASearchFragment.class, null);
        mFragmentTabHost.addTab(tabSpec4, ArticleSearchFragment.class, null);

        mSearchMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.search_course:
                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
                        condition = "course";
                        break;
                    case R.id.rb_search_reward:
                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_SECOND_TAG);
                        condition = "reward";
                        break;
                    case R.id.search_teacher:
                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_THIRD_TAG);
                        condition = "teacher";
                        break;
                    case R.id.search_QA:
                        mFragmentTabHost.setCurrentTabByTag(SEARCH_OF_FOUR_TAG);
                        condition = "question";
                        break;
                    case R.id.search_article:
                        mFragmentTabHost.setCurrentTabByTag(SEARCH_OF_FIFTH_TAG);
                        condition = "article";
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
                mContent.setCurrentItem(position);
            }
        });

        mFragmentTabHost.setCurrentTab(0);
        mSearchCourse.setTextColor(getResources().getColor(R.color.orange));
        condition = "course";

        fragmentList.clear();
        fragmentList.add(new CourseSearchFragment());
        fragmentList.add(new RewardSearchFragment());
        fragmentList.add(new TeacherSearchFragment());
        fragmentList.add(new QASearchFragment());
        fragmentList.add(new ArticleSearchFragment());

        mContent.setAdapter(new MenuAdapter(getSupportFragmentManager()));
        mContent.setOffscreenPageLimit(5);
        mContent.setOnPageChangeListener(new ViewPagerListener());
        initTabLine();

        //默认选中第一个
        mCurrentTarget = mSearchTargetMultiple;
        mSearchTargetMultiple.setTextColor(getResources().getColor(R.color.orange));
        initSubMenu();

        // 设置软键盘“搜索”按钮的监听
        // 1）添加imeOptions
        // 2）修改singleLine
        // 3）添加监听
        mKeyValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //增加动作判断否则会调用两次
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    String searchContext = mKeyValue.getText().toString().trim();

                    if (TextUtils.isEmpty(searchContext)) {
                        Toast.makeText(SearchActivity.this, "关键字不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        // 先隐藏键盘
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                        search(searchContext);
                    }
                    return true;

                } else {
                    return false;
                }
            }
        });
    }

    //搜索方法
    private void search(String keyValue) {
        //TODO:网络传输, page恒为1
        SearchAction searchAction = new SearchAction(this);
        searchAction.execute(condition, "1", keyValue);
    }

    public void getResult(GetSearchResultEvent searchResultEvent) {
        switch (searchResultEvent.getCondition()) {
            case "course":
                break;
            case "reward":
                ((RewardSearchFragment)fragmentList.get(1)).getResult(searchResultEvent);
                break;
            case "article":
                break;
            case "teacher":
                break;
            case "question":
                break;
        }
    }
    //对子菜单进行初始化
    private void initSubMenu() {
        mSearchSubMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.search_target_multiple:
                        mSearchTargetMultiple.setChecked(true);
                        if (mCurrentTarget != mSearchTargetMultiple) {
                            mCurrentTarget.setTextColor(getResources().getColor(R.color.menu));
                            mSearchTargetMultiple.setTextColor(getResources().getColor(R.color.orange));
                            mCurrentTarget = mSearchTargetMultiple;
                        }
                        break;
                    case R.id.search_target_price:
                        mSearchTargetPrice.setChecked(true);
                        if (mCurrentTarget != mSearchTargetPrice) {
                            mCurrentTarget.setTextColor(getResources().getColor(R.color.menu));
                            mSearchTargetPrice.setTextColor(getResources().getColor(R.color.orange));
                            mCurrentTarget = mSearchTargetPrice;
                        }
                        break;
                    case R.id.search_target_distance:
                        mSearchTargetDistance.setChecked(true);
                        if (mCurrentTarget != mSearchTargetDistance) {
                            mCurrentTarget.setTextColor(getResources().getColor(R.color.menu));
                            mSearchTargetDistance.setTextColor(getResources().getColor(R.color.orange));
                            mCurrentTarget = mSearchTargetDistance;
                        }
                        break;
                    case R.id.search_target_course:
                        mSearchTargetCourse.setChecked(true);
                        if (mCurrentTarget != mSearchTargetCourse) {
                            mCurrentTarget.setTextColor(getResources().getColor(R.color.menu));
                            mSearchTargetCourse.setTextColor(getResources().getColor(R.color.orange));
                            mCurrentTarget = mSearchTargetCourse;
                        }
                        break;
                    case R.id.search_target_teacher:
                        mSearchTargetTeacher.setChecked(true);
                        if (mCurrentTarget != mSearchTargetTeacher) {
                            mCurrentTarget.setTextColor(getResources().getColor(R.color.menu));
                            mSearchTargetTeacher.setTextColor(getResources().getColor(R.color.orange));
                            mCurrentTarget = mSearchTargetTeacher;
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //初始化指引线
    private void initTabLine() {

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        FrameLayout.LayoutParams cp = (FrameLayout.LayoutParams) mTabLine.getLayoutParams();
        cp.width = screenWidth / 5 - 80;//设置该控ayoutParams参数
        cp.height = 5;

        mTabLine.setLayoutParams(cp);//将修改好的layoutParams设置为该控件的layoutParams*/
    }

    @OnClick(R.id.ib_return)
    public void setReturnListener(View view) {
        this.onBackPressed();
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
        private RadioButton mCurrentRB = mSearchCourse;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            FrameLayout.LayoutParams cp = (FrameLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            cp.leftMargin = (int) ((positionOffset + position) * screenWidth / 5) + 40;
            mTabLine.setLayoutParams(cp);
        }

        @Override
        public void onPageSelected(int index) {
            if (index == 0) {
                mSearchCourse.setChecked(true);
                if (mCurrentRB != mSearchCourse) {
                    mSearchCourse.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchCourse;
                }

            } else if (index == 1) {
                mSearchReward.setChecked(true);
                if (mCurrentRB != mSearchReward) {
                    mSearchReward.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchReward;
                }
            } else if (index == 2) {
                mSearchTeacher.setChecked(true);
                if (mCurrentRB != mSearchTeacher) {
                    mSearchTeacher.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchTeacher;
                }
            } else if (index == 3) {
                mSearchQA.setChecked(true);
                if (mCurrentRB != mSearchQA) {
                    mSearchQA.setTextColor(getResources().getColor(R.color.orange));
                    mCurrentRB.setTextColor(getResources().getColor(R.color.colorPrimary));
                    mCurrentRB = mSearchQA;
                }
            } else if (index == 4) {
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

}
