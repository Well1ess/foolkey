package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取我发布的 带解决的 悬赏，返回的信息里包括申请的老师
 * 但不会包括所有的老师，如果某个悬赏有特别多的老师申请，只会随即返回 4 条
 * 已经上课、或者等待上课不再此列
 * Created by geyao on 2017/5/21.
 */

public class GetWithApplicationController extends AbstractController {

    private static String url = address + "/aes/reward/getWithApplication";

    public static String execute(
            String pageNo
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
