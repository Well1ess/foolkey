package com.example.a29149.yuyuan.Util;

import com.example.a29149.yuyuan.Util.Secret.AESOperator;

import org.json.JSONObject;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:
 */

public class AESTransformResult {
    public static String getResult(String cipherText, String... params) {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", GlobalUtil.getInstance().getToken());
            for (int i = 0; i < params.length; i = i + 2) {
                jsonObject.put(params[i], params[i + 1]);
            }

            result = "clearText=" + jsonObject.toString() +
                    "&validation=" + java.net.URLEncoder.encode(
                    AESOperator.getInstance().encrypt(jsonObject.toString()).replaceAll("\n", "愚")) +
                    "&cipherText=" + cipherText;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
