package com.example.a29149.yuyuan.action;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.controller.course.reward.AcceptRewardApplicationController;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  学生接收老师对自己悬赏申请的异步任务
 *
 * Created by geyao on 2017/6/15.
 */

public class AcceptRewardApplicationAction extends YYBaseAction<Object> {

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  构造函数
     *
     * @param applicationId 申请的id
     * @param couponId      优惠券的id
     * @param price         价格
     */
    public AcceptRewardApplicationAction(String applicationId, String couponId, String price) {
        super();
        controller = new AcceptRewardApplicationController(applicationId, couponId, price);
    }
}
