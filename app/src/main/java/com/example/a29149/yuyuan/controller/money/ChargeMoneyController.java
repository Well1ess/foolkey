package com.example.a29149.yuyuan.controller.money;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 用户充钱
 * Created by geyao on 2017/5/21.
 */

public class ChargeMoneyController extends AbstractController {

    private static String url = address + "/aes/recharge";

    public static String execute(
            String amount
    ){
        try{
            JSONObject jsonObject = getJSON();
            jsonObject.put("amount", amount );
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
