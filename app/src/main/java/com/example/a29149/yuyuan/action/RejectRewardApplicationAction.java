package com.example.a29149.yuyuan.action;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.controller.course.reward.RejectRewardApplicationController;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  学生拒绝老师对悬赏申请的异步任务
 * Created by geyao on 2017/6/15.
 */

public class RejectRewardApplicationAction<T> extends YYBaseAction<T> {

    /**
     * 构造函数，需要将拒绝的申请id传进来
     * @param applicationId
     */
    public RejectRewardApplicationAction(String applicationId) {
        super();
        controller = new RejectRewardApplicationController<>(applicationId);
    }


}
