package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 接收某位老师对自己的悬赏的申请
 * 需要付款
 * Created by geyao on 2017/5/21.
 */
@Deprecated
public class AcceptController extends AbstractController {

    private static String url = address + "/aes/acceptRewardApplication";

    public static String execute(
            String applicationId, //申请的id
            String couponId, //优惠券的id
            String price // 价格，悬赏的price减去优惠券的value
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("applicationId", applicationId);
            jsonObject.put("couponId", couponId == null? "" : couponId );
            jsonObject.put("price", price);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
