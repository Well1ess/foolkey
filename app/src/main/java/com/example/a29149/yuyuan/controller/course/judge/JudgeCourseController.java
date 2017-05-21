package com.example.a29149.yuyuan.controller.course.judge;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 对course进行评价
 * Created by geyao on 2017/5/21.
 */

public class JudgeCourseController extends AbstractController {
    private static String url = address + "/aes/judge/course";

    public static String execute(
            String orderId,
            String score,
            String content,
            String pic1Path,
            String pic2Path,
            String pic3Path,
            String pic4Path
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            jsonObject.put("score", score);
            jsonObject.put("content", content);
            jsonObject.put("pic1Path", pic1Path);
            jsonObject.put("pic2Path", pic2Path);
            jsonObject.put("pic3Path", pic3Path);
            jsonObject.put("pic4Path", pic4Path);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

}
