package com.example.a29149.yuyuan.AbstractObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.a29149.yuyuan.Util.AppManager;

/**
 * 一个继承Activity的抽象类
 * 应让所有继承Activity的类，都转而继承这个类
 * Created by geyao on 2017/6/8.
 */

public abstract class AbstractActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }
}
