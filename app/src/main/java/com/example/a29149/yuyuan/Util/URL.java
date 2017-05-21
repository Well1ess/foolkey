package com.example.a29149.yuyuan.Util;

import com.example.a29149.yuyuan.Enum.OrderStateEnum;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 张丽华 on 2017/4/30.
 * Description:
 */

public class URL {

    public static final String address = "115.159.5.195:8080";

    //获取公钥
    public static final String publicKeyURL = "http://" + address + "/getKey?";
    //注册
    public static final String registerURL = "http://" + address + "/rsa/register?";
    //登陆
    public static final String loginURL = "http://" + address + "/rsa/login?";
    //注销
    private static final String logOutURL = "http://" + address + "/aes/logOut?";
    //获取折扣卷
    private static final String couponURL = "http://" + address + "/aes/getMyCoupon?";
    //更新个人信息/获取个人信息
    private static final String selfInfoURL = "http://" + address + "/aes/getMyInfo?";
    //获取悬赏任务
    public static final String rewardURL = "http://" + address + "/aes/get";
    //切换身份
    private static final String switchToTeacher = "http://" + address + "/switchToTeacher?";
    //老师发布课程
    private static final String teacherPublishCoursedURL = "http://" + address + "/courseTeacher/publishCourseTeacher?";
    //搜索
    public static final String searchURL = "http://" + address + "/search?";
    //充值
    private static final String rechargeURL = "http://" + address + "/aes/recharge?";
    //提交订单
    private static final String submitOrder = "http://" + address + "/aes/placeOrderTeacherCourse?";
    //申请认证
    private static final String applyVerifyTeacherURL = "http://" + address + "/applyToVerifyTeacher?";

    //获取老师准备上课、正在上课的课程
    private static final String getMyCourseURL = "http://" +address + "/order/getAgreedOnClassOrderCourseByTeacher?";

    //获取老师准备上课、正在上课的悬赏
    private static final String getTeacherRewardURL = "http://" +address + "/order/getAgreedOnClassOrderRewardByTeacher?";

    //获取学生列表
    private static final String applyStudentListURL = "http://" +address + "/order/getAgreedOnClassOrderCourseByTeacher?";


    //未评价订单请求
    private static final String getOrderAsStudent = "http://" +address + "/getOrderAsStudent?";




    //获取老师发布课程
    @Deprecated
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
    @Deprecated
    public static String getLogOutURL(String clearText, String validation, String cipherText) {
        return logOutURL + "clearText=" + clearText +
                "&validation=" + validation +
                "&cipherText=" + cipherText;
    }


    /**
     * 获取我的优惠券
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static String doWithCouponURL(String pageNo, String pageSize){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
            return HttpSender.send( URL.couponURL, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }


    /**
     * 登陆者切换为老师身份
     * @return
     */
    public static String doWithSwitchToTeacher(){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( URL.switchToTeacher, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }


    /**
     * 申请认证老师
     * @return
     */
    public static String doWithApplyVerifyTeacherURL(){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( URL.applyVerifyTeacherURL, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }





    /**
     * 获取登录者的关于课程、悬赏的订单
     * @param orderState 指定的订单状态
     * @param pageNo 页数
     * @return
     */
    public static String doWithGetOrderAsStudent(String orderState, String pageNo){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put( "orderState", orderState);
            jsonObject.put( "pageNo", pageNo );
            return HttpSender.send( URL.getOrderAsStudent, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

    /**
     * 获取登陆者未评价的课程、悬赏订单
     * @param pageNo 页数
     * @return
     */
    public static String doWithNoCommentURL( String pageNo ){
        return doWithGetOrderAsStudent(OrderStateEnum.结束上课.toString() , pageNo);
    }



    /**
     * 老师获取要上课、正在上课的悬赏
     * @param pageNo
     * @return
     */
    public static String doWithGetMyCourseURL(String pageNo){
        try {
            JSONObject target = getJSON();
            target.put("pageNo",pageNo);
            return HttpSender.send( URL.getMyCourseURL, target );

        } catch (Exception e) {
            e.printStackTrace();
            return failJSON();
        }
    }



    /**
     * 老师获取要上课、正在上课的悬赏
     * @param pageNo
     * @return
     */
    public static String doWithGetTeacherRewardURL(String pageNo){
        try {
            JSONObject target = getJSON();
            target.put("pageNo",pageNo);
            return HttpSender.send( URL.getTeacherRewardURL, target );

        } catch (Exception e) {
            e.printStackTrace();
            return failJSON();
        }
    }



    /**
     * 获取未付款的订单，包括课程、悬赏
     * @param pageNo
     * @return
     */
    public static String doWithNoPayOrderURL(String pageNo) {
        return doWithGetOrderAsStudent( OrderStateEnum.未付款.toString(), pageNo);
    }



    //获取一个带有登陆者token的JSON对象
    public static JSONObject getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", GlobalUtil.getInstance().getToken());
        return jsonObject;
    }

    //网络连接失败了，返回这样的函数
    public static String failJSON(){
        return null;
    }


    //获取登陆者的信息
    public static String doWithSelfInfoURL(){
        try{
            JSONObject jsonObject = getJSON();
            return HttpSender.send( URL.selfInfoURL, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

    //登陆者给自己充值
    public static String doWithRechargeURL(String money){
        try{
            JSONObject jsonObject = getJSON();
            jsonObject.put("amount", money );
            return HttpSender.send( URL.rechargeURL, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }


    //提交订单
    /**
     *
     * 购买老师课程
     * @param courseId      课程Id
     * @param amount        定点金额
     * @param number        购买数量
     * @param cutOffPercent 金额
     * @param teachMethod   教授方式
     * @param courseType    课程类型
     * @return
     */
    public static String doWithSubmitOrder(
            String courseId,
            String amount,
            String number,
            String cutOffPercent,
            String teachMethod,
            String courseType,
            String teacherId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put( "courseId", courseId );
            jsonObject.put( "amount", amount );
            jsonObject.put( "number", number );
            jsonObject.put( "cutOffPercent", cutOffPercent );
            jsonObject.put( "teachMethod", teachMethod);
            jsonObject.put( "courseType", courseType );
            jsonObject.put( "teacherId", teacherId );
            return HttpSender.send( URL.submitOrder, jsonObject );
        } catch (Exception e) {
            e.printStackTrace();
            return failJSON();
        }
    }





    /**
     * 登陆者 登出
     * @return
     */
    public static String doWithLogOutURL(){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( URL.logOutURL, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

    /**
     *  老师获取 已付款 未评价 的课程订单(同意上课, 上课中)
     * @param pageNo
     * @return
     */
    public static String doWithApplyStudentListURL(String pageNo){
        try {
            JSONObject target = getJSON();
            target.put("pageNo",pageNo);

            return HttpSender.send( URL.applyStudentListURL, target );

        } catch (Exception e) {
            e.printStackTrace();
            return failJSON();
        }
    }


}
