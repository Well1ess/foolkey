package com.example.a29149.yuyuan.controller.course.judge;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师评价学生
 * 只有分数
 * Created by geyao on 2017/5/21.
 */

public class JudgeStudentController extends AbstractController {

    private static String url = address + "/aes/judge/student";

    public static String execute(
            String orderId,
            String score
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            jsonObject.put("score", score);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
