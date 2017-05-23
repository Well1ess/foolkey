package com.example.a29149.yuyuan.controller;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoClassCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewNoClassRewardAdapter;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.order.teacher.home.GetOrderBuyCourseAsTeacherByOrderStatesController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by GR on 2017/5/22.
 */

public abstract class AbstractControllerTemplate extends AbstractController{

    protected JSONObject jsonObject;

    protected String url = address ;



    public final String execute(){
        try {
            jsonObject = getJSON();
            handle();
            String s = HttpSender.send(url, jsonObject);
            afterHandle(s);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

    public abstract void handle() throws JSONException;

    protected abstract void afterHandle(String s);


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

}

