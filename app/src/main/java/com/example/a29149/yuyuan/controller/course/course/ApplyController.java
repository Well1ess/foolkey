package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 学生申请老师的课程，仅申请，不付款
 * Created by geyao on 2017/5/21.
 */

public class ApplyController extends AbstractController {

    private static String url = address + "/aes/placeOrderTeacherCourse";

    public static String execute(
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
            jsonObject.put("courseId", courseId);
            jsonObject.put("amount", amount);
            jsonObject.put("number", number);
            jsonObject.put("cutOffPercent", cutOffPercent);
            jsonObject.put("teachMethod", teachMethod);
            jsonObject.put("courseType", courseType);
            jsonObject.put("teacherId", teacherId);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
