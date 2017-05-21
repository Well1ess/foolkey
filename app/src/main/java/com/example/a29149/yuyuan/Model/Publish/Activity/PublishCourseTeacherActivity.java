package com.example.a29149.yuyuan.Model.Publish.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.course.course.PublishController;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by MaLei on 2017/5/3 0014.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师发布课程
 */

public class PublishCourseTeacherActivity extends Activity implements View.OnClickListener {

    private TextView mCancel;//取消
    private TextView mPublish;//发布
    private EditText mCourseTitle;//课程标题
    private TextView mCourseTag;//课程标签
    private  RelativeLayout mCourseTagChoose;//课程标签选择按钮
    private EditText mCourseDescription;//课程描述
    private  EditText mCourseProfit;//课程收益
    private  EditText mCourseDuration;//课程总时长
    private Button mAmountDecrease;//课程总时长增加减少按钮
    private Button mAmountIncrease;
    private TextView mCourseType;//授课方式
    private  RelativeLayout mCourseStyleChoose;//选择授课方式按钮
    private TextView mTeacherTime;//老师可以上课的时间
    private RelativeLayout mTeacherTimeChoose;//选择学生上课的时间按钮
    private String[] mChooseContent;//保存用户选择的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_course_teacher);
        initView();
        mChooseContent = new String[7];
    }

    private void initView() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mCancel.setOnClickListener(this);
        mPublish = (TextView) findViewById(R.id.tv_publish);
        mPublish.setOnClickListener(this);
        //课程标题
        mCourseTitle = (EditText) findViewById(R.id.et_title);
        //课程标签
        mCourseTag = (TextView) findViewById(R.id.tv_tag);
        mCourseTagChoose = (RelativeLayout) findViewById(R.id.rl_tagItemChoose);
        mCourseTagChoose.setOnClickListener(this);
        //课程描述
        mCourseDescription = (EditText) findViewById(R.id.et_description);
        //课程收益
        mCourseProfit = (EditText) findViewById(R.id.et_price);
        //课程总时长
        mCourseDuration = (EditText) findViewById(R.id.et_Amount);
        //课程总时长增加减少按钮
        mAmountDecrease = (Button) findViewById(R.id.btnDecrease);
        mAmountDecrease.setOnClickListener(this);
        mAmountIncrease = (Button) findViewById(R.id.btnIncrease);
        mAmountIncrease.setOnClickListener(this);
        //授课方式
        mCourseType = (TextView) findViewById(R.id.tv_courseType);
        mCourseStyleChoose = (RelativeLayout) findViewById(R.id.rl_courseStyleChoose);
        mCourseStyleChoose.setOnClickListener(this);
        //老师可以上课的时间
        mTeacherTime = (TextView) findViewById(R.id.tv_studentTime);
        mTeacherTimeChoose = (RelativeLayout) findViewById(R.id.rl_studentTimeChoose);
        mTeacherTimeChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_publish:
                publish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.rl_tagItemChoose:
                chooseTag();
                break;
            case R.id.rl_courseStyleChoose:
                chooseCourseType();
                break;
            case R.id.rl_studentTimeChoose:
                chooseStudentTime();
                break;
            case R.id.btnDecrease:
                decreaseAmount();
                break;
            case R.id.btnIncrease:
                increaseAmount();
                break;
            default:
                break;
        }
    }


    //课程总时长加1
    private void increaseAmount() {
        Log.i("malei","increaseAmount");
        int i = Integer.parseInt(mCourseDuration.getText().toString()) + 1;
        mCourseDuration.setText(String.valueOf(i));
    }
    //课程总时长减1
    private void decreaseAmount() {
        int i = Integer.parseInt(mCourseDuration.getText().toString()) - 1;
        if(i<1)
            i=1;
        mCourseDuration.setText(String.valueOf(i));
    }

    //点击发布
    private void publish() {
        mChooseContent[0] = mCourseTitle.getText().toString();
        mChooseContent[2] = mCourseDescription.getText().toString();
        mChooseContent[3] = mCourseProfit.getText().toString();
        mChooseContent[4] = mCourseDuration.getText().toString();
        new PublishCourseAction().execute();
    }

    //弹出课程选择标签
    private AlertDialog alertDialogChooseTag;
    public void chooseTag(){
        final String[] courseTag = {"Java","C", "Javascript","Python","PHP","Html5","Android","iOS","数据库","其它"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择课程标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                courseTag);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCourseTag.setText("标签："+courseTag[which]);
                        mChooseContent[1] = courseTag[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
    }


    //弹出授课方式选择标签
    private AlertDialog alertDialogChooseCourseType;
    public void  chooseCourseType(){
        final String[] courseType = {"线上","线下", "不限"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择授课方式");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                courseType);

        alertDialogChooseCourseType = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCourseType.setText("授课方式："+courseType[which]);
                        mChooseContent[5] = courseType[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseCourseType.show();
    }

    //弹出选择老师可以上课的时间
    private AlertDialog alertDialogChooseTeacherTime;
    private void chooseStudentTime() {
        final String[] teacherTime = {"工作日","节假日","不限"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择您可以上课的时间");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                teacherTime);

        alertDialogChooseTeacherTime = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeacherTime.setText("您可以上课的时间："+teacherTime[which]);
                        mChooseContent[6] = teacherTime[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTeacherTime.show();
    }


    /**
     * 发布请求Action，发布课程
     */
    public class PublishCourseAction extends AsyncTask<String, Integer, String> {

        public PublishCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return PublishController.execute(
                    mChooseContent[0],
                    mChooseContent[1],
                    mChooseContent[2],
                    mChooseContent[3],
                    mChooseContent[4],
                    mChooseContent[5],
                    mChooseContent[6]
            );

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
                        Toast.makeText(PublishCourseTeacherActivity.this, "发布课程成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PublishCourseTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PublishCourseTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
