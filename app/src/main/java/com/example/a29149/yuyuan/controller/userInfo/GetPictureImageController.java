package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/22.
 * Email:ml1995@mail.ustc.edu.cn
 * 上传个人头像，获取签名
 */

public class GetPictureImageController extends AbstractController{

    private static String url = address + "/upload/getSign";

    public static String execute(){
        try{
            JSONObject jsonObject = new JSONObject();
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
