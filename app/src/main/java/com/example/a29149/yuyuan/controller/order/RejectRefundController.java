package com.example.a29149.yuyuan.controller.order;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师拒绝了退款申请
 * Created by geyao on 2017/5/21.
 */

public class RejectRefundController extends AbstractController {

    private static String url = address + "/aes/rejectRefund";

    public static String execute(String orderId){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put( "orderId", orderId);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
