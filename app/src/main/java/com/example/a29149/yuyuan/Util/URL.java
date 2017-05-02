package com.example.a29149.yuyuan.Util;

/**
 * Created by 张丽华 on 2017/4/30.
 * Description:
 */

public class URL {
    public static final String address = "10.53.179.207:8080";

    //获取公钥
    public static final String publicKeyURL = "http://" + address + "/getKey?";
    //注册
    public static final String registerURL = "http://" + address + "/rsa/register?";
    //登陆
    public static final String loginURL = "http://" + address + "/rsa/login?";
    //注销
    public static final String logOutURL = "http://" + address + "/aes/logOut?";
    //获取折扣卷
    public static final String couponURL = "http://" +address + "";



    //获取公钥
    public static String getPublicKeyURL() {
        return publicKeyURL;
    }

    //进行注册
    public static String getRegisterURL(String cipherText) {
        return registerURL + "cipherText=" + cipherText;
    }

    //登陆请求
    public static String getLoginURL(String cipherText) {
        return loginURL + "cipherText=" + cipherText;
    }

    //注销
    /**
     * @param clearText 明文Object，包含token
     * @param validation 密文
     * @param cipherText 密文
     * @return
     */
    public static String getLogOutURL(String clearText, String validation, String cipherText) {
        return logOutURL + "clearText=" + clearText +
                "&validation=" + validation +
                "&cipherText=" + cipherText;
    }

    public static String getCouponURL() {
        return couponURL;
    }
}
