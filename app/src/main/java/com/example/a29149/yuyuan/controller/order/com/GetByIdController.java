package com.example.a29149.yuyuan.controller.order.com;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取某个订单的详细信息
 * Created by geyao on 2017/5/21.
 */

public class GetByIdController extends AbstractController {

    private static String url = address + "/aes/order/getById";

    public static String execute(
            String orderId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderId", orderId);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
