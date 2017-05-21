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

    //获取悬赏任务
    public static final String rewardURL = "http://" + address + "/aes/get";
   //老师发布课程
    private static final String teacherPublishCoursedURL = "http://" + address + "/courseTeacher/publishCourseTeacher?";
    //搜索
    public static final String searchURL = "http://" + address + "/search?";


    //获取老师发布课程
    @Deprecated
    public static String getTeacherPublishCoursedURL(String clearText) {
//        return teacherPublishCoursedURL + "clearText=" + clearText;
        return null;
    }

    //注销








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












}
