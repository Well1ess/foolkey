package com.example.a29149.yuyuan.controller.course.course;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取流行的课程
 * Created by geyao on 2017/5/21.
 */

public class GetPopularController extends AbstractController {

    private static String url = address + "/course/popular";

    public static String execute(
        String pageNo,
        String  technicTagEnum
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("technicTagEnum", technicTagEnum);
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
