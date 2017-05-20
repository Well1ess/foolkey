package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 *
 * 这里的课程依然写死了
 *
 *
 * 老师申请接单悬赏
 */
public class ApplyRewardTeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private  Button mApplyReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_reward_teacher);
        initView();

    }

    private void initView() {
        mApplyReward = (Button) findViewById(R.id.bt_apply);
        mApplyReward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_apply:
                applyRewardTeacher();
                break;
            default:
                break;
        }
    }

    //老师申请接单悬赏
    private void applyRewardTeacher() {
        //验证身份
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();

        if(teacherDTO != null)
        {
            Log.i("malei",teacherDTO.toString());
            VerifyStateEnum verifyState = teacherDTO.getVerifyState();
            Log.i("malei",verifyState.toString());
            Log.i("malei",teacherDTO.toString());
            Log.i("malei",verifyState.toString());
            //如果是已认证老师或者是认证中的老师，则直接接单
            if(verifyState.compareTo(VerifyStateEnum.processing) == 0
                    || verifyState.compareTo(VerifyStateEnum.verified) == 0)
            {
                new ApplyRewardAction().execute();
            }
            else
            {
                Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ApplyRewardTeacherActivity.this, ApplyAuthenticationTeacherActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            Log.i("malei","teacherDTO是空的");
            //不是已认证老师，跳转到申请认证页面
            Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ApplyRewardTeacherActivity.this, ApplyAuthenticationTeacherActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 申请悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {


        public ApplyRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token", token);
                target.put("courseId", "这里有bug");

                return HttpSender.send(URL.applyRewardTeacherURL, target);


            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
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
                        Toast.makeText(ApplyRewardTeacherActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ApplyRewardTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyRewardTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
