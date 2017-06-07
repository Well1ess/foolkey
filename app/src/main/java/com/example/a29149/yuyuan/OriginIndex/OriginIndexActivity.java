package com.example.a29149.yuyuan.OriginIndex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;


/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 点击头像跳至此页面，显示学生
 *
 */

public class OriginIndexActivity extends AppCompatActivity {

    @ViewInject(R.id.v_mask)
    private View mMask;

    @ViewInject(R.id.option_menu)
    private LinearLayout mOptionMenu;

    @ViewInject(R.id.ib_return)
    private ImageButton mReturn;

    @ViewInject(R.id.tv_toolbar_title)
    private TextView mToolbarTitle;//老师名字

    @ViewInject(R.id.iv_photo)
    private ImageView mHead;//老师头像

    @ViewInject(R.id.tv_slogan)
    private TextView mSlogan;//老师slogan

    @ViewInject(R.id.tv_sex)
    private TextView mSex;//老师性别

    @ViewInject(R.id.tv_organization)
    private TextView mOrganization;//老师组织

    @ViewInject(R.id.tv_description)
    private TextView mDescription;//老师描述

    @ViewInject(R.id.tv_followNum)
    private TextView mFollowNum;//关注老师人数

    @ViewInject(R.id.tv_follow)
    private TextView mFollow;//关注老师

    @ViewInject(R.id.rb_main_menu_discovery)
    private RadioButton mChart;//联系老师


    //记录Option Menu是否打开
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_index);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (isOpen)
                    closeOptionMenu();
                else
                    openOptionMenu();
                return true;
            }
        });

        initData();
    }

    //填充数据
    private void initData() {
        StudentDTO studentDTO = GlobalUtil.getInstance().getStudentDTO();
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();
        if (teacherDTO == null)
            teacherDTO = new TeacherDTO();
        mToolbarTitle.setText(studentDTO.getNickedName()+"的主页");
        mSlogan.setText(studentDTO.getSlogan());
        mSex.setText(studentDTO.getSexTagEnum().toString());
        mOrganization.setText(studentDTO.getOrganization());
        mDescription.setText(studentDTO.getDescription());
        //mFollowNum.setText(teacherDTO.getFollowerNumber());
    }

    //打开分享面板
    private void openOptionMenu()
    {
        if (!isOpen){
            isOpen = true;

            mMask.setVisibility(View.INVISIBLE);
            mOptionMenu.setVisibility(View.INVISIBLE);

            mMask.setVisibility(View.VISIBLE);
            mOptionMenu.setVisibility(View.VISIBLE);


            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    1.0f,
                    Animation.RELATIVE_TO_SELF,
                    0f);
            translateAnimation.setDuration(150);
            mOptionMenu.startAnimation(translateAnimation);
        }
    }

    //关闭分享面板
    private void closeOptionMenu()
    {
        if (isOpen){
            isOpen = false;

            mMask.setVisibility(View.GONE);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    1.0f);
            translateAnimation.setDuration(150);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mOptionMenu.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mOptionMenu.startAnimation(translateAnimation);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (isOpen)
                closeOptionMenu();
            else
                openOptionMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.v_mask)
    public  void setMaskListener(View view){
        closeOptionMenu();
    }

    @OnClick(R.id.tv_return)
    public  void setReturnListener(View view){
        this.finish();
    }

    @OnClick(R.id.tv_follow)
    public  void setFollowListener(View view){
        Toast.makeText(this,"关注该老师",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.rb_main_menu_discovery)
    public  void setChartListener(View view){
        Toast.makeText(this,"联系该老师",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
