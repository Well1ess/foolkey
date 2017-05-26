package com.example.a29149.yuyuan.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a29149.yuyuan.Main.SplashActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.RefreshSelfInfo.RefreshSelfInfo;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.PhoneFormatCheckUtils;
import com.example.a29149.yuyuan.Util.Secret.AESCoder;
import com.example.a29149.yuyuan.Util.Secret.SHA1Coder;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.LogInController;
import com.example.resource.util.image.GlideCircleTransform;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

import static com.example.a29149.yuyuan.Util.HttpSender.send;

/**
 * LoginActivity:登陆
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * 第一步：获取公玥
     * 第二步：对密码进行SHA1加密
     * 第三部：生成AESKey，
     * 第四部：nu，up，aes分别使用龚玥加密
     * 第五步：{un， up， aeskey}发送
     */

    public static final String defaultPhoto = PictureInfoBO.getDefaultPicCloudPath(0);

    private String strUserName;
    private String strPassWord;

    //上一个输入的用户名，记录放置频繁刷新
    private String lastUserName = "";

    @ViewInject(R.id.username)
    private EditText mUserNameView;

    @ViewInject(R.id.password)
    private EditText mPasswordView;

    //Glide依赖
    private RequestManager glide;
    //寻找图片的BO
    private PictureInfoBO pictureInfoBO = new PictureInfoBO();

    //华哥画的圈，现在可以注入照片了
    @ViewInject(R.id.photo_circle)
    private ImageView imageView;

    //获取用户配置
    UserConfig userConfig;
    //等待提示，华哥的跳跳跳动画
    public ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        //如果进入到这个页面，则清空所有的activity
        AppManager.getInstance().finishAllActivity();

        //图片加载器
        glide = Glide.with(this);

        mUserNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    //更新头像
                    autoUpdatePhoto();

                }
            }
        });



        //浅出效果，不然会有黄色一闪而过
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//        alphaAnimation.setDuration(1000);
//        alphaAnimation.setFillAfter(true);
//        imageView.setAnimation(alphaAnimation);
//        alphaAnimation.start();
        imageView.setVisibility(View.VISIBLE);
        //用glide动态地加载图片
        if (mUserNameView.getText() == null || mUserNameView.getText().toString().equals("")) {
            glide.load(R.drawable.photo_placeholder1)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new GlideCircleTransform(this))
                    .crossFade(3000)
                    .into(imageView);
        }else {
            //如果用户名栏已经有了输入
            glide.load( PictureInfoBO.getOnlinePhoto( mUserNameView.getText().toString() ) )
                    .error(R.drawable.photo_placeholder1)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new GlideCircleTransform(this))
                    .crossFade(3000)
                    .into(imageView);
        }

        //华哥的跳跳跳动画
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.getDialog().setCancelable(false);

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
//            RefreshSelfInfo refreshSelfInfo = new RefreshSelfInfo(this);
//            refreshSelfInfo.execute();
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
        AppManager.getInstance().addActivity(this);
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
        if (TextUtils.isEmpty(userName)
//                || !PhoneFormatCheckUtils.isPhoneLegal( strUserName )
                ) {
            mUserNameView.setError("请输入正确的用户名^_^");
            focusView = mUserNameView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("密码不能为空哦");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //显示华哥的跳跳跳动画
            shapeLoadingDialog.show();
            //TODO:网络传输
            strUserName = userName;
            strPassWord = password;
            GetPublicKeyAction getPublicKeyAction = new GetPublicKeyAction();
            getPublicKeyAction.execute();
        }
    }

    /**
     * 点击事件
     * 并没有什么作用
     * @param view
     */
    @Override
    public void onClick(View view) {
//        imageView.setVisibility(View.INVISIBLE);
        autoUpdatePhoto();
    }

    /**
     * 加载图片
     * @param userName
     */
    private void loadImage( String userName ){
//        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(getResources()
//                , BitmapFactory.decodeResource(getResources()
//                , R.drawable.photo_placeholder1));
//        circleDrawable.getPaint().setAntiAlias(true);
//        circleDrawable.setCircular(true);
//        imageView.setImageDrawable(circleDrawable);

        //用glide动态地加载图片
        String url = pictureInfoBO.getPhotoURL( userName );
        System.out.println(this.getClass() + "   取到的url是");
        System.out.println(url);

        imageView.setVisibility( View.VISIBLE );
//        imageView.setAlpha(0.0f);
        //浅出效果，不然会有黄色一闪而过
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        imageView.setAnimation(alphaAnimation);
        alphaAnimation.start();

        glide.load( url )
//                .asBitmap()
//                .error(R.drawable.photo_placeholder1)
//                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .error(R.drawable.photo_placeholder1)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .crossFade(2000)
//                .animate(1000)
                .dontAnimate()

                .into(imageView);
    }

    /**
     * 根据用户名决定是否值得更新头像
     */
    private void autoUpdatePhoto(){
        //获取输入
        String userName = mUserNameView.getText().toString();
        if (userName != null && !userName.equals("")
                && !userName.equals(lastUserName)
                && !userName.equals("username")
                ){
            //输入了新的用户名，可以刷新图片了
            lastUserName = userName;
            loadImage( lastUserName );
        }
    }





    /**
     * 获取公钥
     */
    private class GetPublicKeyAction extends AsyncTask<String, Integer, String> {

        GetPublicKeyAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return send(URL.publicKeyURL, new JSONObject());
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            log.d(this, result);
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
                shapeLoadingDialog.dismiss();
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
    private class LoginAction extends AsyncTask<String, Integer, String> {

        LoginAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //对密码进行SHA1加密
                String password = SHA1Coder.SHA1(strPassWord);

                return LogInController.execute(
                        strUserName,
                        password,
                        GlobalUtil.getInstance().getAESKey()
                );

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //跳跳跳的动画不再显示
            shapeLoadingDialog.dismiss();
            log.d(this, result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    Log.i("malei",resultFlag);
                    String token = jsonObject.getString("token");
                    Log.i("malei",token);
                    /*//获取学生信息DTO
                    Log.i("malei",jsonObject.getString("StudentDTO"));
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                    }.getType();
                    StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("StudentDTO"), type);
                    //存储学生信息DTO
                    GlobalUtil.getInstance().setStudentDTO(studentDTO);
                    //获取老师信息DTO
                    java.lang.reflect.Type type1 = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                    }.getType();
                    TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("TeacherDTO"), type1);
                    Log.i("malei",jsonObject.getString("TeacherDTO"));
                    if(teacherDTO != null)
                    {
                        //存储老师DTO
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                    }*/
                    //获取id,用以推送
                    String id = jsonObject.getString("id");
                    Log.i("malei","id"+id);


                    if (resultFlag.equals("success") && !token.equals("") && !id.equals("")) {
                        log.d(this, "AESKey:" + GlobalUtil.getInstance().getAESKey());

                        //保存Token
                        GlobalUtil.getInstance().setToken(token);

                        log.d(this, token + " ");
                        Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                        //登录成功后刷新用户信息
                        new RefreshSelfInfo(LoginActivity.this).execute();

                        //保存id
                        GlobalUtil.getInstance().setId(id);
                        //注册用户账号用于小米推送
                        if(!TextUtils.isEmpty(id))
                            MiPushClient.setUserAccount(LoginActivity.this, id, null);

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



                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
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

