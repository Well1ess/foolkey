package com.example.a29149.yuyuan.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * LoginActivity:登陆
 */
public class LoginActivity extends AppCompatActivity{
    /**
     * 第一步：获取公玥
     * 第二步：对密码进行SHA1加密
     * 第三部：生成AESKey，
     * 第四部：nu，up，aes分别使用龚玥加密
     * 第五步：{un， up， aeskey}发送
     */

    String URL = "http://10.53.183.185:8080/rsa/login?";

    @ViewInject(R.id.username)
    private EditText mUserNameView;

    @ViewInject(R.id.password)
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

    }

    @OnClick(R.id.modify_pwd)
    public void setModifyPwd(View view)
    {
        Intent modify = new Intent(this, ModifyPwdActivity.class);
        startActivity(modify);
    }

    @OnClick(R.id.register)
    public void setRegisterListener(View view)
    {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    @OnClick(R.id.sign_in_button)
    public void setLoginListener(View view)
    {
        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError("账号不能为空！");
            focusView = mUserNameView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("密码不能为空！");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
          //TODO:网络传输
            Intent startMainActivity = new Intent(this, MainActivity.class);
            startActivity(startMainActivity);
        }

    }

    /**
     * 登陆请求Action
     */
    public class LoginAction extends AsyncTask<String, Integer, String> {

        public LoginAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                java.net.URL url = new java.net.URL("");
                con = (HttpURLConnection) url.openConnection();
                log.d(this, "");
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}

