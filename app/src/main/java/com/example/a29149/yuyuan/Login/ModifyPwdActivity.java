package com.example.a29149.yuyuan.Login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;


public class ModifyPwdActivity extends AbstractAppCompatActivity {

    @ViewInject(R.id.et_userName)
    private EditText mUserName;

    @ViewInject(R.id.code)
    private EditText mCode;
    private Drawable mCodeDrawableLeft, mCodeDrawableRight;
    //验证码是否经过验证的标志位
    private boolean mValidateTag = false;

    @ViewInject(R.id.et_passWord)
    private EditText mPassWord;

    @ViewInject(R.id.confirm_password)
    private EditText mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        initDrawable();
        setCodeValidate();
    }

    @OnClick(R.id.ib_return)
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

        String userName = mUserName.getText().toString();
        String password = mPassWord.getText().toString();
        String confirm = mConfirm.getText().toString();
        String code = mCode.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid
        if (TextUtils.isEmpty(userName)) {
            mUserName.setError("账号不能为空！");
            focusView = mUserName;
            cancel = true;
        } else if (TextUtils.isEmpty(code)) {
            mCode.setError("验证码不能为空！");
            focusView = mCode;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPassWord.setError("密码不能为空！");
            focusView = mPassWord;
            cancel = true;
        } else if (TextUtils.isEmpty(confirm)) {
            mConfirm.setError("确认密码不能为空！");
            focusView = mConfirm;
            cancel = true;
        } else if (!password.equals(confirm)) {
            mPassWord.setError("两次输入的密码不同！");
            focusView = mPassWord;
            cancel = true;
            mConfirm.setError("两次输入的密码不同！");
        } else if (mValidateTag == false) {
            mCode.setError("验证失败");
            focusView = mCode;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //TODO:网络传输
        }
    }

    private void initDrawable() {
        //获取Drawable,left,top.right,bottom
        mCodeDrawableLeft = mCode.getCompoundDrawables()[0];
        mCodeDrawableRight = mCode.getCompoundDrawables()[2];

        mCode.setCompoundDrawablesWithIntrinsicBounds(mCodeDrawableLeft, null,
                null, null);
    }

    /**
     * 对验证码进行异步验证
     */
    private void setCodeValidate() {

        mCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //验证码不为空且失去焦点，则进行验证
                if (hasFocus == false && !mCode.getText().toString().equals("")) {
                    mCode.setCompoundDrawablesWithIntrinsicBounds(mCodeDrawableLeft, null,
                            null, null);
                    //TODO:网络验证
                    //模拟网络验证
                    if (mCode.getText().toString().equals("6666")) {
                        mValidateTag = true;
                        mCode.setCompoundDrawablesWithIntrinsicBounds(mCodeDrawableLeft, null,
                                mCodeDrawableRight, null);
                    } else {
                        mCode.setError("验证失败");
                    }
                }
            }
        });
    }
}
