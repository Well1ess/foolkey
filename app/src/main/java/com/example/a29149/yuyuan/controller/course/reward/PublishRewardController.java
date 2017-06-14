package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.AbstractObject.YYBaseController;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发布悬赏的controller
 * Created by geyao on 2017/6/14.
 */

public class PublishRewardController extends YYBaseController {

    //需要的参数
    private RewardDTO rewardDTO;

    //成功时，返回的参数
    private RewardWithStudentSTCDTO rewardWithStudentSTCDTO;


    public PublishRewardController(RewardDTO rewardDTO) {
        super();
        this.rewardDTO = rewardDTO;
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
        url = address + "/aes/reward/publish";

        jsonObject.put("technicTagEnum", rewardDTO.getTechnicTagEnum().toString());
        jsonObject.put("topic", rewardDTO.getTopic());
        jsonObject.put("description", rewardDTO.getDescription());
        jsonObject.put("price", rewardDTO.getPrice());
        jsonObject.put("courseTimeDayEnum", rewardDTO.getCourseTimeDayEnum());
        jsonObject.put("teachMethodEnum", rewardDTO.getTeachMethodEnum());
        jsonObject.put("teachRequirementEnum", rewardDTO.getTeacherRequirementEnum());
        jsonObject.put("studentBaseEnum", rewardDTO.getStudentBaseEnum());
    }

    /**
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  具体的Controller做自己的操作，只考虑成功的操作
     *
     * @param s 后台返回的原始String
     */
    @Override
    protected void afterHandle(String s) {
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<RewardDTO>() {
        }.getType();
        RewardDTO rewardDTO = null;
        try {
            rewardDTO = new Gson().fromJson(jsonObject.getString("courseStudentDTO"), type);
        } catch (JSONException e) {
            e.printStackTrace();
            setResult(Const.FAIL);
            return;
        }
        //封装进缓存
        rewardWithStudentSTCDTO = new RewardWithStudentSTCDTO();
        rewardWithStudentSTCDTO.setRewardDTO(rewardDTO);
        rewardWithStudentSTCDTO.setStudentDTO( GlobalUtil.getInstance().getStudentDTO() );
        setResult(Const.SUCCESS);
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取结果
     *
     * @return
     */
    public RewardWithStudentSTCDTO getRewardWithStudentSTCDTO() {
        return rewardWithStudentSTCDTO;
    }
}
