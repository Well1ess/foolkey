package com.example.a29149.yuyuan.controller.question;

import com.example.a29149.yuyuan.DTO.QuestionAnswerDTO;
import com.example.a29149.yuyuan.Enum.PayResultEnum;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 围观问答（买答案）
 * Created by GR on 2017/5/22.
 */

public class BuyAnswerController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //围观的问题的id
    private String questionId;
    //优惠券的id
    private String couponId;

    /**
     * 后台传来的数据
     */
    //结果
    private String result;
    //付款的结果（成功、失败、余额不足、优惠券不可用）
    private PayResultEnum payResultEnum;
    //问答DTO
    private QuestionAnswerDTO questionAnswerDTO;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/question/buyAnswer";

        super.jsonObject.put("questionId", questionId);
        super.jsonObject.put("couponId", couponId);
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
                log.d(this, jsonObject.getString("payResultEnum"));
                log.d(this, jsonObject.getString("questionAnswerDTO"));

                //判断传来的参数是否是空
                if (jsonObject.getString("payResultEnum") == null || jsonObject.getString("questionAnswerDTO") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：payResultEnum
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<PayResultEnum>() {
                }.getType();

                this.payResultEnum = new Gson().fromJson(jsonObject.getString("payResultEnum"), type);

                //处理参数：questionAnswerDTO
                type = new com.google.gson.reflect.TypeToken<QuestionAnswerDTO>() {
                }.getType();

                this.questionAnswerDTO = new Gson().fromJson(jsonObject.getString("questionAnswerDTO"), type);
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getResult() {
        return result;
    }

    public PayResultEnum getPayResultEnum() {
        return payResultEnum;
    }

    public QuestionAnswerDTO getQuestionAnswerDTO() {
        return questionAnswerDTO;
    }
}
