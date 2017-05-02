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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by MaLei on 2017/5/1 0014.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生发布悬赏
 */

public class PublishRewardStudentActivity extends Activity implements View.OnClickListener {

    private TextView mCancel;//取消
    private TextView mPublish;//发布
    private EditText mRewardTitle;//悬赏标题
    private TextView mRewardTag;//悬赏标签
    private EditText mRewardDescription;//悬赏描述
    private TextView mTagChoose;//悬赏标签选择按钮
    private  EditText mRewardCost;//悬赏费用
    private TextView mCourseType;//授课方式
    private TextView mCourseTypeChoose;//选择授课方式按钮
    private TextView mTeacherSex;//老师性别
    private TextView mTeacherSexChoose;//选择老师性别按钮
    private TextView mTeacherType;//老师类别
    private TextView mTeacherTypeChoose;//选择老师类别按钮
    private TextView mStudentBase;//学生基础
    private TextView mStudentBaseChoose;//选择学生基础按钮
    private String[] mChooseContent;//保存用户选择的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        mTagChoose = (TextView) findViewById(R.id.tv_tagChoose);
        mTagChoose.setOnClickListener(this);
        //悬赏描述
        mRewardDescription = (EditText) findViewById(R.id.et_description);
        //悬赏花费
        mRewardCost = (EditText) findViewById(R.id.et_cost);
        //授课方式
        mCourseType = (TextView) findViewById(R.id.tv_courseType);
        mCourseTypeChoose = (TextView) findViewById(R.id.tv_courseTypeChoose);
        mCourseTypeChoose.setOnClickListener(this);
        //老师性别
        mTeacherSex = (TextView) findViewById(R.id.tv_teacherSex);
        mTeacherSexChoose = (TextView) findViewById(R.id.tv_teacherSexChoose);
        mTeacherSexChoose.setOnClickListener(this);
        //老师类别
        mTeacherType = (TextView) findViewById(R.id.tv_teacherType);
        mTeacherTypeChoose = (TextView) findViewById(R.id.tv_teacherTypeChoose);
        mTeacherTypeChoose.setOnClickListener(this);
        //学生基础
        mStudentBase = (TextView) findViewById(R.id.tv_StudentBase);
        mStudentBaseChoose = (TextView) findViewById(R.id.tv_StudentBaseChoose);
        mStudentBaseChoose.setOnClickListener(this);
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
            case R.id.tv_tagChoose:
                chooseTag();
                break;
            case R.id.tv_courseTypeChoose:
                chooseCourseType();
                break;
            case R.id.tv_teacherSexChoose:
                chooseTeacherSex();
                break;
            case R.id.tv_teacherTypeChoose:
                chooseTeacherType();
                break;
            case R.id.tv_StudentBaseChoose:
                chooseStudentBase();
                break;
            default:
                break;
        }
    }



    private void publish() {
        mChooseContent[0] = mRewardTitle.getText().toString();
        mChooseContent[2] = mRewardDescription.getText().toString();
        mChooseContent[3] = mRewardCost.getText().toString();
        new PublishAction().execute();
    }

    /**
     * 发布请求Action
     */
    public class PublishAction extends AsyncTask<String, Integer, String> {

        public PublishAction() {
            super();
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
                target.put("technicTagEnum",mChooseContent[1]);
                target.put("topic",mChooseContent[0]);
                target.put("description",mChooseContent[2]);
                target.put("price",mChooseContent[3]);
                target.put("courseTimeDayEnum","不限");
                target.put("teachMethodEnum",mChooseContent[4]);
                target.put("sexTagEnum",mChooseContent[5]);
                target.put("teachRequirementEnum",mChooseContent[6]);
                target.put("studentBaseEnum",mChooseContent[7]);
                java.net.URL url = new java.net.URL(URL.getStudentPublishXuanshangURL(target.toString()));
                Log.i("malei",target.toString());
                con = (HttpURLConnection) url.openConnection();
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("POST");
                con.setRequestProperty("contentType", "GBK");

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

    //弹出老师性别选择标签
    private AlertDialog alertDialogChooseTeacherSex;
    public void chooseTeacherSex(){
        final String[] teacherSex = {"男","女"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                teacherSex);

        alertDialogChooseTeacherSex = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeacherSex.setText("老师性别："+teacherSex[which]);
                        mChooseContent[5] = teacherSex[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTeacherSex.show();
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
                        mChooseContent[6] = teacherType[which];
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
                        mChooseContent[7] = studentBase[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseStudentBase.show();
    }

}