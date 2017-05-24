package com.example.a29149.yuyuan.controller.question;

import com.example.a29149.yuyuan.DTO.QuestionAnswerDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 回答一个问题
 * Created by GR on 2017/5/22.
 */

public class CreateAnswerController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //回答的问题的id
    private String questionId;
    //问题的回答
    private String answerContent;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;
    //问答DTO
    private QuestionAnswerDTO questionAnswerDTO;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/question/buyAnswer";

        super.jsonObject.put("questionId", questionId);
        super.jsonObject.put("answerContent", answerContent);
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

                //判断传来的参数是否是空
                if (jsonObject.getString("questionAnswerDTO") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：questionAnswerDTO
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<QuestionAnswerDTO>() {
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

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getResult() {
        return result;
    }

    public QuestionAnswerDTO getQuestionAnswerDTO() {
        return questionAnswerDTO;
    }
}
