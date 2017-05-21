package com.example.a29149.yuyuan.controller;

import com.example.a29149.yuyuan.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/21.
 */

public abstract class AbstractController {

    //协议
    public static String protocol = "http://";

    //服务器ip
    public static String address = protocol + URL.address;

//    private JSONObject jsonObject;
//
//    protected String url;
//
//
//    public final String execute(){
//        try {
//            jsonObject = getJSON();
//
//            return handle(url, jsonObject);
//        }catch (Exception e){
//            e.printStackTrace();
//            return failJSON();
//        }
//    }
//
//    public abstract String handle(String url, JSONObject jsonObject);

    /**
     * 获取一个json
     * @return
     */
    public static JSONObject getJSON(){
        try {
            return URL.getJSON();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * 失败时返回
     * @return
     */
    public static String failJSON(){
        return null;
    }
}
