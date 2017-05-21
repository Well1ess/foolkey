package com.example.a29149.yuyuan.controller.course.haveClass;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/21.
 */

public class EndClassController extends AbstractController {
    private static String url = address + "/aes/endClass";

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
