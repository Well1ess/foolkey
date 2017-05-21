package com.example.a29149.yuyuan.controller.order.teacher.home;

import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;

import org.json.JSONObject;

/**
 *
 * 老师查看一个课程下面，所有待上课、正在上课的学生
 * 用于首页的展示
 * Created by geyao on 2017/5/21.
 */

public class GetAgreedOnClassOrderCourseStudentByTeacherController extends AbstractController {

    private static String url = address + "/aes/order/getAgreedOnClassOrderCourseStudentByTeacher";

    public static String execute(
        String pageNo,
        String courseId
    ){
        try {
            JSONObject jsonObject = getJSON();
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("courseId", courseId);
            return HttpSender.send( url, jsonObject );
        }catch (Exception e){
            return failJSON( e );
        }
    }
}
