package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;


/**
 * 获取流行的悬赏
 * Created by geyao on 2017/5/21.
 */

public class GetPopularController extends AbstractController {

    //获取悬赏
    public static String url = address + "/reward/getPopular";


    public static String execute(String pageNo){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

}
