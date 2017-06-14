package com.example.a29149.yuyuan.AbstractObject;

import android.util.Log;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.controller.AbstractController;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * controller的基类，它的这里的T，由2个地方指定，子类Controller，子类Action，他们的T应当是一致的
 * Created by geyao on 2017/6/14.
 */

public abstract class YYBaseController<T> extends AbstractController {
    private static final String TAG = "YYBaseController";
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
                if (resultFlag.equals(Const.SUCCESS)) {
                    this.result = Const.SUCCESS;
                    //根据返回的结果标志进行不同的操作
                    afterHandle(s);
                }
                else
                    this.result = Const.FAIL;
            }
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
    protected abstract void handle() throws JSONException;


    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  具体的Controller做自己的操作，只做成功的处理，不需要考虑失败
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

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  获取controller返回的DTO结果
     * @return
     */
    public abstract T getDTO();

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  设定结果
     * @param result
     */
    protected void setResult(String result){
        this.result = result;
    }
}
