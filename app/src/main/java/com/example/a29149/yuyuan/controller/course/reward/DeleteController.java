package com.example.a29149.yuyuan.controller.course.reward;

import android.app.Activity;

import com.example.a29149.yuyuan.DTO.QuestionAnswerDTO;
import com.example.a29149.yuyuan.Enum.PayResultEnum;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 删除悬赏
 * Created by GR on 2017/5/26.
 */

public class DeleteController extends AbstractControllerTemplate {
    /**
     * 传送到后台的数据
     **/
    //围观的悬赏的id
    private String rewardId;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/deleteReward";

        super.jsonObject.put("rewardId", rewardId);
    }

    @Override
    protected void afterHandle(String s) {
        JSONObject jsonObject = null;
        try {

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");

            //根据返回的结果标志进行不同的操作
            if (resultFlag.equals("success")) {
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }


    public String getResult() {
        return result;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }
}
