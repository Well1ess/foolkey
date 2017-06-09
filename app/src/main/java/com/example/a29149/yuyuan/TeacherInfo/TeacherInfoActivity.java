package com.example.a29149.yuyuan.TeacherInfo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;

@Deprecated
public class TeacherInfoActivity extends AbstractAppCompatActivity {

    @ViewInject(R.id.v_mask)
    private View mMask;

    @ViewInject(R.id.ib_return)
    private ImageButton mReturn;

    @ViewInject(R.id.option_menu)
    private LinearLayout mOptionMenu;

    //记录Option Menu是否打开
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
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
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }
}
