package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师更新课程
 *
 * Created by geyao on 2017/5/21.
 */

public class UpdateCourseController extends AbstractController {

    private static String url = address + "/aes/courseTeacher/updateCourseTeacher";

    public static String execute(
            String courseId,
            String technicTagEnum,
            String topic,
            String description,
            String price,
            String courseTimeDayEnum,
            String teachMethodEnum,
            String duration
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("courseId", courseId);
            jsonObject.put("technicTagEnum", technicTagEnum);
            jsonObject.put("topic", topic);
            jsonObject.put("description", description);
            jsonObject.put("price", price);
            jsonObject.put("courseTimeDayEnum", courseTimeDayEnum);
            jsonObject.put("teachMethodEnum", teachMethodEnum);
            jsonObject.put("duration", duration);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
