package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师发布课程
 * 仅认证老师可以
 * Created by geyao on 2017/5/21.
 */

public class PublishController extends AbstractController {

    private static String url = "/course/publish";

    public static String execute(
            String topic,
            String technicTagEnum,
            String description,
            String price,
            String courseTimeDayEnum,
            String duration,
            String teachMethodEnum
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("topic", topic);
            jsonObject.put("technicTagEnum", technicTagEnum);
            jsonObject.put("description", description);
            jsonObject.put("price", price);
            jsonObject.put("courseTimeDayEnum", courseTimeDayEnum);
            jsonObject.put("duration", duration);
            jsonObject.put("teachMethodEnum", teachMethodEnum);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
