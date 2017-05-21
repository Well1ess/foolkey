package com.example.a29149.yuyuan.controller.order.student;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 作为学生，获取某个特定状态的订单，包括悬赏
 * Created by geyao on 2017/5/21.
 */

public class GetSpecifiStateOrderController extends AbstractController {

    private static String url = address + "/aes/getOrderAsStudent";

    public static String execute(
        String orderState,
        String pageNo
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("orderState", orderState);
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
