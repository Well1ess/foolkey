package com.example.a29149.yuyuan.AbstractObject;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.controller.AbstractControllerTemplate;

/**
 * 愚猿通用的异步网络通讯任务模板
 * Created by geyao on 2017/6/14.
 */

public abstract class YYBaseAction extends AsyncTask {

    //执行网络通讯的action
    protected YYBaseController controller;

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
        onAsyncTask.beforeAction(controller);
        return controller.execute();
    }

    @Override
    protected void onPostExecute(Object o) {
        //网络异常
        if (o == null){
            onAsyncTask.onNull(controller);
        }else {
            //成功处理
            if (controller.getResult().equals(Const.SUCCESS)) {
                onAsyncTask.onSuccess(controller);
            }
            //失败处理
            if (controller.getResult().equals(Const.FAIL)) {
                onAsyncTask.onFail(controller);
            }
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  回调接口
     */
    public interface OnAsyncTask{
        //进行网络传输前的操作，一般在这里，对controller赋参数
        public Object beforeAction(YYBaseController controller);
        //返回成功时，做的处理
        public void onSuccess(YYBaseController controller);
        //返回失败时，做的处理
        public void onFail(YYBaseController controller);
        //网络异常时，做的处理
        public void onNull(YYBaseController controller);

    }

    private OnAsyncTask onAsyncTask;

    public void setOnAsyncTask(OnAsyncTask onAsyncTask){
        this.onAsyncTask = onAsyncTask;
    }
}
