package com.example.a29149.yuyuan.Search.action;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.controller.search.SearchRewardController;

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
    //进行搜索的Controller
    private SearchRewardController searchRewardController = new SearchRewardController();
    //回调接口，由各个Fragment去实现
    private AfterResult afterResult;

    //搜索条件们
    public static final String FILTER1 = "course";
    public static final String FILTER2 = "reward";
    public static final String FILTER3 = "article";
    public static final String FILTER4 = "teacher";
    public static final String FILTER5 = "question";

    @Override
    protected String doInBackground(String... params) {
        //params[0] condition
        mCondition = params[0];
        mPageNo = params[1];
        switch (mCondition) {
            case FILTER1:
                //TODO
                break;
            case FILTER2:
                searchRewardController.setKeyWord(params[2]);
                searchRewardController.setPageNo(params[1]);
                searchRewardController.execute();
                return searchRewardController.getResult();
            case FILTER3:
                //TODO
                break;
            case FILTER4:
                //TODO
                break;
            case FILTER5:
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
                case FILTER1:
                    break;
                case FILTER2:
                    afterResult.handleResult( searchRewardController.getRewardWithStudentSTCDTOList() );
                    break;
                case FILTER3:
                    break;
                case FILTER4:
                    break;
                case FILTER5:
                    break;
            }
        } else {
            switch (mCondition) {
                case FILTER1:
                    break;
                case FILTER2:
                    afterResult.handleResult( searchRewardController.getRewardWithStudentSTCDTOList() );
                    break;
                case FILTER3:
                    break;
                case FILTER4:
                    break;
                case FILTER5:
                    break;
            }
        }
    }

}
