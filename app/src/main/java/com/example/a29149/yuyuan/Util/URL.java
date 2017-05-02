package com.example.a29149.yuyuan.Util;

/**
 * Created by 张丽华 on 2017/4/30.
 * Description:
 */

public class URL {
    public static final String address = "192.168.31.30:8080";

    //获取公钥
    public static final String publicKeyURL = "http://" + address + "/getKey?";
    //注册
    public static final String registerURL = "http://" + address + "/rsa/register?";
    //登陆
    public static final String loginURL = "http://" + address + "/rsa/login?";
    //注销
    public static final String logOutURL = "http://" + address + "/aes/logOut?";
    //获取折扣卷
    public static final String couponURL = "http://" + address + "/aes/getMyCoupon?";
    //更新个人信息/获取个人信息
    public static final String selfInfoURL = "http://" + address + "/aes/getMyInfo?";
    //学生发布悬赏
    public static final String studentPublishXuanshangURL = "http://" + address + "/courseStudent/publishRewardCourse?";

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

    //获取学生发布悬赏
    public static String getStudentPublishXuanshangURL(String clearText) {
        return studentPublishXuanshangURL+ "clearText=" + clearText;
    }

    //注销

    /**
     * @param clearText  明文Object，包含token
     * @param validation 密文
     * @param cipherText 密文
     * @return
     */
    public static String getLogOutURL(String clearText, String validation, String cipherText) {
        return logOutURL + "clearText=" + clearText +
                "&validation=" + validation +
                "&cipherText=" + cipherText;
    }

    //优惠券
    public static String getCouponURL(String pageNo, String pageSize) {
        return couponURL + AESTransformResult.getResult("", "pageNo", pageNo, "pageSize", pageSize);
    }

    //获取个人信息
    public static String getSelfInfoURL() {
        return selfInfoURL + AESTransformResult.getResult("");
    }
}
