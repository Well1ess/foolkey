package com.example.a29149.yuyuan.Search;

import android.os.AsyncTask;

import com.example.a29149.yuyuan.DTO.CourseTeacherDTO;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

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

    @Override
    protected String doInBackground(String... params) {
//        StringBuffer sb = new StringBuffer();
//        BufferedReader reader = null;
//        HttpURLConnection con = null;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("condition", params[0]);
            jsonObject.put("pageNo", params[1]);
            jsonObject.put("keyWord", params[2]);

            mPageNo = params[1];
            mCondition = params[0];

            return HttpSender.send( URL.searchURL, jsonObject );

//            java.net.URL url = new java.net.URL(URL.getSearchURL(jsonObject.toString()));
//            con = (HttpURLConnection) url.openConnection();
//            log.d(this, URL.getSearchURL(jsonObject.toString()));
//            // 设置允许输出，默认为false
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.setConnectTimeout(5 * 1000);
//            con.setReadTimeout(10 * 1000);
//
//            con.setRequestMethod("POST");
//            con.setRequestProperty("contentType", "UTF-8");
//
//
//            // 获得服务端的返回数据
//            InputStreamReader read = new InputStreamReader(con.getInputStream());
//            reader = new BufferedReader(read);
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);
                String resultFlag = jsonObject.getString("result");

                if (resultFlag.equals("success")) {

                    //获取结果
                    List<Object> targetList = setSearchList(jsonObject);

                    //注入数据
                    if (injectDataToGlobe(targetList))
                        EventBus.getDefault().post(new GetSearchResultEvent(mCondition, true, keyValue));
                    else
                        EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));

                } else {
                    EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
                }
            } catch (Exception e) {
                EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
            }
        } else {
            EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
        }
    }

    /**
     * 将输入分类注入
     * @param original
     * @return
     */
    private boolean injectDataToGlobe(List<Object> original)
    {
        //获取目标数组
        List<Object> target = null;

        switch (mCondition) {
            case "course":
                target = GlobalUtil.getInstance().getCourseList();
                break;
            case "reward":
                target = GlobalUtil.getInstance().getRewardList();
                break;
            case "article":
                target = GlobalUtil.getInstance().getArticleList();
                break;
            case "teacher":
                target = GlobalUtil.getInstance().getTeacherList();
                break;
            case "question":
                target = GlobalUtil.getInstance().getQuestionList();
                break;
        }

        if (target == null)
        {
            EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
            return false;
        }

        //若>1则表示分页存取
        if (Integer.parseInt(mPageNo) == 1) {
            target = original;
            return true;
        } else  if (Integer.parseInt(mPageNo) > 1){
            target.addAll(original);
            return true;
        }

        return false;
    }

    /**
     * 根据condition特异的提取不同的结果
     *
     * @param target
     * @return
     */
    private List<Object> setSearchList(JSONObject target) {
        java.lang.reflect.Type type = null;

        switch (mCondition) {
            case "course":
                type = new com.google.gson.
                        reflect.TypeToken<List<CourseTeacherDTO>>() {
                }.getType();
                break;
            case "reward":
                type = new com.google.gson.
                        reflect.TypeToken<List<CourseTeacherDTO>>() {
                }.getType();
                break;
            case "article":
                type = new com.google.gson.
                        reflect.TypeToken<List<CourseTeacherDTO>>() {
                }.getType();
                break;
            case "teacher":
                type = new com.google.gson.
                        reflect.TypeToken<List<CourseTeacherDTO>>() {
                }.getType();
                break;
            case "question":
                type = new com.google.gson.
                        reflect.TypeToken<List<CourseTeacherDTO>>() {
                }.getType();
                break;
        }

        if (type == null) {
            EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
            return null;
        }

        List<Object> result;
        try {
            result = new Gson().fromJson(target.getString("courseTeacherDTOS"), type);

        } catch (JSONException e) {
            EventBus.getDefault().post(new GetSearchResultEvent(mCondition, false, keyValue));
            return null;
        }

        return result;
    }
}
