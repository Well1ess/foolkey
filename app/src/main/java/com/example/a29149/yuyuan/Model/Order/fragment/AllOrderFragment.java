package com.example.a29149.yuyuan.Model.Order.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewFinishCourseAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewFinishRewardAdapter;
import com.example.a29149.yuyuan.Model.Order.adapter.MyListViewRecommandAdapter;
import com.example.a29149.yuyuan.Model.Order.view.MyListView;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.JSONUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by MaLei on 2017/4/29.
 * Email:ml1995@mail.ustc.edu.cn
 * 全部订单的Fragment
 */

public class AllOrderFragment extends Fragment {

    private Context mContext;
    private MyListView mBuyCourse;
    private MyListView mReward;
    private MyListView mRecommand;
    private List<Map<String,Object>> courseNoPayList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_viewpager_all_order, null);
        mBuyCourse = (MyListView) view.findViewById(R.id.lv_buyCourse);
        mBuyCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });
        mReward = (MyListView) view.findViewById(R.id.lv_reward);
        mReward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });
        mRecommand = (MyListView) view.findViewById(R.id.lv_recommend);
        mRecommand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("malei","你点击了"+position);
            }
        });


        //requestData();

        MyListViewFinishCourseAdapter myListViewFinishCourseAdapter = new MyListViewFinishCourseAdapter(mContext);
        myListViewFinishCourseAdapter.setData(courseNoPayList);

        MyListViewFinishRewardAdapter MyListViewFinishRewardAdapter = new MyListViewFinishRewardAdapter(mContext);

        MyListViewRecommandAdapter myListViewRecommandAdapter = new MyListViewRecommandAdapter(mContext);

        mBuyCourse.setAdapter(myListViewFinishCourseAdapter);
        mReward.setAdapter(MyListViewFinishRewardAdapter);
        mRecommand.setAdapter(myListViewRecommandAdapter);
        return view;
    }

    //请求数据
    private void requestData() {
        new RequestNoPayCourseAction().execute();
    }
    /**
     * 请求以购买未付款的订单Action
     */
    public class RequestNoPayCourseAction extends AsyncTask<String, Integer, String> {

        public RequestNoPayCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token",token);
                /*target.put("topic",mChooseContent[0]);
                target.put("technicTagEnum",mChooseContent[1]);
                target.put("description",mChooseContent[2]);
                target.put("price",mChooseContent[3]);
                target.put("duration",mChooseContent[4]);
                target.put("teachMethodEnum",mChooseContent[5]);
                target.put("courseTimeDayEnum",mChooseContent[6]);*/

                java.net.URL url = new java.net.URL(URL.getTeacherPublishCoursedURL(target.toString()));
                Log.i("malei",target.toString());
                con = (HttpURLConnection) url.openConnection();
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("POST");
                con.setRequestProperty("contentType", "GBK");

                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    courseNoPayList = JSONUtil.jsonObject2List(jsonObject,"keyName");
                    String resultFlag = jsonObject.getString("result");
                    if (resultFlag.equals("success")) {
                        Toast.makeText(mContext, "发布课程成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
