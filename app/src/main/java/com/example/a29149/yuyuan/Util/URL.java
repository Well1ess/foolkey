package com.example.a29149.yuyuan.Util;

/**
 * Created by 张丽华 on 2017/4/30.
 * Description:
 */

public class URL {
    public static final String address = "";

    //获取公钥
    public static final String publicKeyURL = "http://" + address + "/getKey?";
    //注册
    public static final String registerURL = "http://" + address + "/rsa/register?cipherText=";
    //登陆
    public static final String loginURL = "http://" + address + "/rsa/login?";

    //获取公钥
    public static String getPublicKeyURL() {
        return publicKeyURL;
    }

    //进行注册
    public static String getRegisterURL(String cipherText) {
        return registerURL + cipherText;
    }

    //登陆请求
    public static String getLoginURL(String cipherText){
        return loginURL+ "cipherText="+cipherText;
    }
}
