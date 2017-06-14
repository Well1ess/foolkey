package com.example.a29149.yuyuan.action;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.AbstractObject.YYBaseController;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.controller.course.reward.PublishRewardController;

/**
 * 发布悬赏的异步任务
 *
 * Author:       geyao
 * Date:         2017/6/14
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:
 * Created by geyao on 2017/6/14.
 */

public class PublishRewardAction extends YYBaseAction<RewardWithStudentSTCDTO> {

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  具体的构造函数
     * @param rewardDTO
     */
    public PublishRewardAction(RewardDTO rewardDTO) {
        super();
        //根据reward新建controller
        this.controller = new PublishRewardController(rewardDTO);
    }


}
