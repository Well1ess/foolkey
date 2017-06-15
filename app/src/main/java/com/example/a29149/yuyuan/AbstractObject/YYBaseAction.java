package com.example.a29149.yuyuan.AbstractObject;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

/**
 * 愚猿通用的异步网络通讯任务模板
 * action的基类，它的这里的T，由子类Action，决定
 * 子类action和子类controller 1对1 配套使用，共同指定一个相同的T
 * Activity在使用的时候，要写回调函数，在指定类型的时候，传进来的类也要与T相同
 *
 * Author:       geyao
 * Date:         2017/6/14
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:
 *
 * Created by geyao on 2017/6/14.
 */

public abstract class YYBaseAction<T> extends AsyncTask {

    //执行网络通讯的action
    protected YYBaseController<T> controller;

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  构造函数，每个子类都应该重新实现它
     */
    protected YYBaseAction(){
        super();
    }


    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  发送前做的事情，需要在具体的回调实现里，要新建controller
     * @param objects
     * @return
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            return controller.execute();
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        //网络异常
        if (o == null){
            onAsyncTask.onNull();
        }else {
            //成功处理
            if (controller.getResult().equals(Const.SUCCESS)) {
                onAsyncTask.onSuccess( controller.getDTO());
            }
            //失败处理
            if (controller.getResult().equals(Const.FAIL)) {
                onAsyncTask.onFail();
            }
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  回调接口
     *               这里的类型要和子类action设置的泛型保持一致
     */
    public interface OnAsyncTask<T>{


        public static final String FAIL_MESSAGE = "服务器繁忙，请稍后再试T_T";

        public static final String NULL_MESSAGE = "网络连接失败T_T";

        public static final String SUCCESS_MESSAGE = "请求成功";

        //返回成功时，做的处理
        public void onSuccess(T data);
        //返回失败时，做的处理
        public void onFail();
        //网络异常时，做的处理
        public void onNull();

    }

    private OnAsyncTask<T> onAsyncTask;

    public void setOnAsyncTask(OnAsyncTask<T> onAsyncTask){
        this.onAsyncTask = onAsyncTask;
    }
}
