package com.example.a29149.yuyuan.controller.order.teacher;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师同意退款申请
 * Created by geyao on 2017/5/21.
 */

public class AgreeRefundController extends AbstractController {

    private static String url = address + "/aes/agreeRefund";

    public static String execute( String orderId ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put( "orderId", orderId );
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
