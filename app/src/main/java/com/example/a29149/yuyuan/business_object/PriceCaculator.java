package com.example.a29149.yuyuan.business_object;

import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.business_object.com.AbstractBO;

/**
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  价格计算器
 * Created by geyao on 2017/6/15.
 */

public class PriceCaculator extends AbstractBO {

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  计算用户实际要支付的价格
     * @param rewardDTO 悬赏DTO
     * @param couponDTO 优惠券DTO
     * @return
     */
    public static double getRewardPrice(RewardDTO rewardDTO, CouponDTO couponDTO){
        //FIXME 目前没有对优惠券做处理
        return rewardDTO.getPrice();
    }
}
