package com.example.a29149.yuyuan.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 张丽华 on 2016/12/21 0021.
 */

public class UserConfig {

    public static final String xmlPUBLIC_KEY = "publicKey";
    public static final String xmlAES_KEY = "publicKey";
    public static final String xmlUSER_NAME = "username";
    public static final String xmlPASSWORD = "password";
    public static final String xmlSAVE = "save";

    private String USER_CONFIG = "UserConfig";
    private Context context;

    public UserConfig() {
    }

    public UserConfig(Context context) {
        this.context = context;
    }

    public void setUserInfo(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }

    public void setUserInfo(String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public String getStringInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public boolean getBooleanInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
