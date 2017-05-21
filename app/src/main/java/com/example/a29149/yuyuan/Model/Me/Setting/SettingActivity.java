package com.example.a29149.yuyuan.Model.Me.Setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.userInfo.LogOutController;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

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
        AppManager.getInstance().removeActivity(MainActivity.class);
    }

    @OnClick(R.id.quit)
    public void setBackListener(View view) {
        String id = GlobalUtil.getInstance().getId();
        MiPushClient.unsetUserAccount(SettingActivity.this, id, null);

        LogOutAction logOutAction = new LogOutAction();
        logOutAction.execute();
    }

    public class LogOutAction extends AsyncTask<String, Integer, String> {

        public LogOutAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            return LogOutController.execute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        //注销账户时反注销推送id，用以不接受消息
                        String id = GlobalUtil.getInstance().getId();
                        MiPushClient.unsetUserAccount(SettingActivity.this, id, null);

                        AppManager.getInstance().finishActivity(MainActivity.class);

                        UserConfig mUserConfig = new UserConfig(SettingActivity.this);
                        mUserConfig.clear();


                        SettingActivity.this.finish();

                    } else {
                        Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }finally {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
