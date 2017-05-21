package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取我发布的所有悬赏
 * 只包含悬赏本身，悬赏人信息（就是登陆者），申请人，一概不获取】
 * 老师不能使用这个controller
 * Created by geyao on 2017/5/21.
 */

public class MineController extends AbstractController {

    private static String url = address + "/aes/reward/getMine";

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
