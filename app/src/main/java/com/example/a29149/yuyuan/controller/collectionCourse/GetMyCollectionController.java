package com.example.a29149.yuyuan.controller.collectionCourse;

import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseTeacherPopularDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractController;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 获取到我收藏的课程
 * 参数：
 * token
 * pageNo
 * 返回：
 * result
 * courseTeacherDTOS：ArrayList<CourseWithTeacherSTCDTO>
 * <p>
 * Created by GR on 2017/5/22.
 */

public class GetMyCollectionController extends AbstractControllerTemplate {

    //传给后台
    private String pageNo;

    //后台传来的
    private String result;
    private List<CourseTeacherPopularDTO> courseTeacherPopularDTOS;


    private static String url = address + "/collectionCourse/getMyCollection";


    @Override
    public void handle() throws JSONException {
        super.url += "/collectionCourse/getMyCollection";

        super.jsonObject.put("pageNo",pageNo);
    }

    @Override
    protected void afterHandle(String s) {
        JSONObject jsonObject = null;
        try {

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");

            //根据返回的结果标志进行不同的操作
            if(resultFlag.equals("success")){
                log.d(this, jsonObject.getString("courseWithTeacherSTCDTOS"));
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<CourseTeacherPopularDTO>>() {
                }.getType();

                //判断传来的参数是否是空
                if(jsonObject.getString("courseWithTeacherSTCDTOS") == null){
                    this.result = Const.FAIL;
                    return;
                }
                this.courseTeacherPopularDTOS = new Gson().fromJson(jsonObject.getString("courseWithTeacherSTCDTOS"), type);
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getResult() {
        return result;
    }

    public List<CourseTeacherPopularDTO> getCourseTeacherPopularDTOS() {
        return courseTeacherPopularDTOS;
    }
}
