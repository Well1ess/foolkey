package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 *
 * 删除某个课程
 * 需要courseId
 * Created by geyao on 2017/5/21.
 */

public class DeleteController extends AbstractController{

    private static String url = address + "/aes/course/delete";

    public static String execute(String courseId){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("courseId", courseId);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
