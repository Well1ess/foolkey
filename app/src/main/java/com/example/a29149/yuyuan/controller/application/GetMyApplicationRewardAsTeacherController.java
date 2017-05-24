package com.example.a29149.yuyuan.controller.application;

import com.example.a29149.yuyuan.DTO.ApplicationRewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 老师，获取到我申请的所有的悬赏
 * Created by GR on 2017/5/24.
 */

public class GetMyApplicationRewardAsTeacherController  extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //页码
    private Integer pageNo;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;
    //申请信息（包括申请信息、学生<悬赏发布者>信息、悬赏信息）
    private List<ApplicationRewardWithStudentSTCDTO> applicationRewardWithStudentSTCDTOS;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/reward/getApplicantAsTeacher";

        super.jsonObject.put("pageNo", pageNo);
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
                log.d(this, jsonObject.getString("applicationRewardWithStudentSTCDTOS"));

                //判断传来的参数是否是空
                if (jsonObject.getString("applicationRewardWithStudentSTCDTOS") == null ) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：payResultEnum
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<ApplicationRewardWithStudentSTCDTO>>() {
                }.getType();

                this.applicationRewardWithStudentSTCDTOS = new Gson().fromJson(jsonObject.getString("applicationRewardWithStudentSTCDTOS"), type);

                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getResult() {
        return result;
    }

    public List<ApplicationRewardWithStudentSTCDTO> getApplicationRewardWithStudentSTCDTOS() {
        return applicationRewardWithStudentSTCDTOS;
    }
}
