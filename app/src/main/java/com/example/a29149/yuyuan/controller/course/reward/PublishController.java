package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 发布悬赏
 * Created by geyao on 2017/5/21.
 */

public class PublishController extends AbstractController {

    private static String url = address + "/aes/reward/publish";

    public static String execute(
            String technicTagEnum,
            String topic,
            String description,
            String price,
            String courseTimeDayEnum,
            String teachMethodEnum,
            String teachRequirementEnum,
            String studentBaseEnum
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("technicTagEnum", technicTagEnum);
            jsonObject.put("topic", topic);
            jsonObject.put("description", description);
            jsonObject.put("price", price);
            jsonObject.put("courseTimeDayEnum", courseTimeDayEnum);
            jsonObject.put("teachMethodEnum", teachMethodEnum);
            jsonObject.put("teachRequirementEnum", teachRequirementEnum);
            jsonObject.put("studentBaseEnum", studentBaseEnum);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON(e);
        }
    }
}
