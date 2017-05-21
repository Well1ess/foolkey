package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 更新悬赏
 * Created by geyao on 2017/5/21.
 */

public class UpdateRewardController extends AbstractController {

    private static String url = address + "/aes/reward/update";

    public static String execute(
        String rewardId, // id
        String technicTagEnum, // 技术标签
        String topic, //标题
        String description, //描述
        String price, //价格，虚拟币
        String courseTimeDayEnum,
        String teachMethodEnum, //授课方法
        String teacherRequirementEnum, // 对教师的要求
        String studentBaseEnum //学生基础
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put( "rewardId", rewardId );
            jsonObject.put( "technicTagEnum", technicTagEnum);
            jsonObject.put( "topic", topic);
            jsonObject.put( "description", description);
            jsonObject.put( "price", price);
            jsonObject.put( "courseTimeDayEnum", courseTimeDayEnum);
            jsonObject.put( "teachMethodEnum", teachMethodEnum);
            jsonObject.put( "teacherRequirementEnum", teacherRequirementEnum);
            jsonObject.put( "studentBaseEnum", studentBaseEnum);
            return HttpSender.send(url, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
