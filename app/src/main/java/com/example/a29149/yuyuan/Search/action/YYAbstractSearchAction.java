package com.example.a29149.yuyuan.Search.action;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Util.Const;

/**
 * @Author:        geyao
 * @Date:          2017/6/12
 * @Description:   不使用回调完成搜索
 * //TODO 应当在构造函数中就指定listView与Adapter，这要求Adapter都继承自YYBaseAdapter
 */

public abstract class YYAbstractSearchAction extends AsyncTask<String, Integer, String> {
    //用于暂存当前请求的是第几页，如果为1，则表示要clear List
    protected String mPageNo = "1";

    @Override
    protected String doInBackground(String... params) {
        return before(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null && result.equals(Const.SUCCESS)) {
            //注入数据
            injectDataToGlobe();
        }
    }

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索前做的事情
     */
    protected abstract String  before(String... params);

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索后做的事情，请求第一页
     */
    protected abstract void getOnePage();

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索后做的事情，请求更多页
     */
    protected abstract void getMorePage();


    /**
     * 将输入分类注入
     *
     * @return‘
     */

    private void injectDataToGlobe() {
        if (Integer.parseInt(mPageNo) == 1) {
            getOnePage();
        } else {
            getMorePage();
        }
    }

}
