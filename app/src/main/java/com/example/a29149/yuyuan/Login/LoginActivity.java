package com.example.a29149.yuyuan.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.RefreshSelfInfo.RefreshSelfInfo;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESCoder;
import com.example.a29149.yuyuan.Util.Secret.RSAKeyBO;
import com.example.a29149.yuyuan.Util.Secret.SHA1Coder;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

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

    private String strUserName;
    private String strPassWord;

    @ViewInject(R.id.username)
    private EditText mUserNameView;

    @ViewInject(R.id.password)
    private EditText mPasswordView;

    //获取用户配置
    UserConfig userConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        userConfig = new UserConfig(this);
        if (userConfig.getBooleanInfo(UserConfig.xmlSAVE)) {
            log.d(this, userConfig.getStringInfo(UserConfig.xmlPUBLIC_KEY));
            log.d(this, userConfig.getStringInfo(UserConfig.xmlAES_KEY));
            log.d(this, userConfig.getStringInfo(UserConfig.xmlUSER_NAME));
            log.d(this, userConfig.getStringInfo(UserConfig.xmlPASSWORD));

            strUserName = userConfig.getStringInfo(UserConfig.xmlUSER_NAME);
            strPassWord = userConfig.getStringInfo(UserConfig.xmlPASSWORD);
            GlobalUtil.getInstance().setPublicKey(userConfig.getStringInfo(UserConfig.xmlPUBLIC_KEY));
            GlobalUtil.getInstance().setAESKey(userConfig.getStringInfo(UserConfig.xmlAES_KEY));
            GlobalUtil.getInstance().setToken(userConfig.getStringInfo(UserConfig.xmlTOKEN));

            mUserNameView.setText(strUserName);
            mPasswordView.setText(strPassWord);

            log.d(this, strUserName);
            log.d(this, strPassWord);

            //TODO：直接进行网络传输
            RefreshSelfInfo refreshSelfInfo = new RefreshSelfInfo(this);
            refreshSelfInfo.execute();
        }

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
            strUserName = userName;
            strPassWord = password;
            GetPublicKeyAction getPublicKeyAction = new GetPublicKeyAction();
            getPublicKeyAction.execute();
        }

    }

    /**
     * 获取公钥
     */
    public class GetPublicKeyAction extends AsyncTask<String, Integer, String> {

        public GetPublicKeyAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                java.net.URL url = new java.net.URL(URL.getPublicKeyURL());
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getPublicKeyURL());
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

                            //对公钥的操作保存在单例类中
                            GlobalUtil.getInstance().setPublicKey(publicKey);

                            try {
                                AESCoder coder = new AESCoder();

                                //对AESKey的操作保存在单例中
                                GlobalUtil.getInstance().setAESKey(coder.getRandomString(16));

                                log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());
                                log.d(this, "publicKey:" + GlobalUtil.getInstance().getPublicKey());

                                //执行登陆动作
                                LoginAction loginAction = new LoginAction();
                                loginAction.execute();

                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(LoginActivity.this, "公玥获取失败！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
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

                //对密码进行SHA1加密
                String password = SHA1Coder.SHA1(strPassWord);

                JSONObject target = new JSONObject();
                //对账号，密码先进行RSA加密，再进行替换，随后是UTF-8编码
                target.put("userName", java.net.URLEncoder.encode(
                        RSAKeyBO.encryptByPub(strUserName, GlobalUtil.getInstance().getPublicKey())
                                .replaceAll("\n", "愚")));
                target.put("passWord", java.net.URLEncoder.encode(
                        RSAKeyBO.encryptByPub(password, GlobalUtil.getInstance().getPublicKey())
                                .replaceAll("\n", "愚")));
                target.put("AESKey", java.net.URLEncoder.encode(
                        RSAKeyBO.encryptByPub(GlobalUtil.getInstance().getAESKey(),
                                GlobalUtil.getInstance().getPublicKey())
                                .replaceAll("\n", "愚")));

                java.net.URL url = new java.net.URL(URL.getLoginURL(target.toString()));
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getLoginURL(target.toString()));

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
                    String token = jsonObject.getString("token");
                    //获取id,用以推送
                    String id = jsonObject.getString("id");
                    Log.i("malei",id);
                    //设置用户账号用于小米推送
                    if(!TextUtils.isEmpty(id))
                         MiPushClient.setUserAccount(LoginActivity.this, id, null);


                    if (resultFlag.equals("success") && !token.equals("")) {
                        log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());

                        //保存Token
                        GlobalUtil.getInstance().setToken(token);

                        log.d(this, token + " ");
                        Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();

                        userConfig.clear();

                        //保存公钥到文件中
                        userConfig.setUserInfo(UserConfig.xmlPUBLIC_KEY, GlobalUtil.getInstance().getPublicKey());
                        //保存AES到文件中
                        userConfig.setUserInfo(UserConfig.xmlAES_KEY, GlobalUtil.getInstance().getAESKey());
                        //保存Token
                        userConfig.setUserInfo(UserConfig.xmlTOKEN, token);
                        //保存非加密的用户名和密码
                        userConfig.setUserInfo(UserConfig.xmlUSER_NAME, strUserName);
                        userConfig.setUserInfo(UserConfig.xmlPASSWORD, strPassWord);
                        //设置标志位
                        userConfig.setUserInfo(UserConfig.xmlSAVE, true);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();

                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}

