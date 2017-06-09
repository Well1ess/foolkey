package com.example.a29149.yuyuan.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.ModelStudent.Discovery.DiscoveryMainFragment;
import com.example.a29149.yuyuan.ModelStudent.Index.IndexMainFragment;
import com.example.a29149.yuyuan.ModelStudent.Me.MeMainFragment;
import com.example.a29149.yuyuan.ModelStudent.Order.fragment.OrderFragment;
import com.example.a29149.yuyuan.ModelStudent.Publish.Activity.PublishRewardDescribeStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.PublishMainFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_MODIFY;
import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

public class MainStudentActivity extends AbstractAppCompatActivity {

    private static final String TAG = "MainStudentActivity";

    public static final String SHOW_OF_FIRST_TAG = "first";
    public static final String SHOW_OF_SECOND_TAG = "second";
    public static final String SHOW_OF_THIRD_TAG = "third";
    public static final String SHOW_OF_FOURTH_TAG = "fourth";
    public static final String SHOW_OF_FIFTH_TAG = "fifth";

    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mFragmentTabHost;

    @ViewInject(R.id.tab_main_menu)
    private RadioGroup mMainMenu;

    @ViewInject(R.id.main_menu_index)
    private RadioButton mIndexButton;

    @ViewInject(R.id.rb_main_menu_discovery)
    private RadioButton mDiscoveryButton;

    @ViewInject(R.id.main_menu_publish)
    private RadioButton mPublishButton;

    @ViewInject(R.id.main_menu_order)
    private RadioButton mOrderButton;

    @ViewInject(R.id.main_menu_me)
    private RadioButton mMeButton;

    @ViewInject(R.id.alpha_publish_panel)
    private FrameLayout mAlphaPublishPanel;

    @ViewInject(R.id.publish_panel)
    private LinearLayout mPublishPanel;

    @ViewInject(R.id.publish_panel_real)
    private LinearLayout mRealPublishPanel;
    private boolean mPublishPanelOpen = false;

    public static ShapeLoadingDialog shapeLoadingDialog;

    private TextView mPublishRewardStudent;
    private TextView mPublishAskStudent;

    //首页
    private Fragment indexMainFragment = new IndexMainFragment() ;
    //发现
    private Fragment discoveryMainFragment = new DiscoveryMainFragment();
    //发布
    private Fragment publishMainFragment = new PublishMainFragment() ;
    //订单
    private Fragment orderFragment = new OrderFragment();
    //我的
    private Fragment meMainFragment = new MeMainFragment() ;
    //fragment管理器
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进入到这里时会杀死闪入的activity
        AppManager.getInstance().finishActivity( SplashActivity.class );
        //绑定UI等等
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注解式绑定
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        //往activity管理器中添加本activity，这一步移交给AbstractFragment做了
        //全局Dialog的初始化
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        //设置按钮颜色
        mIndexButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        //初始化按钮与fragment绑定关系
        initRadioGroupListener();
        //一开始的时候展示的是首页
        switchFragment(R.id.main_tab_fragment, indexMainFragment);
        //悬赏页面的控件初始化
        mPublishRewardStudent = (TextView) findViewById(R.id.tv_xuanshang);
        mPublishAskStudent = (TextView) findViewById(R.id.tv_ask);
        mPublishRewardStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStudentActivity.this, PublishRewardDescribeStudentActivity.class);

                startActivity(intent);
            }
        });
        mPublishAskStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainStudentActivity.this, "发布问题，敬请期待！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 发布按键
     * @param view
     */
    @OnClick(R.id.main_menu_publish)
    public void setMainMenuListener(View view)
    {
        openPublishPanel();
    }

    /**
     * 初始化5个按钮
     */
    private void initRadioGroupListener() {
        mMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            //注册事件
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /**
                 * 根据点击不同，展示不同的fragment
                 */
                switch (checkedId) {
                    case R.id.main_menu_index://首页
                        switchFragment(R.id.main_tab_fragment, indexMainFragment);
                        resetMenuTextColor();
                        mIndexButton.setTextColor(0xff8BC34A);
                        break;

                    case R.id.rb_main_menu_discovery: //发现
                        switchFragment(R.id.main_tab_fragment, discoveryMainFragment);
                        resetMenuTextColor();
                        mDiscoveryButton.setTextColor(0xff8BC34A);
                        break;

                    case R.id.main_menu_order: // 订单
                        switchFragment(R.id.main_tab_fragment, orderFragment);
                        resetMenuTextColor();
                        mOrderButton.setTextColor(0xff8BC34A);

                        break;
                    case R.id.main_menu_me: // 我的
                        switchFragment(R.id.main_tab_fragment, meMainFragment);
                        resetMenuTextColor();
                        mMeButton.setTextColor(0xff8BC34A);
                        break;

                    default:
                        break;
                }
            }
        });

    }

    private void openPublishPanel() {
        mPublishPanelOpen = true;
        mAlphaPublishPanel.setVisibility(View.VISIBLE);
        mPublishPanel.setVisibility(View.VISIBLE);

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, -0.1f);
        translateAnimation.setDuration(150);
        translateAnimation.setFillAfter(true);

        TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -0.1f, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation1.setDuration(30);
        translateAnimation1.setStartOffset(250);
        translateAnimation1.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(translateAnimation1);

        mRealPublishPanel.startAnimation(animationSet);
    }

    @OnClick(R.id.close_publish_panel)
    public void setClosePublishPanel(View view)
    {
        mPublishPanelOpen = false;
        mAlphaPublishPanel.setVisibility(View.GONE);
        mPublishPanel.setVisibility(View.GONE);
    }

    /**
     * 重设按钮的颜色
     */
    private void resetMenuTextColor() {
        mIndexButton.setTextColor(0xffaaaaaa);
        mDiscoveryButton.setTextColor(0xffaaaaaa);
        //mPublishButton.setTextColor(getResources().getColor(R.color.orange));
        mOrderButton.setTextColor(0xffaaaaaa);
        mMeButton.setTextColor(0xffaaaaaa);
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                //FIXME java.lang.ClassNotFoundException: android.view.MiuiWindowManager$LayoutParams
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                if (layoutParams == null) {
                    return false;
                }
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (mPublishPanelOpen)
            setClosePublishPanel(null);
        else
            super.onBackPressed();
    }

    /**
     * fragment里面，startActivityForResult，返回的结果在这里处理
     * 这里拿到fragment，再做处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // 成功请求
            StudentDTO studentDTO = GlobalUtil.getInstance().getStudentDTO();
            MeMainFragment meMainFragment = (MeMainFragment) this.meMainFragment;
            switch (requestCode) {
                case FROM_ME_FRAGMENT_TO_RECHARGE: //充值界面回去
                    meMainFragment.setVirtualMoney(data.getStringExtra("virtualCurrency"));
                    break;
                case FROM_ME_FRAGMENT_TO_MODIFY: //修改个人资料界面回去
                    meMainFragment.setGithub(studentDTO.getGithubUrl());
                    meMainFragment.setEmail(studentDTO.getEmail());
                    meMainFragment.setTechnicTag( studentDTO.getTechnicTagEnum() );
                    meMainFragment.set2stNickedName(studentDTO.getNickedName());
                    meMainFragment.setTitle(studentDTO.getNickedName());
                    meMainFragment.setUserSlogan( studentDTO.getSlogan() );
                    break;
            }
        }
    }

    /**
     * 以下5个获取fragment的方法，可以使得别的activity直接操作这些fragment
     * @Author geyao
     * @Date 2017/6/8 下午12:39
     **/
    /**
     * 获取首页的fragment
     * @return
     */
    public Fragment getIndexMainFragment() {
        return indexMainFragment;
    }

    /**
     * 获取发现的fragment
     * @return
     */
    public Fragment getDiscoveryMainFragment() {
        return discoveryMainFragment;
    }

    /**
     * 获取发布的fragment
     * @return
     */
    public Fragment getPublishMainFragment() {
        return publishMainFragment;
    }

    /**
     * 获取订单的fragment
     * @return
     */
    public Fragment getOrderFragment() {
        return orderFragment;
    }

    /**
     * 获取【我的】fragment
     * @return
     */
    public Fragment getMeMainFragment() {
        return meMainFragment;
    }
}
