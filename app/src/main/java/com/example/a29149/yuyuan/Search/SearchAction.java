package com.example.a29149.yuyuan.Search;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.controller.search.SearchRewardController;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by 张丽华 on 2017/5/6.
 * Description:
 * condition:course,reward,article,teacher,question
 * pageNo,
 * keyWord,
 * 明文传输
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

    public SearchAction(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }


    @Override
    protected String doInBackground(String... params) {
        //params[0] condition
        mCondition = params[0];
        mPageNo = params[1];
        switch (params[0]) {
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
     * @return
     */

    private void injectDataToGlobe() {
        if (Integer.parseInt(mPageNo) == 1) {
            switch (mCondition) {
                case "course":
                    break;
                case "reward":
                    GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().clear();
                    GlobalUtil.getInstance().setRewardWithStudentSTCDTOs(searchRewardController.getRewardWithStudentSTCDTOList());
                    searchActivity.getResult(new GetSearchResultEvent("reward", true, keyValue));
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
                    EventBus.getDefault().post(new GetSearchResultEvent(mCondition, true, keyValue));
                    searchActivity.getResult(new GetSearchResultEvent("reward", true, keyValue));
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
