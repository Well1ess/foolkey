package com.example.a29149.yuyuan.Util;

/**
 * Created by 张丽华 on 2017/4/30.
 * Description:
 */

public class URL {

    public static final String address = "123.206.102.231:8080";

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
    //获取悬赏任务
    public static final String rewordURL = "http://" + address + "/aes/get";
    //切换身份
    public static final String switchToTeacher = "http://" + address + "/switchToTeacher?";
    //获取课程
    public static final String getHotCourseURL = "http://" + address + "/courseTeacher/getCourseTeacherPopular?";
    //学生发布悬赏
    public static final String studentPublishRewardURL = "http://" + address + "/courseStudent/publishRewardCourse?";
    //老师发布课程
    public static final String teacherPublishCoursedURL = "http://" + address + "/courseTeacher/publishCourseTeacher?";
    //搜索
    public static final String searchURL = "http://" + address + "/search?";

    public static final String studentPublishXuanshangURL = "http://" + address + "/courseStudent/publishRewardCourse?";
    //充值
    public static final String rechargeURL = "http://" + address + "/aes/recharge?";
    //提交订单
    public static final String submitOrder = "http://" + address + "/aes/placeOrderTeacherCourse?";
    //申请认证
    public static final String applyVerifyTeacherURL = "http://" + address + "/applyToVerifyTeacher?";
    //老师申请接受悬赏
    public static final String applyRewardTeacherURL = "http://" + address + "/aes/placeOrderStudentCourse?";

    //获取悬赏
    public static final String getRewardURL = "http://" +address + "/courseStudent/getRewardCoursePopular?";
    //获取我发布的悬赏
    public static final String getMyRewardURL = "http://" +address + "/application/getApplicationStudentRewardAsStudentController?";


    //获取我发布的课程
    public static final String getMyCourseURL = "http://" +address + "/order/getAgreedOnClassOrderCourseByTeacher?";

    //获取老师发布的悬赏
    public static final String getTeacherRewardURL = "http://" +address + "/order/getAgreedOnClassOrderRewardByTeacher?";



    //同意悬赏申请
    public static final String agreeApplyRewardURL = "http://" +address + "/acceptRewardApplication?";
    //拒绝悬赏申请
    public static final String disagreeApplyRewardURL = "http://" +address + "/refuseTeacherApplication?";

    //获取学生列表
    public static final String applyStudentListURL = "http://" +address + "/order/getAgreedOnClassOrderCourseByTeacher?";


    //开始上课请求
    public static final String startClassURL = "http://" +address + "/startClass?";

    //下课请求
    public static final String endClassURL = "http://" +address + "/endClass?";


    //未评价订单请求
    public static final String noCommentURL = "http://" +address + "/获取未评价的订单?";

    //未评价订单请求
    public static final String noPayCourseURL = "http://" +address + "/getStudentCourseOrder?";

    //评价老师
    public static final String commentTeacherURL = "http://" +address + "/judge/teacher?";

    //评价课程
    public static final String commentCourseURL = "http://" +address + "/judge/course?";





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
    public static String getStudentPublishRewardURL(String clearText) {
        return studentPublishRewardURL + "clearText=" + clearText;
    }

    //获取老师发布课程
    public static String getTeacherPublishCoursedURL(String clearText) {
        return teacherPublishCoursedURL + "clearText=" + clearText;
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

    //切换身份
    public static String getSwitchToTeacher() {
        return switchToTeacher + "token=" + GlobalUtil.getInstance().getToken();
    }

    //获取热门课程
    public static String getGetHotCourseURL(String str) {
        return getHotCourseURL + "clearText=" + str;
    }

    //充值
    public static String getRechargeURL(String amount) {
        return rechargeURL + AESTransformResult.getResult("", "amount", amount);
    }

    //提交订单

    /**
     * @param courseId      课程Id
     * @param amount        定点金额
     * @param number        购买数量
     * @param cutOffPercent 金额
     * @param teachMethod   教授方式
     * @param courseType    课程类型
     * @return
     */
    public static String getSubmitOrder(String courseId, String amount, String number, String cutOffPercent, String teachMethod, String courseType, String teacherId) {
        return submitOrder + AESTransformResult.getResult("",
                "courseId", courseId,
                "amount", amount,
                "number", number,
                "cutOffPercent", cutOffPercent,
                "teachMethod", teachMethod,
                "courseType", courseType,
                "teacherId", teacherId);
    }

    public static String getSearchURL(String obj)
    {
        return searchURL + "clearText="+obj;
    }

    //获取认证老师的url
    public static String getApplyVerifyTeacher(String token) {
        return applyVerifyTeacherURL + "clearText=" + token;
    }

    //老师申请接受悬赏
    public static String getApplyRewardTeacherURL(String clearText, String validation, String cipherText) {
        return applyRewardTeacherURL+ "clearText=" + clearText +
                "&validation=" + validation +
                "&cipherText=" + cipherText;
    }

    //获取悬赏
    public static String getRewordURL(String obj)
    {
        return getRewardURL + "clearText="+obj;
    }
    //获取我发布的悬赏
    public static String getGetMyRewardURL(String token) {
        return getMyRewardURL + "clearText=" + token;
    }

    //同意悬赏申请
    public static String getAgreeApplyRewardURL(String token) {
        return agreeApplyRewardURL+ "clearText=" + token;
    }

    //拒绝悬赏申请
    public static String getDisagreeApplyRewardURL(String token) {
        return disagreeApplyRewardURL+ "clearText=" + token;
    }


    //获取学生列表
    public static String getApplyStudentListURL(String token) {
        return applyStudentListURL+ "clearText=" + token;
    }

    //开始上课请求
    public static String getStartClassURL(String token) {
        return startClassURL+ "clearText=" + token;
    }

    //下课请求
    public static String getEndClassURL(String token) {
        return endClassURL+ "clearText=" + token;
    }

    //获取没评价的订单
    public static String getNoCommentURL(String token) {
        return noCommentURL+ "clearText=" + token;
    }

    //获取评价老师
    public static String getCommentTeacherURL(String token) {
        return commentTeacherURL+ "clearText=" + token;
    }

    //获取我发布的课程
    public static String getGetMyCourseURL(String token) {
        return getMyCourseURL+ "clearText=" + token;
    }

    //获取老师发布的悬赏
    public static String getGetTeacherRewardURL(String token) {
        return getTeacherRewardURL+ "clearText=" + token;
    }

    //评价课程
    public static String getCommentCourseURL(String token) {
        return commentCourseURL+ "clearText=" + token;
    }


    public static String getNoPayCourseURL(String token) {
        return noPayCourseURL+ "clearText=" + token;
    }




}
