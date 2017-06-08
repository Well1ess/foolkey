package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/1 0014.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生发布悬赏
 */
@Deprecated
public class PublishRewardStudentActivity extends AbstractActivity implements View.OnClickListener {

    private TextView mCancel;//取消
    private TextView mPublish;//发布
    private EditText mRewardTitle;//悬赏标题
    private TextView mRewardTag;//悬赏标签
    private  RelativeLayout mRewardTagChoose;//悬赏标签选择按钮
    private EditText mRewardDescription;//悬赏描述
    private  EditText mRewardCost;//悬赏费用
    private TextView mCourseType;//授课方式
    private  RelativeLayout mCourseStyleChoose;//选择授课方式按钮
    private TextView mTeacherType;//老师类别
    private RelativeLayout mTeacherTypeChoose;//选择老师类别按钮
    private TextView mStudentBase;//学生基础
    private RelativeLayout mStudentBaseChoose;//选择学生基础按钮
    private TextView mStudentTime;//学生可以上课的时间
    private RelativeLayout mStudentTimeChoose;//选择学生上课的时间按钮
    private String[] mChooseContent;//保存用户选择的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_reward_student);
        initView();
        mChooseContent = new String[8];
    }

    private void initView() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mCancel.setOnClickListener(this);
        mPublish = (TextView) findViewById(R.id.tv_publish);
        mPublish.setOnClickListener(this);
        //悬赏标题
        mRewardTitle = (EditText) findViewById(R.id.et_title);
        //悬赏标签
        mRewardTag = (TextView) findViewById(R.id.tv_tag);
        mRewardTagChoose = (RelativeLayout) findViewById(R.id.rl_tagItemChoose);
        mRewardTagChoose.setOnClickListener(this);
        //悬赏描述
        mRewardDescription = (EditText) findViewById(R.id.et_description);
        //悬赏花费
        mRewardCost = (EditText) findViewById(R.id.et_cost);
        //授课方式
        mCourseType = (TextView) findViewById(R.id.tv_courseType);
        mCourseStyleChoose = (RelativeLayout) findViewById(R.id.rl_courseStyleChoose);
        mCourseStyleChoose.setOnClickListener(this);
        //老师类别
        mTeacherType = (TextView) findViewById(R.id.tv_teacherType);
        mTeacherTypeChoose = (RelativeLayout) findViewById(R.id.rl_teacherTypeChoose);
        mTeacherTypeChoose.setOnClickListener(this);
        //学生基础
        mStudentBase = (TextView) findViewById(R.id.tv_StudentBase);
        mStudentBaseChoose = (RelativeLayout) findViewById(R.id.rl_studentBaseChoose);
        mStudentBaseChoose.setOnClickListener(this);
        //学生可以上课的时间
        mStudentTime = (TextView) findViewById(R.id.tv_studentTime);
        mStudentTimeChoose = (RelativeLayout) findViewById(R.id.rl_studentTimeChoose);
        mStudentTimeChoose.setOnClickListener(this);
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
            case R.id.rl_teacherTypeChoose:
                chooseTeacherType();
                break;
            case R.id.rl_studentBaseChoose:
                chooseStudentBase();
                break;
            case R.id.rl_studentTimeChoose:
                chooseStudentTime();
                break;
            default:
                break;
        }
    }

    //点击发布
    private void publish() {
        mChooseContent[0] = mRewardTitle.getText().toString();
        mChooseContent[2] = mRewardDescription.getText().toString();
        mChooseContent[3] = mRewardCost.getText().toString();
        new PublishRewardAction().execute();
    }

    /**
     * 发布悬赏请求Action
     */
    public class PublishRewardAction extends AsyncTask<String, Integer, String> {

        public PublishRewardAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return null;
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
                        Toast.makeText(PublishRewardStudentActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PublishRewardStudentActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PublishRewardStudentActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


    //弹出悬赏选择标签
    private AlertDialog alertDialogChooseTag;
    public void chooseTag(){
        final String[] xuanshangTag = {"Java","C", "Javascript","Python","PHP","Html5","Android","iOS","数据库","其它"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                xuanshangTag);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRewardTag.setText("标签："+xuanshangTag[which]);
                        mChooseContent[1] = xuanshangTag[which];
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
                        mChooseContent[4] = courseType[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseCourseType.show();
    }


    //弹出老师类别选择标签
    private AlertDialog alertDialogChooseTeacherType;
    public void chooseTeacherType(){
        final String[] teacherType = {"已认证老师","未认证老师","不限"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择老师类别");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                teacherType);

        alertDialogChooseTeacherType = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeacherType.setText("老师类别："+teacherType[which]);
                        mChooseContent[5] = teacherType[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTeacherType.show();
    }

    //弹出选择学生基础
    private AlertDialog alertDialogChooseStudentBase;
    private void chooseStudentBase() {
        final String[] studentBase = {"小白","入门","熟练"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择学生基础");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                studentBase);

        alertDialogChooseStudentBase = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStudentBase.setText("学生基础："+studentBase[which]);
                        mChooseContent[6] = studentBase[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseStudentBase.show();
    }

    //弹出选择学生可以上课的时间
    private AlertDialog alertDialogChooseStudentTime;
    private void chooseStudentTime() {
        final String[] studentTime = {"工作日","节假日","不限"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择您可以上课的时间");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                studentTime);

        alertDialogChooseStudentTime = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStudentTime.setText("您可以上课的时间："+studentTime[which]);
                        mChooseContent[7] = studentTime[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseStudentTime.show();
    }

}
