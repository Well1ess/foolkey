package com.example.a29149.yuyuan.AbstractObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.a29149.yuyuan.Util.AppManager;
import com.example.resource.DataHelper;
import com.example.resource.util.AsyncHttpClient;
import com.example.resource.util.HttpMethod;

/**
 * Created by 张丽华 on 2017/5/19.
 * Description:
 */

public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    public void SS() {
        new AsyncHttpClient(this, "URL", HttpMethod.POST)
                .setOnHttpResultListener(new AsyncHttpClient.OnHttpResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        new DataHelper<String>().save(result, "JsonName", new DataHelper.Callback() {
                            @Override
                            public void onResult(int resultCode) {
                                switch (resultCode) {
                                    case DataHelper.SUCCESS:

                                        break;
                                    case DataHelper.FAILURE:

                                        break;
                                    case DataHelper.EXCEPTION:

                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure() {

                    }
                })
                .setOnResultExceptionListener(new AsyncHttpClient.OnResultExceptionListener() {
                    @Override
                    public void onException(@Nullable Exception e) {

                    }
                })
                .send();
    }

    private Fragment mCurrentFragment; //暂存当前的fragment

    /**
     * 切换Fragment的方法
     *
     * @param containerViewId containerView的R.id
     * @param yyFragment      目标fragment
     * @return 切换是否正确
     * @author shs1330
     * @time 2017/6/7 15:43
     */
    protected boolean switchFragment(int containerViewId, Fragment yyFragment) {
        if (yyFragment == null)
            return false;

        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        //判断是否为第一次启动，若mCurrentFragment不为空则为第一次启动
        if (mCurrentFragment != null) {
            //先隐藏当前Fragment
            fTransaction.hide(mCurrentFragment);
        }
        //目标Fragment如果以前没有被添加过
        if (!yyFragment.isAdded()) {
            //先添加
            fTransaction.add(containerViewId, yyFragment);
        }
        //随后显示
        fTransaction.show(yyFragment).commit();
        //保存至当前Fragment
        mCurrentFragment = yyFragment;
        return true;
    }
}
