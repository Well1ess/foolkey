package com.example.a29149.yuyuan.controller.order.teacher.home;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsStudentDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 老师：根据订单状态获取到我的课程-悬赏订单
 * Created by GR on 2017/5/23.
 */

public class GetOrderBuyCourseAsTeacherByOrderStatesController extends AbstractControllerTemplate {

    /**
     * 传送到后台的数据
     **/
    //页码
    private String pageNo;

    //订单状态
    private String orderStateEnum;

    /**
     * 后台传来的数据
     */
    //结果
    private String result ="fail";

    //订单信息DTOS
    private List<OrderBuyCourseAsStudentDTO> orderList;

    @Override
    public void handle() throws JSONException {
        super.url += "/aes/order/GetOrderAsTeacherByOrderStates";

        super.jsonObject.put("pageNo", pageNo);
        super.jsonObject.put("orderStateEnum", orderStateEnum);
    }

    @Override
    protected void afterHandle(String s) {
        JSONObject jsonObject = null;
        try {

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");
            if(resultFlag == null){
                resultFlag = Const.FAIL;
            }
            //根据返回的结果标志进行不同的操作
            if (resultFlag.equals("success")) {

                log.d(this, jsonObject.getString("orderList"));

                //判断传来的参数是否是空
                if (jsonObject.getString("orderList") == null) {
                    this.result = Const.FAIL;
                    return;
                }

                //处理参数：orderList（买课订单DTOS）
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

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public void setOrderStateEnum(String orderStateEnum) {
        this.orderStateEnum = orderStateEnum;
    }

    public String getResult() {
        return result;
    }

    public List<OrderBuyCourseAsStudentDTO> getOrderList() {
        return orderList;
    }
}
