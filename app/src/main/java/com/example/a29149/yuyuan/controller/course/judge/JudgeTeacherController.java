package com.example.a29149.yuyuan.controller.course.judge;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/21.
 */

public class JudgeTeacherController extends AbstractController {

    private static String url = address + "/aes/judge/teacher";

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
