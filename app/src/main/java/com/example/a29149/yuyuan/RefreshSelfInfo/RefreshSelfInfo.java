package com.example.a29149.yuyuan.RefreshSelfInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by 张丽华 on 2017/5/2.
 * Description:获取用户信息
 */

public class RefreshSelfInfo extends AsyncTask<String, Integer, String> {

    private Context mContext;

    public RefreshSelfInfo(Context context) {
        super();
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {

//            java.net.URL url = new java.net.URL(URL.getSelfInfoURL());
//            con = (HttpURLConnection) url.openConnection();
//
//            log.d(this, URL.getSelfInfoURL());
//
//            // 设置允许输出，默认为false
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.setConnectTimeout(5 * 1000);
//            con.setReadTimeout(10 * 1000);
//
//            con.setRequestMethod("POST");
//            con.setRequestProperty("contentType", "UTF-8");
            JSONObject content = new JSONObject();
            content.put(HttpSender.token, GlobalUtil.getInstance().getToken());
            //加密传输
            con = HttpSender.send( URL.selfInfoURL, AESOperator.getInstance().encode(content));

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
                if (resultFlag.equals("success")) {
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    Activity activity = (Activity)mContext;
                    activity.finish();
                    //获取学生信息dto
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                    }.getType();
                    StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentDTO"), type);

                    if (studentDTO != null)
                    {
                        GlobalUtil.getInstance().setStudentDTO(studentDTO);
                        Log.i("malei",studentDTO.toString());
                    }

                    //获取老师信息dto
                    java.lang.reflect.Type type1 = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                    }.getType();
                    TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("teacherDTO"), type1);
                    if (teacherDTO != null)
                    {
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                        Log.i("malei",teacherDTO.toString());
                    }
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
