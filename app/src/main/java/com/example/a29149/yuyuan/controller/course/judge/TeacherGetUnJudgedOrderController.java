package com.example.a29149.yuyuan.controller.course.judge;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师获取未评价的订单，包括悬赏，课程
 * Created by geyao on 2017/5/21.
 */

public class TeacherGetUnJudgedOrderController extends AbstractController {

    private static String url = address + "/aes/getOrderToJudge/teacher";

    public String execute( String pageNo ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
