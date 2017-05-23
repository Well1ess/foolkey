package com.example.a29149.yuyuan.controller.coupon;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取到我的优惠券
 *
 * 参数：
 * token
 * pageNo
 *
 * 返回：
 * couponList(List<CouponDTO>)
 * Created by GR on 2017/5/22.
 */

public class GetMyCouponController extends AbstractController {

    private static String url = address + "/aes/getMyCoupon";

    public static String execute(
            String pageNo
    ) {
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send(url, jsonObject);
        } catch (Exception e) {
            return failJSON(e);
        }
    }
}
