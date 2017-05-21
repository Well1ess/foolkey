package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取某个特定的课程的信息
 * Created by geyao on 2017/5/21.
 */

public class GetByIdController extends AbstractController {
    private static String url = address + "/course/getById";

    public static String execute(
            String courseId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("courseId", courseId);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
