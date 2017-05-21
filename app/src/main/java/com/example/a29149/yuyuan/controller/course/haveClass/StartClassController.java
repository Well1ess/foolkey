package com.example.a29149.yuyuan.controller.course.haveClass;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 开始上课
 * 只有以老师身份可以点击
 * Created by geyao on 2017/5/21.
 */

public class StartClassController extends AbstractController {

    private static String url = address + "/aes/startClass";

    public static String execute(
            String orderId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
