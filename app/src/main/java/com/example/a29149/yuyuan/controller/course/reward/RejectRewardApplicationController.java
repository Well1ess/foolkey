package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.AbstractObject.YYBaseController;

import org.json.JSONException;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  学生拒绝老师申请的controller
 * Created by geyao on 2017/6/15.
 */

public class RejectRewardApplicationController<T> extends YYBaseController<T> {

    private String applicationId;

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  构造函数，
     * @param applicationId 申请的id
     */
    public RejectRewardApplicationController(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  在这里给url加上具体的链接，在jsonObject放置数据
     *
     * @throws JSONException
     */
    @Override
    protected void handle() throws JSONException {
        url += "/aes/reward/refuse";
        jsonObject.put("applicationId", applicationId);
    }

    /**
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  具体的Controller做自己的操作，只做成功的处理，不需要考虑失败
     *
     * @param s 后台返回的原始String，大多为JSON
     */
    @Override
    protected void afterHandle(String s) {
        //无返回值，无需做任何处理
    }

    /**
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取controller返回的DTO结果
     *
     * @return
     */
    @Override
    public T getDTO() {
        return null;
    }
}
