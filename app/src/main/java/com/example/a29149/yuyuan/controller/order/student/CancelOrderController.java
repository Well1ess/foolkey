package com.example.a29149.yuyuan.controller.order.student;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 取消某个订单，只能是未付款的
 * 只能学生提出
 * Created by geyao on 2017/5/21.
 */

public class CancelOrderController extends AbstractController {

    private static String url = address + "/aes/cancelOrder";

    public static String execute(String orderId){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
