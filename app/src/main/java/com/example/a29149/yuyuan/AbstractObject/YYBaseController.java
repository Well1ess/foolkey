package com.example.a29149.yuyuan.AbstractObject;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by geyao on 2017/6/14.
 */

public abstract class YYBaseController extends AbstractController {

    protected JSONObject jsonObject;

    protected String url = address ;

    //结果
    private String result = Const.FAIL;


    public final String execute(){
        try {
            jsonObject = getJSON();
            //每个具体的Controller要做的事情，
            handle();
            //发送数据
            String s = HttpSender.send(url, jsonObject);

            //把后台传来的数据String转为JSON
            jsonObject = new JSONObject(s);
            String resultFlag = jsonObject.getString("result");
            if(resultFlag == null){
                this.result = resultFlag = Const.FAIL;
            }else {
                if (resultFlag.equals(Const.SUCCESS))
                    this.result = Const.SUCCESS;
                else
                    this.result = Const.FAIL;
            }
            //根据返回的结果标志进行不同的操作
            afterHandle(s);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return failJSON();
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  在这里给url加上具体的链接，在jsonObject放置数据
     * @throws JSONException
     */
    public abstract void handle() throws JSONException;


    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  具体的Controller做自己的操作
     * @param s 后台返回的原始String
     */
    protected abstract void afterHandle(String s);

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:
     * @return       后台返回的Result
     */
    public String getResult() {
        return result;
    }
}
