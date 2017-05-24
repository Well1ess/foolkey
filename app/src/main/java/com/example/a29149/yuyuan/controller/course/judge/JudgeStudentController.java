package com.example.a29149.yuyuan.controller.course.judge;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 老师评价学生
 * 只有分数
 * Created by geyao on 2017/5/21.
 */

public class JudgeStudentController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //订单id
    private Long orderId;
    //分数
    private Double score;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = "";

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/judge/student";

        super.jsonObject.put("orderId", orderId);
        super.jsonObject.put("score", score);
    }

    @Override
    protected void afterHandle(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            //把后台传来的数据String转为JSON
            String resultFlag = jsonObject.getString("result");

            //根据返回的结果标志进行不同的操作
            if (resultFlag.equals("success")) {
                log.d(this, jsonObject.getString("result"));
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        } catch (Exception e){
            this.result = Const.FAIL;
        }
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

}