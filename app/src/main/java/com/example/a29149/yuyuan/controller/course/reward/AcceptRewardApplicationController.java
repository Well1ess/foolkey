package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.AbstractObject.YYBaseController;

import org.json.JSONException;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  接收悬赏申请的controller
 * Created by geyao on 2017/6/15.
 */

public class AcceptRewardApplicationController extends YYBaseController<Object> {

    //申请的id
    private String applicationId;
    //优惠券的id
    private String couponId;
    //价格的Id
    private String price;

    public AcceptRewardApplicationController(String applicationId, String couponId, String price) {
        super();
        this.applicationId = applicationId;
        this.couponId = couponId;
        this.price = price;
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
        url += "/aes/acceptRewardApplication";

        jsonObject.put("applicationId", applicationId);
        jsonObject.put("couponId", couponId);
        jsonObject.put("price", price);
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
        //没有数据返回
        //不用做数据转换
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
    public Object getDTO() {
        return null;
    }
}
