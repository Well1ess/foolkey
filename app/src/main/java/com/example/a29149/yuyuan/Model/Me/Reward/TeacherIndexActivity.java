package com.example.a29149.yuyuan.Model.Me.Reward;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by MaLei on 2017/5/11.
 * Email:ml1995@mail.ustc.edu.cn
 * 申请悬赏的老师主页
 */

public class TeacherIndexActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTest;
    private TeacherAllInfoDTO mTeacherAllInfoDTO;//老师信息
    private StudentDTO mStudentDTO;//学生信息
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;//申请信息
    private RewardDTO mRewardDTO;//悬赏信息DTO
    private RadioButton mRewardAgree;//同意该老师申请
    private RadioButton mRewardDisagree;//不同意该老师申请
    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_index_reward);
        Intent intent = getIntent();
        String positionIn = intent.getStringExtra("positionIn");//gridview的position
        Log.i("malei",positionIn+"");
        Integer posiIn = Integer.parseInt(positionIn);
        String positionOut = intent.getStringExtra("positionOut");//listview的position
        Log.i("malei",positionOut+"");
        Integer posiOut = Integer.parseInt(positionOut);
        mTeacherAllInfoDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut)
                .getApplicationRewardWithTeacherSTCDTOS().get(posiIn).getTeacherAllInfoDTO();

        applicationStudentRewardDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut)
                .getApplicationRewardWithTeacherSTCDTOS().get(posiIn).getApplicationStudentRewardDTO();

        mRewardDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut).getRewardDTO();
        mStudentDTO = GlobalUtil.getInstance().getStudentDTO();
        Log.i("malei",mTeacherAllInfoDTO.toString());

        displayInfo = new WarningDisplayDialog.Builder(this);
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("接      单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.create();

        initView();
        initData();
    }

    private void initData() {
        String nickedName = mTeacherAllInfoDTO.getNickedName();
        mTest.setText(nickedName);
    }

    private void initView() {
        mTest = (TextView) findViewById(R.id.tv_test);
        mRewardAgree = (RadioButton) findViewById(R.id.main_menu_agree);
        mRewardAgree.setOnClickListener(this);
        mRewardDisagree = (RadioButton)findViewById(R.id.main_menu_disagree);
        mRewardDisagree.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.main_menu_agree:
                agreeApplyReward();
                break;
            case R.id.main_menu_disagree:
                new DisagreeApplyRewardAction().execute();
                break;
            default:
                break;
        }
    }

    //学生点击同意
    private void agreeApplyReward() {
        if(mStudentDTO.getVirtualCurrency() < mRewardDTO.getPrice()) {
            displayInfo.setMsg("您余额不足？\n \n 请及时充值");

            displayInfo.getDialog().show();
        }else{
            new AgreeApplyRewardAction().execute();
        }

    }


    /**
     * 拒绝申请悬赏请求Action
     */
    public class DisagreeApplyRewardAction extends AsyncTask<String, Integer, String> {


        public DisagreeApplyRewardAction() {
            super();
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
                target.put("token",token);
                target.put("courseId",mRewardDTO.getId());
                target.put("teacherId",mTeacherAllInfoDTO.getId());

                java.net.URL url = new java.net.URL(URL.getDisagreeApplyRewardURL(target.toString()));
                Log.i("malei",target.toString());
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

                    if (resultFlag.equals("success")) {
                        Toast.makeText(TeacherIndexActivity.this, "拒绝申请成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TeacherIndexActivity.this,OwnerRewardActivity.class);
                        TeacherIndexActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(TeacherIndexActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TeacherIndexActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 同意申请悬赏请求Action
     */
    public class AgreeApplyRewardAction extends AsyncTask<String, Integer, String> {


        public AgreeApplyRewardAction() {
            super();
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
                target.put("token",token);
                target.put("applicationId",applicationStudentRewardDTO.getId());
                target.put("rewardId",mRewardDTO.getId());
                target.put("couponId","");
                target.put("price",mRewardDTO.getPrice());

                java.net.URL url = new java.net.URL(URL.getAgreeApplyRewardURL(target.toString()));
                Log.i("malei",target.toString());
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

                    if (resultFlag.equals("success")) {
                        Toast.makeText(TeacherIndexActivity.this, "同意申请悬赏成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TeacherIndexActivity.this,OwnerRewardActivity.class);
                        TeacherIndexActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(TeacherIndexActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TeacherIndexActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
