package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师接收学生对于课程的申请
 * 学生付款后，会对老师进行申请，老师同意以后，就会进入到开始上课阶段
 * Created by geyao on 2017/5/21.
 */

public class AcceptController extends AbstractController {

    private static String url = address + "/aes/course/accept";

    public static String execute(
        String orderId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
