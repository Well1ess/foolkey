package com.example.a29149.yuyuan.Search.action;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.controller.search.SearchRewardController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张丽华 on 2017/5/6.
 * Description:
 * condition:course,reward,article,teacher,question
 * pageNo,
 * keyWord,
 * 明文传输
 *
 * //TODO 应当在构造函数中就指定listView与Adapter，这要求Adapter都继承自YYBaseAdapter
 */

public class SearchAction extends AsyncTask<String, Integer, String> {
    //用于暂存当前请求的是第几页，如果为1，则表示要clear List
    private String mPageNo;
    //用于暂存当前搜索的是什么
    private String mCondition;
    //关键字
    private String keyValue;


    private SearchRewardController searchRewardController = new SearchRewardController();

    private SearchActivity searchActivity;

    //回调接口，由各个Fragment去实现
    private AfterResult afterResult;

    //存放搜索结果
    private List dataList = new ArrayList();

    public SearchAction(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }


    @Override
    protected String doInBackground(String... params) {
        //params[0] condition
        mCondition = params[0];
        mPageNo = params[1];
        switch (mCondition) {
            case "course":
                //TODO
                break;
            case "reward":
                searchRewardController.setKeyWord(params[2]);
                searchRewardController.setPageNo(params[1]);
                searchRewardController.execute();
                return searchRewardController.getResult();
            case "article":
                //TODO
                break;
            case "teacher":
                //TODO
                break;
            case "question":
                //TODO
                break;
        }
        return null;
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
     * 回调接口
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   用来处理搜索到结果后的ListView与Adapter的绑定
     */
    public interface AfterResult{
        /**
         * @Author:        geyao
         * @Date:          2017/6/12
         * @Description:   将搜索到的数据作为参数传进去
         * @param data
         */
        public void handleResult(List data);

    }

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   回调的设值函数
     * @param afterResult
     */
    public void setAfterResult(AfterResult afterResult) {
        this.afterResult = afterResult;
    }

    /**
     * 将输入分类注入
     *
     * @return‘
     */

    private void injectDataToGlobe() {
        if (Integer.parseInt(mPageNo) == 1) {
            switch (mCondition) {
                case "course":
                    break;
                case "reward":
                    afterResult.handleResult( searchRewardController.getRewardWithStudentSTCDTOList() );
                    break;
                case "article":
                    break;
                case "teacher":
                    break;
                case "question":
                    break;
            }
        } else {
            switch (mCondition) {
                case "course":
                    break;
                case "reward":
                    afterResult.handleResult( searchRewardController.getRewardWithStudentSTCDTOList() );
                    break;
                case "article":
                    break;
                case "teacher":
                    break;
                case "question":
                    break;
            }
        }
    }

}
