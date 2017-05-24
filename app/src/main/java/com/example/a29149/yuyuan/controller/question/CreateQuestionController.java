package com.example.a29149.yuyuan.controller.question;

import com.example.a29149.yuyuan.DTO.OrderAskQuestionDTO;
import com.example.a29149.yuyuan.DTO.QuestionAnswerDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建一个问题
 * Created by GR on 2017/5/22.
 */

public class CreateQuestionController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //回答者的id
    private String answerId;
    //问题的价格
    private String price;
    //问题的题目
    private String title;
    //回答内容
    private String questionContent;
    //优惠券id
    private String couponId;
    //技术类别
    private String technicTagEnum;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;
    //问答DTO
    private QuestionAnswerDTO questionAnswerDTO;
    //问答的订单信息
    private OrderAskQuestionDTO orderAskQuestionDTO;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/question/buyAnswer";

        super.jsonObject.put("answerId", answerId);
        super.jsonObject.put("price", price);
        super.jsonObject.put("title", title);
        super.jsonObject.put("questionContent", questionContent);
        super.jsonObject.put("couponId", couponId);
        super.jsonObject.put("technicTagEnum", technicTagEnum);

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

                log.d(this, jsonObject.getString("questionAnswerDTO"));
                log.d(this, jsonObject.getString("orderAskQuestionDTO"));



                //判断传来的参数是否是空
                if (jsonObject.getString("questionAnswerDTO") == null || jsonObject.getString("orderAskQuestionDTO") == null) {
                    this.result = Const.FAIL;
                    return;
                }
                //处理参数：questionAnswerDTO
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<QuestionAnswerDTO>() {
                }.getType();
                this.questionAnswerDTO = new Gson().fromJson(jsonObject.getString("questionAnswerDTO"), type);

                //处理参数：orderAskQuestionDTO
                type = new com.google.gson.reflect.TypeToken<OrderAskQuestionDTO>() {
                }.getType();
                this.orderAskQuestionDTO = new Gson().fromJson(jsonObject.getString("orderAskQuestionDTO"), type);
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public void setTechnicTagEnum(String technicTagEnum) {
        this.technicTagEnum = technicTagEnum;
    }

    public String getResult() {
        return result;
    }

    public QuestionAnswerDTO getQuestionAnswerDTO() {
        return questionAnswerDTO;
    }

    public OrderAskQuestionDTO getOrderAskQuestionDTO() {
        return orderAskQuestionDTO;
    }
}
