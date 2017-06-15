package com.example.a29149.yuyuan.action;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.controller.course.reward.GetRewardApplicationController;

import java.util.List;

/**
 * 学生获取自己悬赏收到的请求 异步任务
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  每个reward都会返回一个DTO，就算里面不包含申请
 *
 * Created by geyao on 2017/6/15.
 */

public class GetRewardApplicationAction extends YYBaseAction<List<ApplicationStudentRewardAsStudentSTCDTO>> {


    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  构造函数
     * @param pageNo 页码，从1开始
     */
    public GetRewardApplicationAction(int pageNo) {
        super();
        if (pageNo < 1)
            return;
        controller = new GetRewardApplicationController(pageNo);
    }
}
