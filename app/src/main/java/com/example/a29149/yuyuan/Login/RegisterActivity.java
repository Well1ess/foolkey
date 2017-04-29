package com.example.a29149.yuyuan.Login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESCoder;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.Secret.RSAKeyBO;
import com.example.a29149.yuyuan.Util.Secret.SHA1Coder;
import com.example.a29149.yuyuan.Util.log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class RegisterActivity extends AppCompatActivity {

    String address = "www.foopia.com";
    String URL = "http://" + address +"/getKey?";
    String URL2 = "http://" + address + "/rsa/register?cipherText=";

    String strUserName;
    String strPassword;


    @ViewInject(R.id.username)
    private EditText mUserName;

    @ViewInject(R.id.password)
    private EditText mPassWord;

    @ViewInject(R.id.confirm_password)
    private EditText mConfirm;

    @ViewInject(R.id.code)
    private EditText mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
    }

    @OnClick(R.id.bt_return)
    public void setCancelListener(View view) {
        this.finish();
    }

    @OnClick(R.id.commit)
    public void setCommitListener(View view) {
        // Reset errors.
        mUserName.setError(null);
        mPassWord.setError(null);
        mConfirm.setError(null);
        mCode.setError(null);

        strUserName = mUserName.getText().toString();
        strPassword = mPassWord.getText().toString();
        String confirm = mConfirm.getText().toString();
        String code = mCode.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid
        if (TextUtils.isEmpty(strUserName)) {
            mUserName.setError("账号不能为空！");
            focusView = mUserName;
            cancel = true;
        } else if (TextUtils.isEmpty(strPassword)) {
            mPassWord.setError("密码不能为空！");
            focusView = mPassWord;
            cancel = true;
        } else if (TextUtils.isEmpty(confirm)) {
            mConfirm.setError("确认密码不能为空！");
            focusView = mConfirm;
            cancel = true;
        } else if (TextUtils.isEmpty(code)) {
            mCode.setError("验证码不能为空！");
            focusView = mCode;
            cancel = true;
        } else if (!strPassword.equals(confirm)) {
            mPassWord.setError("两次输入的密码不同！");
            focusView = mPassWord;
            cancel = true;
            mConfirm.setError("两次输入的密码不同！");
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //TODO:网络传输
            RegisterAction registerAction = new RegisterAction();
            registerAction.execute();
        }
    }

    /**
     * 注册请求Action
     */
    public class RegisterAction extends AsyncTask<String, Integer, String> {

        public RegisterAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                java.net.URL url = new java.net.URL(URL);
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL);
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
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    String publicKey = jsonObject.getString("publicKey");


                    if (resultFlag.equals("success")) {
                        if (!publicKey.equals("")) {
                            GlobalUtil.getInstance().setPublicKey(publicKey);

                            try {
                                AESCoder coder = new AESCoder();
                                //String AESKey = coder.getAESKeyBase64();
                                //String AESKey = "smkldospdosldaaa";
                                GlobalUtil.getInstance().setAESKey(coder.getRandomString(16));

                                String AESKey = GlobalUtil.getInstance().getAESKey();

                                log.d(this, "AESKey:" + AESKey);
                                log.d(this, "publicKey:" + GlobalUtil.getInstance().getPublicKey());

                                String encrypt = RSAKeyBO.encryptByPub(AESKey, GlobalUtil.getInstance().getPublicKey());

                                log.d(this, "encryptByPub:" + encrypt);

                                String convert = encrypt.replaceAll("\n", "愚");
                                log.d(this, "convert:" + convert);
                                log.d(this, "convert2:" + java.net.URLEncoder.encode(convert));

                                RegisterAction2 registerAction2 = new RegisterAction2();
                                registerAction2.execute(java.net.URLEncoder.encode(convert));

                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(RegisterActivity.this, "公玥获取失败！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 注册请求Action
     */
    public class RegisterAction2 extends AsyncTask<String, Integer, String> {

        public RegisterAction2() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                strPassword = SHA1Coder.SHA1(strPassword);

                JSONObject target = new JSONObject();
                target.put("userName", java.net.URLEncoder.encode(
                        RSAKeyBO.encryptByPub(strUserName, GlobalUtil.getInstance().getPublicKey())
                                .replaceAll("\n", "愚")));
                target.put("passWord", java.net.URLEncoder.encode(
                        RSAKeyBO.encryptByPub(strPassword, GlobalUtil.getInstance().getPublicKey())
                                .replaceAll("\n", "愚")));
                target.put("AESKey", params[0]);


                log.d(this, target.toString());

                java.net.URL url = new java.net.URL(URL2 + target.toString());
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL2 + target.toString());
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
                    String token = jsonObject.getString("tokenCipher");

                    if (resultFlag.equals("success") && !token.equals("")) {
                        log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());

                        //AESKeyBO aesKeyBO = new AESKeyBO();
                        //String TOKEN = aesKeyBO.decrypt(token, GlobalUtil.getInstance().getAESKey());

                        String TOKEN = AESOperator.getInstance().decrypt(token);

                        GlobalUtil.getInstance().setToken(TOKEN);
                        log.d(this, TOKEN + " ");
                        Toast.makeText(RegisterActivity.this, TOKEN, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
