package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.Model.Publish.Activity.PublishRewardOptionsStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师申请接单悬赏
 */
public class ApplyRewardTeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private  Button mApplyReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_reward_teacher);
        initView();
    }

    private void initView() {
        mApplyReward = (Button) findViewById(R.id.bt_apply);
        mApplyReward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_apply:
                applyRewardTeacher();
                break;
            default:
                break;
        }
    }

    //老师申请接单悬赏
    private void applyRewardTeacher() {
        //验证身份
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();
        if(teacherDTO != null)
        {
            String verifyState = teacherDTO.getVerifyState();
            //如果是已认证老师或者是认证中的老师，则直接接单
            if(verifyState.equals("processing") || verifyState.equals("verified"))
            {
                new ApplyRewardAction().execute();
            }
            else
            {
                Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ApplyRewardTeacherActivity.this, ApplyAuthenticationTeacherActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            //不是已认证老师，跳转到申请认证页面
            Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ApplyRewardTeacherActivity.this, ApplyAuthenticationTeacherActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 申请悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {


        public ApplyRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token",token);
                target.put("courseId","5");
                //加密
                String validation = java.net.URLEncoder.encode(
                        AESOperator.getInstance().encrypt(target.toString()).replaceAll("\n", "愚"));

                java.net.URL url = new java.net.URL(URL.getApplyRewardTeacherURL(target.toString()
                        ,validation,""));
                Log.i("malei",target.toString());
                Log.i("malei",validation);
                con = (HttpURLConnection) url.openConnection();
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("POST");
                con.setRequestProperty("contentType", "UTF-8");

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
                        Toast.makeText(ApplyRewardTeacherActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ApplyRewardTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyRewardTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
