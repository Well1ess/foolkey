package com.example.a29149.yuyuan.controller.userInfo.teacher;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 * 提交申请认证的请求
 *
 * 如果学生用户试图发布课程，或者主动点击【我的-切换教师-申请认证】
 * 就会提示他需要认证, role会变成 alreadyApplied，verified字段变成processing
 * 需要去完成来自【5】个【不同学生】的悬赏【任务】
 * 且中间不能有【3星】以下的评分，如果有，则认证失败, verified变成refused,
 * 如果一路高分评价，则顺利成为老师，他的verifyState 字段，将会变成verified， role会变成 teacher
 * 可以正常进行 发布课程、接受提问、发表文章的功能。
 *
 * Created by geyao on 2017/5/21.
 */

public class ApplyToVerifyController extends AbstractController {

    private static String url = address + "/aes/applyToVerifyTeacher";

    public static String execute(

    ){
        try {
            JSONObject jsonObject = getJSON();
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }
}
