package com.example.a29149.yuyuan.controller.order.student;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 登录者作为学生，申请关闭某个交易
 * 如果他还未付款，则立刻取消
 * 否则会像老师发送申请
 * Created by geyao on 2017/5/21.
 */

public class RequestToCloseOrderController extends AbstractController {

    public static String url = address + "/aes/closeOrder";

    public static String execute(String orderId){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
