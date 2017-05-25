package com.example.a29149.yuyuan.controller.search;

import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 搜索悬赏
 * Created by geyao on 2017/5/25.
 */

public class SearchRewardController extends AbstractControllerTemplate {

    //传
    private String keyWord;
    private String pageNo;
    //取
    private String result;
    private List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTOList;




    @Override
    public void handle() throws JSONException {
        jsonObject.put("condition", "reward");
        jsonObject.put("keyWord", keyWord);
        jsonObject.put("pageNo", pageNo);
        url = "/search";
    }

    @Override
    protected void afterHandle(String s) {
        JSONObject jsonObject = null;
        try {

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");
            if(resultFlag == null){
                resultFlag = Const.FAIL;
            }
            //根据返回的结果标志进行不同的操作
            if (resultFlag.equals("success")) {

                log.d(this, jsonObject.getString("orderList"));

                //判断传来的参数是否是空
                if (jsonObject.getString("orderList") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：orderList（买课订单DTOS）
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<RewardWithStudentSTCDTO>>() {
                }.getType();
                this.rewardWithStudentSTCDTOList = new Gson().fromJson(jsonObject.getString("list"), type);

                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getResult() {
        return result;
    }

    public List<RewardWithStudentSTCDTO> getRewardWithStudentSTCDTOList() {
        return rewardWithStudentSTCDTOList;
    }
}
