package com.example.a29149.yuyuan.ModelStudent.Order.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.course.judge.JudgeTeacherController;

import org.json.JSONObject;

/**
 * Created by geyao on 2017/5/13.
 * 评价悬赏订单
 */

public class CommentRewardActivity extends Activity implements View.OnClickListener {

    private TextView mPublish;//发布评价
    private TextView mRewardScore;//订单分数
    private String score;//保存评价分数
    private String orderId;//保存评价订单ID
    private int position;//记录评论位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_reward);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        orderId = intent.getStringExtra("orderId");
        Log.i("malei","position="+position+"    "+ "orderId="+orderId+"");
        initView();
    }

    private void initView() {
        mRewardScore = (TextView) findViewById(R.id.ed_score);
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
        if (TextUtils.isEmpty(mRewardScore.getText().toString()))
            Toast.makeText(this, "请输入评价分数", Toast.LENGTH_SHORT).show();
        else
        {
            score = mRewardScore.getText().toString();
            new CommentRewardAction(score).execute();
        }

    }

    /**
     * 提交评论悬赏订单Action
     */
    public class CommentRewardAction extends AsyncTask<String, Integer, String> {

        String score;

        public CommentRewardAction(String score) {
            super();
            this.score = score;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i("malei", GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().toString());
            return JudgeTeacherController.execute(
                    //GlobalUtil.getInstance().getOrderBuyCourseAsStudentDTOs().get(position).getOrderDTO().getId() + "",
                    orderId,
                    score
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
                        Toast.makeText(CommentRewardActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                        //跳转到主页面
                        Intent intent = new Intent(CommentRewardActivity.this, MainStudentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommentRewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CommentRewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
