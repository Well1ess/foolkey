package com.example.a29149.yuyuan.controller.order.student;

import com.example.a29149.yuyuan.DTO.EvaluationCourseDTO;
import com.example.a29149.yuyuan.DTO.QuestionAnswerDTO;
import com.example.a29149.yuyuan.Enum.PayResultEnum;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 评价课程
 * Created by GR on 2017/5/24.
 */

public class JudgeCourseController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //订单id
    private Long orderId;
    //分数
    private Double score;
    //评价内容
    private String content;
    //图片地址
    private String pic1Path;
    private String pic2Path;
    private String pic3Path;
    private String pic4Path;


    /**
     * 后台传来的数据
     */
    //结果
    private String result;
    //评价DTO
    private EvaluationCourseDTO evaluation;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/judge/course";

        super.jsonObject.put("orderId", orderId);
        super.jsonObject.put("score", score);
        super.jsonObject.put("content", content);
        super.jsonObject.put("pic1Path", pic1Path);
        super.jsonObject.put("pic2Path", pic2Path);
        super.jsonObject.put("pic3Path", pic3Path);
        super.jsonObject.put("pic4Path", pic4Path);
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
                log.d(this, jsonObject.getString("evaluation"));

                //判断传来的参数是否是空
                if (jsonObject.getString("evaluation") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：payResultEnum
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<EvaluationCourseDTO>() {
                }.getType();

                this.evaluation = new Gson().fromJson(jsonObject.getString("evaluation"), type);
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPic1Path(String pic1Path) {
        this.pic1Path = pic1Path;
    }

    public void setPic2Path(String pic2Path) {
        this.pic2Path = pic2Path;
    }

    public void setPic3Path(String pic3Path) {
        this.pic3Path = pic3Path;
    }

    public void setPic4Path(String pic4Path) {
        this.pic4Path = pic4Path;
    }

    public String getResult() {
        return result;
    }

    public EvaluationCourseDTO getEvaluation() {
        return evaluation;
    }
}
