package com.example.a29149.yuyuan.Main;

import android.app.Activity;
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
 * splashActivity
 * 启动时一闪而过的东西，会判断本地是否有缓存，如果有，则向服务器发送一个自动刷新的请求
 * 如果没有，或者请求失败，则跳转到登陆界面
 * Created by geyao on 2017/5/22.
 */

public class SplashActivity extends Activity {

    //获取用户配置
    UserConfig userConfig;

    private String strUserName;
    private String strPassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //这里要首先设置主题，否则会有灰色的画面闪过，不能达到一开app就splash图片的效果
        setTheme( R.style.splashScreenTheme );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userConfig = new UserConfig(this);
        if (userConfig.getBooleanInfo(UserConfig.xmlSAVE)) {

            strUserName = userConfig.getStringInfo(UserConfig.xmlUSER_NAME);
            strPassWord = userConfig.getStringInfo(UserConfig.xmlPASSWORD);
            GlobalUtil.getInstance().setPublicKey(userConfig.getStringInfo(UserConfig.xmlPUBLIC_KEY));
            GlobalUtil.getInstance().setAESKey(userConfig.getStringInfo(UserConfig.xmlAES_KEY));
            GlobalUtil.getInstance().setToken(userConfig.getStringInfo(UserConfig.xmlTOKEN));

            //TODO：直接进行网络传输
            //向服务器刷新，如果成功，则登录
            RefreshSelfInfo refreshSelfInfo = new RefreshSelfInfo(this);
            refreshSelfInfo.execute();
            SplashActivity.this.finish();
        }else {
            //如果本地没有数据，则重新登录
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
