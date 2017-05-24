package com.example.a29149.yuyuan.Application;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSClientConfig;
import com.tencent.cos.common.COSEndPoint;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by MaLei on 2017/5/4.
 * Email:ml1995@mail.ustc.edu.cn
 * 自定义application，用以注册小米推送
 */

public class MyApplication extends Application {

    public static final String APP_ID = "2882303761517572848";
    public static final String APP_KEY = "5151757210848";
    public static final String TAG = "com.example.a29149";

    public static COSClient cos;

        @Override
        public void onCreate() {
            super.onCreate();
            //初始化push推送服务
            if(shouldInit()) {
                MiPushClient.registerPush(this, APP_ID, APP_KEY);
            }
           /* //设置用户账号用于小米推送
            MiPushClient.setUserAccount(this, "com.example.a29149.yuyuan.Application", null);*/
            //设置用户订阅topic
            String topic = "Java";
            MiPushClient.subscribe(this, topic, null);
            //打开Log
            LoggerInterface newLogger = new LoggerInterface() {

                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(this, newLogger);

            String appid =  "1252706879";
            Context context = getApplicationContext();
            String peristenceId = "AD5mKsOjtsiP8kCTaWOE1e6NSO9jASZW";

             //创建COSClientConfig对象，根据需要修改默认的配置参数
            COSClientConfig config = new COSClientConfig();
            //如设置园区
            config.setEndPoint(COSEndPoint.COS_SH);

             cos = new COSClient(context,appid,config,peristenceId);
        }
        //判断应用是否开启
        private boolean shouldInit() {
            ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            String mainProcessName = getPackageName();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
            return false;
        }

}
