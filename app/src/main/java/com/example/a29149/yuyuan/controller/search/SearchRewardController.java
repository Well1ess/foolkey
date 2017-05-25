package com.example.a29149.yuyuan.controller.search;

import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

import org.json.JSONException;

/**
 * 搜索悬赏
 * Created by geyao on 2017/5/25.
 */

public class SearchRewardController extends AbstractControllerTemplate {

    private String keyWord;
    private String

    @Override
    public void handle() throws JSONException {
        jsonObject.put("condition", "reward");


    }

    @Override
    protected void afterHandle(String s) {

    }
}
