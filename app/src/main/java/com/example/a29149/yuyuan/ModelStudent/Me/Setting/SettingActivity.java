package com.example.a29149.yuyuan.ModelStudent.Me.Setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;
import com.example.a29149.yuyuan.Login.LoginActivity;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Main.MainTeacherActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.controller.userInfo.LogOutController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;
import com.google.gson.Gson;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;
/**
 * Created by MaLei on 2017/5/11.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生设置：切换到老师，登出
 */
public class SettingActivity extends AbstractAppCompatActivity {

    private TextView mChangeRole;//切换用户角色
    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);



        displayInfo = new WarningDisplayDialog.Builder( this );
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("确      定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //点击确定后跳发送申请认证
                new ApplyAuthenticationTeacherAction().execute();
                Intent intent = new Intent( SettingActivity.this, MainTeacherActivity.class);
                startActivity(intent);
            }
        });
        displayInfo.create();


    }

    @OnClick(R.id.ib_return)
    public void setBtReturnListener(View view) {
        this.onBackPressed();
        AppManager.getInstance().removeActivity(MainStudentActivity.class);
    }

    @OnClick(R.id.tv_logOut)
    public void setBackListener(View view) {
        String id = GlobalUtil.getInstance().getId();
        MiPushClient.unsetUserAccount(SettingActivity.this, id, null);

        LogOutAction logOutAction = new LogOutAction();
        logOutAction.execute();
    }

    @OnClick(R.id.tv_switch)
    public void Switch(View view){
        if ( GlobalUtil.getInstance().getUserRole().equals(RoleEnum.student.toString()) )
            changeRole();
        else
            changeBackTOStudent();
    }




    //切换用户角色
    private void changeRole() {
        //验证身份
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();

        if(teacherDTO != null)
        {

            VerifyStateEnum verifyState = teacherDTO.getVerifyState();

            //如果是已认证老师或者是认证中的老师，则直接接单
            if(verifyState.compareTo(VerifyStateEnum.processing) == 0
                    || verifyState.compareTo(VerifyStateEnum.verified) == 0)
            {
                //更改用户角色
                GlobalUtil.getInstance().setUserRole("teacher");
                Intent intent = new Intent( SettingActivity.this,MainTeacherActivity.class);
                startActivity(intent);
            }
            else
            {
                //Toast.makeText(getContext(), "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
                displayInfo.setMsg("抱歉，您现在不是已认证老师，请先认证？\n \n 点击 确定 申请认证");

                displayInfo.getDialog().show();

            }
        }
        else
        {
            Log.i("malei","teacherDTO是空的");
            //不是已认证老师，跳转到申请认证页面
            Toast.makeText( SettingActivity.this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            displayInfo.setMsg("抱歉，您现在不是已认证老师，请先认证？\n \n 点击 确定 申请认证");

            displayInfo.getDialog().show();
        }
    }

    /**
     * 老师切换回学生
     */
    private void changeBackTOStudent(){
        GlobalUtil.getInstance().setUserRole(RoleEnum.student.toString());
        Intent toStudent = new Intent(SettingActivity.this, MainStudentActivity.class);
        Toast.makeText(this, "已经切换回学生了哦", Toast.LENGTH_LONG).show();
        startActivity(toStudent);

        finish();
    }


    //登出
    public class LogOutAction extends AsyncTask<String, Integer, String> {

        public LogOutAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            return LogOutController.execute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        //注销账户时反注销推送id，用以不接受消息
                        String id = GlobalUtil.getInstance().getId();
                        MiPushClient.unsetUserAccount(SettingActivity.this, id, null);

                        AppManager.getInstance().finishActivity(MainStudentActivity.class);

                        UserConfig mUserConfig = new UserConfig(SettingActivity.this);
                        mUserConfig.clear();

                        //退出时清空Global里面的东西，否则老师DTO会影响下一位用户接单
                        GlobalUtil.clear();

                        SettingActivity.this.finish();

                    } else {
                        Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }finally {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(SettingActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

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
                    if(teacherDTO != null)
                    {
                        //存储老师DTO
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);

                    }


                    if (resultFlag.equals("success")) {
                        Toast.makeText(SettingActivity.this, "认证成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SettingActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SettingActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



}
