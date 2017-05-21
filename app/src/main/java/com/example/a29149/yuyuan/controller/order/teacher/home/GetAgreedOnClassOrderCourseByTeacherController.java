package com.example.a29149.yuyuan.controller.order.teacher.home;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取代上课、已经上课的course
 * 包含部分学生
 * 用于老师首页的展示
 * Created by geyao on 2017/5/21.
 */

public class GetAgreedOnClassOrderCourseByTeacherController extends AbstractController {

    private static String url = address + "/aes/order/getAgreedOnClassOrderRewardByTeacher";

    public static String execute(
        String pageNo
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
