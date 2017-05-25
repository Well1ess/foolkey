package com.example.a29149.yuyuan.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.Main.ImageUploadActivity;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.RefreshSelfInfo.RefreshSelfInfo;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.PhoneFormatCheckUtils;
import com.example.a29149.yuyuan.Util.Secret.AESCoder;
import com.example.a29149.yuyuan.Util.Secret.SHA1Coder;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.RegisterController;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

import static com.example.a29149.yuyuan.Login.LoginActivity.defaultPhoto;

/**
 * 现在注册成功后，会发送一次refresh请求，以便填充全局中的studentDTO
 */
public class RegisterActivity extends AppCompatActivity {

    private String strUserName;
    private String strPassword;

    //用户配置
    UserConfig userConfig;

    @ViewInject(R.id.username)
    private EditText mUserName;

    //来个随机的默认头像
    private Integer defaultPicNum = (int) ( Math.random() * PictureInfoBO.defaultPicNum );


    @ViewInject(R.id.password)
    private EditText mPassWord;

    @ViewInject(R.id.confirm_password)
    private EditText mConfirm;

    @ViewInject(R.id.code)
    private EditText mCode;

    //头像
    @ViewInject(R.id.photo_circle)
    private ImageView imageView;
    //是否设置了头像
    private boolean hasPhoto = false;


    //Glide依赖
    private RequestManager glide;
    //寻找图片的BO
    private PictureInfoBO pictureInfoBO = new PictureInfoBO();

    //等待提示,华哥的跳跳跳动画
    public ShapeLoadingDialog shapeLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        userConfig = new UserConfig(RegisterActivity.this);

        //等待提示,华哥的跳跳跳动画
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.getDialog().setCancelable(false);

        //图片加载器
        glide = Glide.with(this);

        //浅出效果，不然会有黄色一闪而过
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        imageView.setAnimation(alphaAnimation);
        imageView.setAlpha(0.0f);
        imageView.setVisibility(View.VISIBLE);
        alphaAnimation.start();
        imageView.setAlpha(1.0f);

        //用glide动态地加载图片
        glide.load( PictureInfoBO.getDefaultPicCloudPath(defaultPicNum) )
                .transform(new GlideCircleTransform(this))
//                .dontAnimate()
                .crossFade(2000)
                .into(imageView);



        //手机号输入框
        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    //查询是否注册



                    //判空
                    if ( mUserName.getText() != null){
                        strUserName = mUserName.getText().toString();
                        //验证输入的是否是手机号
                        if ( PhoneFormatCheckUtils.isPhoneLegal( strUserName ) ){
                            //strUserName = mUserName.getText().toString();
                        }
                    }else {
                        //输入不合法
                        mUserName.setError("请输入正确的手机号");
                    }
                }
            }
        });
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
        if (TextUtils.isEmpty(strUserName) || !PhoneFormatCheckUtils.isPhoneLegal( strUserName )) {
            mUserName.setError("手机号输入错误");
            focusView = mUserName;
            cancel = true;
        } else if (TextUtils.isEmpty(strPassword)) {
            mPassWord.setError("密码不可为空");
            focusView = mPassWord;
            cancel = true;
        } else if (TextUtils.isEmpty(confirm)) {
            mConfirm.setError("确认密码不能为空");
            focusView = mConfirm;
            cancel = true;
        } else if (
//                TextUtils.isEmpty(code)
                    false
                ) {
            mCode.setError("验证码不能为空！");
            focusView = mCode;
            cancel = true;
        } else if (!strPassword.equals(confirm)) {
            mPassWord.setError("两次输入的密码不同！");
            focusView = mPassWord;
            cancel = true;
            mConfirm.setError("两次输入的密码不同！");
        }

        //要求上传图片
        if (!GlobalUtil.getInstance().isUploadPhotoFlag()){
//            Toast.makeText(RegisterActivity.this, "请点击图片，上传头像", Toast.LENGTH_SHORT).show();
//            return;
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
     * 调用上传图片的activity
     * 要放置extra
     * @param view
     */
    @OnClick(R.id.photo_circle)
    public void uploadPhoto(View view){

        //不让用户上传头像了


//        System.out.println(this.getClass() + "209行" + mUserName.getText().toString());
//        //判空
//        if ( mUserName.getText() != null
//                && !mUserName.getText().toString().equals("")
////                && PhoneFormatCheckUtils.isPhoneLegal( mUserName.getText().toString() )
//
//                ){
//            if ( PhoneFormatCheckUtils.isPhoneLegal( mUserName.getText().toString() ) )
//                strUserName = mUserName.getText().toString();
//            else {
//                Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }else {
//            //输入不合法
//            Toast.makeText(RegisterActivity.this, "请先输入手机号", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Intent intent = new Intent(this, ImageUploadActivity.class );
//        intent.putExtra("userName", strUserName);
//
//        startActivity( intent );

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

                if (!GlobalUtil.getInstance().isUploadPhotoFlag()){
                    //用户没有选择上传图片
                    return RegisterController.execute(
                            strUserName,
                            password,
                            params[0],
                            defaultPicNum + ""
                    );
                }else {
                    //用户自己上传了图片
                    return RegisterController.execute(
                            strUserName,
                            password,
                            params[0],
                            null
                    );
                }


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


                        //这里需要弄一下上传


                        AppManager.getInstance().finishActivity(LoginActivity.class);

                        //等待提示,华哥的跳跳跳动画，消失
                        shapeLoadingDialog.dismiss();



                        RefreshSelfInfo refreshSelfInfo = new RefreshSelfInfo(RegisterActivity.this);
                        refreshSelfInfo.execute();

                        AppManager.getInstance().addActivity(RegisterActivity.this);

//                        Intent intent = new Intent(RegisterActivity.this, MainStudentActivity.class);
//                        startActivity(intent);
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
