package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 拒绝某位老师的申请
 * Created by geyao on 2017/5/21.
 */

public class RefuseController extends AbstractController {

    private static String url = address + "/aes/reward/refuse";

    public static String execute(
            String applicationId //申请的id
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("applicationId", applicationId);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
