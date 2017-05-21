package com.example.a29149.yuyuan.controller.userInfo.teacher;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 切换教师身份
 * Created by geyao on 2017/5/21.
 */

public class SwitchToTeacherController extends AbstractController {

    private static String url = address + "switchToTeacher";

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
