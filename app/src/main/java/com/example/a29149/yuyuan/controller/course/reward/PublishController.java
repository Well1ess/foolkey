package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 发布悬赏
 * Created by geyao on 2017/5/21.
 */

public class PublishController extends AbstractController {

    private static String url = address + "/aes/reward/publish";

    public static String execute(
            RewardDTO rewardDTO
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("technicTagEnum", rewardDTO.getTechnicTagEnum().toString());
            jsonObject.put("topic", rewardDTO.getTopic());
            jsonObject.put("description", rewardDTO.getDescription());
            jsonObject.put("price", rewardDTO.getPrice());
            jsonObject.put("courseTimeDayEnum", rewardDTO.getCourseTimeDayEnum());
            jsonObject.put("teachMethodEnum", rewardDTO.getTeachMethodEnum());
            jsonObject.put("teachRequirementEnum", rewardDTO.getTeacherRequirementEnum());
            jsonObject.put("studentBaseEnum", rewardDTO.getStudentBaseEnum());
            return HttpSender.send( url, jsonObject);
        }catch (Exception e){
            return failJSON(e);
        }
    }
}
