package com.example.a29149.yuyuan.Model.Publish.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.course.reward.GetWithApplicationController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生申请认证老师
 */

public class ApplyAuthenticationTeacherActivity extends Activity implements View.OnClickListener {

    private Button mApply;//学生点击申请认证老师
    private Button mApplyReaward;//查看我发布的悬赏
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_authentication_teacher);
        initView();

    }

    private void initView() {
        mApply = (Button) findViewById(R.id.bt_apply);
        mApply.setOnClickListener(this);
        mApplyReaward = (Button) findViewById(R.id.bt_applyReward);
        mApplyReaward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_apply:
                apply();
                break;
            case R.id.bt_applyReward:
                applyReward();
                break;
            default:
                break;
        }
    }

    //查看我发布的悬赏
    private void applyReward() {
        new ApplyRewardAction(1).execute();
    }

    //学生申请认证老师
    private void apply() {
        new ApplyAuthenticationTeacherAction().execute();
    }

    /**
     * 认证老师请求Action
     */
    public class ApplyAuthenticationTeacherAction extends AsyncTask<String, Integer, String> {

        public ApplyAuthenticationTeacherAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return ApplyToVerifyController.execute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                    }.getType();
                    StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentDTO"), type);
                    //存储学生信息DTO
                    GlobalUtil.getInstance().setStudentDTO(studentDTO);
                    //获取老师信息DTO
                    java.lang.reflect.Type type1 = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                    }.getType();
                    TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("teacherDTO"), type1);
                    Log.i("malei",jsonObject.getString("teacherDTO"));
                    if(teacherDTO != null)
                    {
                        //存储老师DTO
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                        Log.i("geyao  ", "认证后存储老师DTO了嘛？ " + this.getClass());
                    }


                    if (resultFlag.equals("success")) {
                        Toast.makeText(ApplyAuthenticationTeacherActivity.this, "认证成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ApplyAuthenticationTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyAuthenticationTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 获取我的悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {

        int pageNo;

        public ApplyRewardAction(int pageNo) {
            super();
            this.pageNo = pageNo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return GetWithApplicationController.execute(pageNo + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    //存储所有我拥有的悬赏信息DTO
                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<ApplicationStudentRewardAsStudentSTCDTO>>() {
                    }.getType();
                    List<ApplicationStudentRewardAsStudentSTCDTO> applicationStudentRewardAsStudentSTCDTOs = new Gson().fromJson(jsonObject.getString("applicationStudentRewardAsStudentSTCDTOS"), type);
                    GlobalUtil.getInstance().setApplicationStudentRewardAsStudentSTCDTOs(applicationStudentRewardAsStudentSTCDTOs);
                    Log.i("malei",applicationStudentRewardAsStudentSTCDTOs.toString());
                    Log.i("malei",GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(0).getRewardDTO().toString());


                    if (resultFlag.equals("success")) {
                        Toast.makeText(ApplyAuthenticationTeacherActivity.this, "获取悬赏成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ApplyAuthenticationTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyAuthenticationTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
