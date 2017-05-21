package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 获取自身的最新数据
 * Created by geyao on 2017/5/21.
 */

public class GetMyInfoController extends AbstractController {

    private static String url = address + "/aes/getMyInfo";

    public static String execute(){
        try{
            JSONObject jsonObject = getJSON();
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
