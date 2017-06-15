package com.example.a29149.yuyuan.controller.course.reward;

import com.example.a29149.yuyuan.AbstractObject.YYBaseController;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 获取我发布的 带解决的 悬赏，返回的信息里包括申请的老师
 * 但不会包括所有的老师，如果某个悬赏有特别多的老师申请，只会随即返回 4 条
 * 已经上课、或者等待上课不再此列
 * Created by geyao on 2017/5/21.
 */

public class GetRewardApplicationController extends YYBaseController<List<ApplicationStudentRewardAsStudentSTCDTO>> {

    //请求的页码
    private int pageNo = 1;

    //返回的结果值
    private List<ApplicationStudentRewardAsStudentSTCDTO> data;

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  构造函数
     *
     * @param pageNo
     */
    public GetRewardApplicationController(int pageNo) {
        super();
        this.pageNo = pageNo;
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
        url += "/aes/reward/getWithApplication";

        jsonObject.put("pageNo", pageNo);
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
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<ApplicationStudentRewardAsStudentSTCDTO>>() {
        }.getType();
        try {
            data = new Gson().fromJson(jsonObject.getString("applicationStudentRewardAsStudentSTCDTOS"), type);
        } catch (JSONException e) {
            e.printStackTrace();
            setResult(Const.FAIL);
            return;
        }
        //封装进缓存
        setResult(Const.SUCCESS);
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
    public List<ApplicationStudentRewardAsStudentSTCDTO> getDTO() {
        return data;
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  设置要传递的参数
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
