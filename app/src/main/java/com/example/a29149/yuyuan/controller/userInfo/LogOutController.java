package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/21.
 */

public class LogOutController extends AbstractController {

    private static String url = address + "logOut";

    public static String execute(){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
