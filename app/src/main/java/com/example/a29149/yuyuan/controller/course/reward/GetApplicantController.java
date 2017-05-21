package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取某个特定未解决悬赏下的所有申请
 * 一般在GetRewardWithApplicationController后面使用
 * Created by geyao on 2017/5/21.
 */

public class GetApplicantController extends AbstractController {

    private static String url = address + "/aes/reward/getApplicant";

    public static String execute(
        String rewardId,
        String pageNo
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("rewardId", rewardId);
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
