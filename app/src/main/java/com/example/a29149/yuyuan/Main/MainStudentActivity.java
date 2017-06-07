package com.example.a29149.yuyuan.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

public class MainStudentActivity extends AppCompatActivity {

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

    @ViewInject(R.id.main_menu_discovery)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进入到这里时会杀死闪入的activity
        AppManager.getInstance().finishActivity( SplashActivity.class );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        AppManager.getInstance().addActivity(MainStudentActivity.this);
        //全局Dialog的初始化
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        //shapeLoadingDialog.getDialog().setCancelable(false);

        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_fragment);

        TabHost.TabSpec tabSpec0 = mFragmentTabHost.newTabSpec(SHOW_OF_FIRST_TAG)
                .setIndicator("0");
        TabHost.TabSpec tabSpec1 = mFragmentTabHost.newTabSpec(SHOW_OF_SECOND_TAG)
                .setIndicator("1");
        TabHost.TabSpec tabSpec2 = mFragmentTabHost.newTabSpec(SHOW_OF_THIRD_TAG)
                .setIndicator("2");
        TabHost.TabSpec tabSpec3 = mFragmentTabHost.newTabSpec(SHOW_OF_FOURTH_TAG)
                .setIndicator("3");
        TabHost.TabSpec tabSpec4 = mFragmentTabHost.newTabSpec(SHOW_OF_FIFTH_TAG)
                .setIndicator("4");

        mFragmentTabHost.addTab(tabSpec0, IndexMainFragment.class, null);
        mFragmentTabHost.addTab(tabSpec1, DiscoveryMainFragment.class, null);
        mFragmentTabHost.addTab(tabSpec2, PublishMainFragment.class, null);
        mFragmentTabHost.addTab(tabSpec3, OrderFragment.class, null);
        mFragmentTabHost.addTab(tabSpec4, MeMainFragment.class, null);

        mIndexButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        initRadioGroupListener();

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

    @OnClick(R.id.main_menu_publish)
    public void setMainMenuListener(View view)
    {
        openPublishPanel();
    }

    private void initRadioGroupListener() {
        mMainMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_menu_index:

                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
                        resetMenuTextColor();
                        mIndexButton.setTextColor(0xff8BC34A);

                        break;
                    case R.id.main_menu_discovery:

                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_SECOND_TAG);
                        resetMenuTextColor();
                        mDiscoveryButton.setTextColor(0xff8BC34A);

                        break;

                    case R.id.main_menu_order:

                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_FOURTH_TAG);
                        resetMenuTextColor();
                        mOrderButton.setTextColor(0xff8BC34A);

                        break;
                    case R.id.main_menu_me:

                        mFragmentTabHost.setCurrentTabByTag(SHOW_OF_FIFTH_TAG);
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
     * fragment里面，startActivityForResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FROM_ME_FRAGMENT_TO_RECHARGE:
                if (resultCode == RESULT_OK){
                    MeMainFragment meMainFragment;
                    meMainFragment = (MeMainFragment) getSupportFragmentManager().findFragmentByTag(SHOW_OF_FIFTH_TAG);
                    meMainFragment.setVirtualMoney( data.getStringExtra("virtualCurrency") );
                }else {
                    Toast.makeText(this, "充值失败", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
