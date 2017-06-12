package com.example.a29149.yuyuan.Search;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.controller.search.SearchRewardController;

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

    //填充数据的适配器
    private RewardListAdapter rewardListAdapter;

    private SearchRewardController searchRewardController = new SearchRewardController();

    private SearchActivity searchActivity;

    public SearchAction(SearchActivity searchActivity, RewardListAdapter adapter) {
        this.searchActivity = searchActivity;
        this.rewardListAdapter = adapter;
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

                    rewardListAdapter.setData(searchRewardController.getRewardWithStudentSTCDTOList());

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
                    rewardListAdapter.getDataList().addAll(searchRewardController.getRewardWithStudentSTCDTOList());
                    rewardListAdapter.updateList();
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
