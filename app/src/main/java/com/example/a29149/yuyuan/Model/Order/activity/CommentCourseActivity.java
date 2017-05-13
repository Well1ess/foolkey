package com.example.a29149.yuyuan.Model.Order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
 * Created by geyao on 2017/5/13.
 * 评价课程订单
 */

public class CommentCourseActivity extends Activity implements View.OnClickListener {

    private TextView mPublish;//发布评价
    private EditText mCourseScore;//课程分数
    private EditText mCourseContent;//课程内容评价
    private EditText mTeacherScore;//课程分数
    private String scoreCourse;//保存评价课程分数
    private String commentContent;//保存评价课程内容
    private String scoreTeacher;//保存评价老师分数
    private int position;//记录评论位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_course);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        Log.i("malei",position+"");
        initView();
    }

    private void initView() {
        mCourseScore = (EditText) findViewById(R.id.ed_score_course);
        mCourseContent = (EditText) findViewById(R.id.ed_comment_content);
        mTeacherScore = (EditText) findViewById(R.id.ed_score_teacher);
        mPublish = (TextView) findViewById(R.id.tv_publish);
        mPublish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.tv_publish:
                publishCommentReward();
                break;
            default:
                break;
        }

    }

    private void publishCommentReward() {
        if (TextUtils.isEmpty(mCourseScore.getText().toString()) || TextUtils.isEmpty(mCourseContent.getText().toString())
        || TextUtils.isEmpty(mTeacherScore.getText().toString()))
            //非空判断
            Toast.makeText(this, "请输入评价内容", Toast.LENGTH_SHORT).show();
        else
        {
            scoreCourse = mCourseScore.getText().toString();
            commentContent = mCourseContent.getText().toString();
            scoreTeacher = mTeacherScore.getText().toString();
            new CommentCourseAction().execute();
            new CommentRewardAction().execute();
        }

    }

    /**
     * 提交评论课程订单Action
     */
    public class CommentCourseAction extends AsyncTask<String, Integer, String> {


        public CommentCourseAction() {
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
                target.put("orderId",GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId());
                target.put("scoreCourse",scoreCourse);
                target.put("content",commentContent);
                target.put("pic1Path","");
                target.put("pic2Path","");
                target.put("pic3Path","");
                target.put("pic4Path","");


                java.net.URL url = new java.net.URL(URL.getCommentCourseURL(target.toString()));
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
                        Toast.makeText(CommentCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 提交评论悬赏订单Action
     */
    public class CommentRewardAction extends AsyncTask<String, Integer, String> {


        public CommentRewardAction() {
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
                target.put("orderId",GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId());
                target.put("score",scoreTeacher);
                target.put("teacherId",GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getTeacherDTO().getId());


                java.net.URL url = new java.net.URL(URL.getCommentTeacherURL(target.toString()));
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
                        Toast.makeText(CommentCourseActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentCourseActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
