package com.example.a29149.yuyuan.RefreshSelfInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.Main.MainActivity;
import com.example.a29149.yuyuan.Main.SplashActivity;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.userInfo.GetMyInfoController;
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
        return GetMyInfoController.execute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        log.d(this, result);

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
                Toast.makeText(mContext, "请求失败，请重新登录", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                Activity activity = (Activity)mContext;
//                activity.finish();
            }
        } else {
            Toast.makeText(mContext, "网络连接失败！", Toast.LENGTH_SHORT).show();
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            Activity activity = (Activity)mContext;
//            activity.finish();
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
