package com.example.a29149.yuyuan.controller.userInfo;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/22.
 * Email:ml1995@mail.ustc.edu.cn
 * 上传个人头像，获取签名
 */

public class GetPictureImage extends AbstractController{

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
