package com.example.a29149.yuyuan.TeacherMain.Score.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.OrderBuyCourseAsTeacherSTCDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Teacher.Index.course.OwnerCourseListAdapter;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.shapeloading.ShapeLoadingDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by MaLei on 2017/5/14.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师发布的课程
 */
public class OwnerCourseTeacherFragment extends Fragment {

    //课程列表
    private ListView mCourseList;

    //适配器
    private OwnerCourseListAdapter mAdapter;

    public static ShapeLoadingDialog shapeLoadingDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_course_teacher,null);

        mCourseList = (ListView) view.findViewById(R.id.course_list);

        shapeLoadingDialog = new ShapeLoadingDialog(getContext());
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);
        shapeLoadingDialog.show();

        loadData();

        return view;
    }

    private void loadData()
    {
        //如果没有进行加载
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.show();
            applyCourse();
        }
    }


    //获取我的课程
    private void applyCourse() {
        new ApplyCourseAction(1).execute();
    }
    /**
     * 获取我的课程请求Action
     */
    public class ApplyCourseAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public ApplyCourseAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token", token);
                target.put("pageNo", pageNo);
                java.net.URL url = new java.net.URL(URL.getGetMyCourseURL(target.toString()));
                Log.i("malei", target.toString());
                con = (HttpURLConnection) url.openConnection();
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("POST");
                con.setRequestProperty("contentType", "UTF-8");

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
                    String resultFlag = jsonObject.getString("result");

                    //存储所有我拥有的课程信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<OrderBuyCourseAsTeacherSTCDTO>>() {
                    }.getType();
                    List<OrderBuyCourseAsTeacherSTCDTO> orderBuyCourseAsTeacherSTCDTOs = new Gson().fromJson(jsonObject.getString("orderBuyCourseAsTeacherSTCDTOS"), type);
                    GlobalUtil.getInstance().setOrderBuyCourseAsTeacherSTCDTOs(orderBuyCourseAsTeacherSTCDTOs);
                    Log.i("malei", orderBuyCourseAsTeacherSTCDTOs.toString());

                    if (resultFlag.equals("success")) {
                        Toast.makeText(getContext(), "获取课程成功！", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new OwnerCourseListAdapter(getContext());
                                mCourseList.setAdapter(mAdapter);
                                shapeLoadingDialog.dismiss();

                            }
                        }, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}