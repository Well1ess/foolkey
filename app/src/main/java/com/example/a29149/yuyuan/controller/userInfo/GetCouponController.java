package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取我的优惠券
 * Created by geyao on 2017/5/21.
 */

public class GetCouponController extends AbstractController {

    private static String  url = address + "/aes/getMyCoupon";

    public static String execute(
            String pageNo
    ){
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
