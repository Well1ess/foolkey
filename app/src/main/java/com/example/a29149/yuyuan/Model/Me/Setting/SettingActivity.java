package com.example.a29149.yuyuan.Model.Me.Setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;

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
    }

    @OnClick(R.id.quit)
    public void setBackListener(View view) {

        LogOutAction logOutAction = new LogOutAction();
        logOutAction.execute();
    }

    public class LogOutAction extends AsyncTask<String, Integer, String> {

        public LogOutAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", GlobalUtil.getInstance().getToken());

                log.d(this, GlobalUtil.getInstance().getAESKey());

                String validation = java.net.URLEncoder.encode(
                        AESOperator.getInstance().encrypt(jsonObject.toString()).replaceAll("\n", "愚"));

                java.net.URL url = new java.net.URL(URL.getLogOutURL(jsonObject.toString(),
                        validation,
                        ""));

                con = (HttpURLConnection) url.openConnection();
                log.d(this, AESOperator.getInstance().encrypt(jsonObject.toString()));
                log.d(this, "解密："+AESOperator.getInstance().decrypt(AESOperator.getInstance().encrypt(jsonObject.toString())));
                log.d(this, URL.getLogOutURL(jsonObject.toString(),
                        validation,
                        ""));
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("GET");
                con.setRequestProperty("contentType", "GBK");


                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        UserConfig mUserConfig = new UserConfig(SettingActivity.this);
                        mUserConfig.clear();
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        SettingActivity.this.finish();

                    } else {
                        Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
