package com.example.a29149.yuyuan.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.RefreshSelfInfo.RefreshSelfInfo;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;

/**
 * Created by geyao on 2017/5/22.
 */

public class SplashActivity extends AppCompatActivity {

    //获取用户配置
    UserConfig userConfig;

    private String strUserName;
    private String strPassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash);

        userConfig = new UserConfig(this);
        if (userConfig.getBooleanInfo(UserConfig.xmlSAVE)) {

            strUserName = userConfig.getStringInfo(UserConfig.xmlUSER_NAME);
            strPassWord = userConfig.getStringInfo(UserConfig.xmlPASSWORD);
            GlobalUtil.getInstance().setPublicKey(userConfig.getStringInfo(UserConfig.xmlPUBLIC_KEY));
            GlobalUtil.getInstance().setAESKey(userConfig.getStringInfo(UserConfig.xmlAES_KEY));
            GlobalUtil.getInstance().setToken(userConfig.getStringInfo(UserConfig.xmlTOKEN));

            System.out.println(this.getClass() + "判断可以自动登录");
            //TODO：直接进行网络传输
            RefreshSelfInfo refreshSelfInfo = new RefreshSelfInfo(this);
            refreshSelfInfo.execute();
        }else {
            System.out.println(this.getClass() + "判断不可以自动登录");
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
