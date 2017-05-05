package com.example.a29149.yuyuan.Model.Me.Recharge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class RechargeActivity extends AppCompatActivity {

    @ViewInject(R.id.money)
    private EditText mMoney;

    Intent intent;

    String money = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
    }

    @OnClick(R.id.bt_return)
    public void setBtReturnListener(View view)
    {
        this.finish();
    }

    @OnClick(R.id.recharge)
    public void setRechargeListener(View view)
    {
        if (mMoney.getText().toString().equals(""))
        {
            Toast.makeText(this, "金额不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        money = mMoney.getText().toString();
        Recharge recharge = new Recharge();
        recharge.execute();
    }

    public class Recharge extends AsyncTask<String, Integer, String> {

        public Recharge() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                java.net.URL url = new java.net.URL(URL.getRechargeURL(money));

                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getRechargeURL(money));

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

                        Toast.makeText(RechargeActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();

                        SelfInfo selfInfo = new SelfInfo();
                        selfInfo.execute();


                    } else {
                        Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class SelfInfo extends AsyncTask<String, Integer, String> {

        public SelfInfo() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                java.net.URL url = new java.net.URL(URL.getSelfInfoURL());
                con = (HttpURLConnection) url.openConnection();

                log.d(this, URL.getSelfInfoURL());

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
            } catch (Exception e) {
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

                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                        }.getType();
                        StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentDTO"), type);

                        if (studentDTO != null)
                            GlobalUtil.getInstance().setStudentDTO(studentDTO);

                        setResult(1, intent);
                        RechargeActivity.this.finish();

                    }
                } catch (Exception e) {
                    Toast.makeText(RechargeActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
