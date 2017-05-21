package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 登录
 * Created by geyao on 2017/5/21.
 */

public class LogInController extends AbstractController {

    private static String url = address + "/rsa/login";

    public static String execute(
            String userName,
            String passWord,
            String AESKey
    ){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", userName);
            jsonObject.put("passWord", passWord);
            jsonObject.put("AESKey", AESKey);
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
