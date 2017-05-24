package com.example.a29149.yuyuan.controller.order.student;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Enum.OrderStateEnum;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 作为学生，获取某个特定状态的订单，包括悬赏
 * Created by geyao on 2017/5/21.
 */

public class GetSpecificStateOrderController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //订单状态
    private OrderStateEnum orderState;
    //页码
    private Integer pageNo;

    /**
     * 后台传来的数据
     */
    //结果
    private String result = Const.FAIL;
    //订单
    private List<OrderBuyCourseAsStudentDTO> orderList;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/getOrderAsStudent";

        super.jsonObject.put("orderState", orderState);
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
                log.d(this, jsonObject.getString("orderList"));

                //判断传来的参数是否是空
                if (jsonObject.getString("orderList") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：orderList
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsStudentDTO>>() {
                }.getType();

                this.orderList = new Gson().fromJson(jsonObject.getString("orderList"), type);
                this.result = Const.SUCCESS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.result = Const.FAIL;
        }
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }


    public String getResult() {
        return result;
    }

    public List<OrderBuyCourseAsStudentDTO> getOrderList() {
        return orderList;
    }
}
