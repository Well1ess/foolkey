package com.example.a29149.yuyuan.Model.Me.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.UserConfig;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

    }

    @OnClick(R.id.bt_return)
    public void setBtReturnListener(View view) {
        this.onBackPressed();
    }

    @OnClick(R.id.quit)
    public void setBackListener(View view) {
        UserConfig mUserConfig = new UserConfig(this);
        mUserConfig.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();
    }
}
