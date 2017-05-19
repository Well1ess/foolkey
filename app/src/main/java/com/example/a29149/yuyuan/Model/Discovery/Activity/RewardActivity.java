package com.example.a29149.yuyuan.Model.Discovery.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseStudentDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.HttpSender;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏详情
 */

public class RewardActivity extends AppCompatActivity {

    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;
    private RadioButton mOrder;//我要接单
    private int position = -1;//item位置
    private StudentDTO studentDTO;//发布悬赏的学生信息
    private CourseStudentDTO courseStudentDTO;//悬赏信息
    private TextView mRewardUser;//发布悬赏人的信息
    private TextView mTeacherEvaluate;//悬赏价格
    private TextView mRewardTopic;//悬赏标题
    private TextView mRewardDescription;//悬赏描述
    private TextView mCreateTime;//创建悬赏的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        position = extras.getInt("position");
        Log.i("malei",position+"");
        if(position != -1)
        {
            studentDTO = GlobalUtil.getInstance().getCourseStudentPopularDTOs().get(position).getStudentDTO();
            courseStudentDTO = GlobalUtil.getInstance().getCourseStudentPopularDTOs().get(position).getCourseStudentDTO();

        }
        else
        {
            studentDTO = new StudentDTO();
            courseStudentDTO = new CourseStudentDTO();
        }
        initView();
        initData();

        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

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
                //点击接单后发送请求
                applyRewardTeacher();
            }
        });
        displayInfo.create();
    }

    //为底部菜单添加监听
    @OnClick({R.id.want_learn, R.id.chart, R.id.order})
    public void setMenuListener(View view)
    {
        switch (view.getId())
        {
            case R.id.want_learn:
                //TODO:网络传输
                break;
            case R.id.chart:
                //TODO:网络传输
                break;
            case R.id.order:
                displayInfo.setMsg("您确定要此单吗？\n \n 点击 接单 将发送申请");

                displayInfo.getDialog().show();
                break;
        }
    }

    private void initView() {
        mOrder = (RadioButton) findViewById(R.id.order);
        //mOrder.setOnClickListener(this);

        mRewardUser = (TextView) findViewById(R.id.courseEvaluate);
        mTeacherEvaluate =(TextView) findViewById(R.id.teacherEvaluate);
        mRewardTopic = (TextView) findViewById(R.id.tv_topic);
        mRewardDescription = (TextView) findViewById(R.id.tv_description);
        mCreateTime = (TextView) findViewById(R.id.tv_createTime);
    }


    private void initData() {
        mRewardUser.setText(studentDTO.getNickedName());
        mTeacherEvaluate.setText(courseStudentDTO.getPrice()+"");
        mRewardTopic.setText(courseStudentDTO.getTopic()+"");
        mRewardDescription.setText(courseStudentDTO.getDescription());
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
        if(verifyState.compareTo( VerifyStateEnum.processing ) == 0
                || verifyState.compareTo( VerifyStateEnum.verified) == 0)
        {
            new RewardActivity.ApplyRewardAction().execute();
        }
        else
        {
            Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RewardActivity.this, ApplyAuthenticationTeacherActivity.class);
            startActivity(intent);
        }
    }
    else
    {
        Log.i("malei","teacherDTO是空的");
        //不是已认证老师，跳转到申请认证页面
        Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RewardActivity.this, ApplyAuthenticationTeacherActivity.class);
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
                //构建JSON
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token", token);
                target.put("courseId", courseStudentDTO.getId());
                return HttpSender.sendWithAES( URL.applyRewardTeacherURL, target );
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
                    Log.i("malei",result);


                    if (resultFlag.equals("success")) {
                        Toast.makeText(RewardActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(RewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
