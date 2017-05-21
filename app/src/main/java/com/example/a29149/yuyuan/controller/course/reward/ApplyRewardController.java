package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 老师申请悬赏，需要悬赏的Id
 * Created by geyao on 2017/5/21.
 */

public class ApplyRewardController extends AbstractController {

    private static String url = address + "/aes/applyReward";

    public static String execute( String rewardId ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("rewardId", rewardId);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
