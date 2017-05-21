package com.example.a29149.yuyuan.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.Secret.AESCoder;
import com.example.a29149.yuyuan.Util.Secret.SHA1Coder;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.controller.userInfo.RegisterController;

import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    private String strUserName;
    private String strPassword;

    //用户配置
    UserConfig userConfig;

    @ViewInject(R.id.username)
    private EditText mUserName;

    @ViewInject(R.id.password)
    private EditText mPassWord;

    @ViewInject(R.id.confirm_password)
    private EditText mConfirm;

    @ViewInject(R.id.code)
    private EditText mCode;

    //等待提示
    public ShapeLoadingDialog shapeLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        userConfig = new UserConfig(RegisterActivity.this);

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.getDialog().setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance().removeActivity(LoginActivity.class);
    }

    @OnClick(R.id.bt_return)
    public void setCancelListener(View view) {

        this.onBackPressed();
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
            //显示
            shapeLoadingDialog.show();
            //TODO:网络传输
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
            try {
                //传输一个空的json，获取key
                return HttpSender.send(URL.publicKeyURL, new JSONObject());
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
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

                                //对AESKey的操作保存在单例中
                                GlobalUtil.getInstance().setAESKey(AESCoder.getRandomString(16));

                                log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());
                                log.d(this, "publicKey:" + GlobalUtil.getInstance().getPublicKey());
                                //获取AES密钥
                                String encrypt = GlobalUtil.getInstance().getAESKey();

                                log.d(this, "encryptByPub:" + encrypt);

                                //执行注册动作
                                RegisterAction registerAction = new RegisterAction();
                                registerAction.execute(encrypt);

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
    public class RegisterAction extends AsyncTask<String, Integer, String> {

        public RegisterAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                log.d(this, "P:" + strPassword);
                log.d(this, "U:" + strUserName);
                String password = SHA1Coder.SHA1(strPassword);

                return RegisterController.execute(
                        strUserName,
                        password,
                        params[0]
                );

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            shapeLoadingDialog.dismiss();
            log.d(this, result);
            if (result != null) {
                try {
                    log.d(this, "result========="+result);
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    String token = jsonObject.getString("tokenCipher");

                    if (resultFlag.equals("success") && !token.equals("")) {
                        log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());

                       /* //进行解密
                        String TOKEN = AESOperator.getInstance().decrypt(token);*/
                        String TOKEN = token;

                        //保存Token
                        GlobalUtil.getInstance().setToken(TOKEN);

                        log.d(this, TOKEN + " ");
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                        //保存公钥到文件中
                        userConfig.setUserInfo(UserConfig.xmlPUBLIC_KEY, GlobalUtil.getInstance().getPublicKey());
                        //保存AES到文件中
                        userConfig.setUserInfo(UserConfig.xmlAES_KEY, GlobalUtil.getInstance().getAESKey());
                        //保存token
                        userConfig.setUserInfo(UserConfig.xmlTOKEN, token);
                        //保存非加密的用户名和密码
                        userConfig.setUserInfo(UserConfig.xmlUSER_NAME, strUserName);
                        userConfig.setUserInfo(UserConfig.xmlPASSWORD, strPassword);
                        //设置标志位
                        userConfig.setUserInfo(UserConfig.xmlSAVE, true);


                        AppManager.getInstance().finishActivity(LoginActivity.class);
                        RegisterActivity.this.finish();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
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
