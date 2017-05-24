package com.example.a29149.yuyuan.controller.order.teacher;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 老师同意退款申请
 * Created by geyao on 2017/5/21.
 */

public class AgreeRefundController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //订单id
    private Long orderId;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/agreeRefund";

        super.jsonObject.put("orderId", orderId);
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
                log.d(this, jsonObject.getString("result"));
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

    public String getResult() {
        return result;
    }
}
