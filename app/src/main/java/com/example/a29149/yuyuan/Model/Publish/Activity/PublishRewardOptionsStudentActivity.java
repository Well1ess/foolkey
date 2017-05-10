package com.example.a29149.yuyuan.Model.Publish.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生填写悬赏上课时间、基础、理想老师
 */

public class PublishRewardOptionsStudentActivity extends Activity implements View.OnClickListener {

    private ImageView mReturn;
    private TextView mGo;

    private CheckBox mWorkday;//工作日
    private CheckBox mHoliday;//节假日
    private CheckBox mEveryday;//节假日

    private CheckBox mWhiteman;//小白
    private CheckBox mLittleman;//入门
    private CheckBox mMuchman;//熟练
    private CheckBox mOnlyTeacher;//仅老师
    private CheckBox mEverybaby;//不限
    private CheckBox mOnLine;//线上
    private CheckBox mOffLine;//线下
    private CheckBox mOnAndOff;//不限

    private String[] rewardChooseContent;//保存用户填写的信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_reward);
        initView();
        rewardChooseContent = GlobalUtil.getInstance().getRewardChooseContent();
    }

    private void initView() {
        mReturn = (ImageView) findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);
        mGo = (TextView) findViewById(R.id.tv_go);
        mGo.setOnClickListener(this);

        mWorkday = (CheckBox) findViewById(R.id.cb_workday);
        mWorkday.setOnClickListener(this);
        mHoliday = (CheckBox) findViewById(R.id.cb_holiday);
        mHoliday.setOnClickListener(this);
        mEveryday = (CheckBox) findViewById(R.id.cb_everyday);
        mEveryday.setOnClickListener(this);
        mWhiteman = (CheckBox) findViewById(R.id.cb_whiteman);
        mWhiteman.setOnClickListener(this);
        mLittleman = (CheckBox) findViewById(R.id.cb_littleman);
        mLittleman.setOnClickListener(this);
        mMuchman = (CheckBox) findViewById(R.id.cb_muchman);
        mMuchman.setOnClickListener(this);
        mOnlyTeacher = (CheckBox) findViewById(R.id.cb_onlyTeacher);
        mOnlyTeacher.setOnClickListener(this);
        mEverybaby = (CheckBox) findViewById(R.id.cb_everybady);
        mEverybaby.setOnClickListener(this);

        mOnLine = (CheckBox) findViewById(R.id.cb_onLine);
        mOnLine.setOnClickListener(this);
        mOffLine = (CheckBox) findViewById(R.id.cb_offLine);
        mOffLine.setOnClickListener(this);
        mOnAndOff = (CheckBox) findViewById(R.id.cb_onAndOff);
        mOnAndOff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_go:
                goNext();
                break;
            case R.id.cb_workday:
                chooseWeekday();
                break;
            case R.id.cb_holiday:
                chooseHoliday();
                break;
            case R.id.cb_everyday:
                chooseEveryday();
                break;
            case R.id.cb_whiteman:
                choosewhiteMan();
                break;
            case R.id.cb_littleman:
                chooseLittleMan();
                break;
            case R.id.cb_onLine:
                chooseOnLine();
                break;
            case R.id.cb_offLine:
                chooseOffLine();
                break;
            case R.id.cb_onAndOff:
                chooseOnAndOff();
                break;
            case R.id.cb_muchman:
                chooseMuchMan();
                break;
            case R.id.cb_onlyTeacher:
                chooseOnlyTeacher();
                break;
            case R.id.cb_everybady:
                chooseEveryBady();
                break;
            default:
                break;
        }
    }



    private void goNext() {
        //提交用户的信息
        GlobalUtil.getInstance().setRewardChooseContent(rewardChooseContent);
        Log.i("malei",rewardChooseContent.toString());
        //发布到服务器
        new PublishRewardAction().execute();
    }


    //选择了工作日
    private void chooseWeekday() {
        rewardChooseContent[4] = mWorkday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mHoliday.setChecked(false);
        mEveryday.setChecked(false);
    }

    //选择了休息日
    private void chooseHoliday() {
        rewardChooseContent[4] = mHoliday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWorkday.setChecked(false);
        mEveryday.setChecked(false);
    }

    //选择了不限
    private void chooseEveryday() {
        rewardChooseContent[4] = mEveryday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWorkday.setChecked(false);
        mHoliday.setChecked(false);
    }

    //选择了小白
    private void choosewhiteMan() {
        rewardChooseContent[5] = mWhiteman.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mLittleman.setChecked(false);
        mMuchman.setChecked(false);
    }

    //选择了入门
    private void chooseLittleMan() {
        rewardChooseContent[5] = mLittleman.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWhiteman.setChecked(false);
        mMuchman.setChecked(false);
    }

    //选择了熟练
    private void chooseMuchMan() {
        rewardChooseContent[5] = mMuchman.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWhiteman.setChecked(false);
        mLittleman.setChecked(false);
    }

    //选择线上
    private void chooseOnLine() {
        rewardChooseContent[6] = mOnLine.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffLine.setChecked(false);
        mOnAndOff.setChecked(false);
    }
    //选择线下
    private void chooseOnAndOff() {
        rewardChooseContent[6] = mOnAndOff.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffLine.setChecked(false);
        mOnLine.setChecked(false);
    }
    //选择线下或线上
    private void chooseOffLine() {
        rewardChooseContent[6] = mOffLine.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOnLine.setChecked(false);
        mOnAndOff.setChecked(false);
    }

    //选择了仅老师
    private void chooseOnlyTeacher() {
        rewardChooseContent[7] = mOnlyTeacher.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mEverybaby.setChecked(false);
    }

    //选择了不限
    private void chooseEveryBady() {
        rewardChooseContent[7] = mEverybaby.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOnlyTeacher.setChecked(false);
    }

    /**
     * 发布悬赏请求Action
     */
    public class PublishRewardAction extends AsyncTask<String, Integer, String> {

        private  String[] mChooseContent;

        public PublishRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mChooseContent = GlobalUtil.getInstance().getRewardChooseContent();
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
                target.put("courseTimeDayEnum",mChooseContent[4]);
                target.put("teachMethodEnum",mChooseContent[6]);
                target.put("teachRequirementEnum",mChooseContent[7]);
                target.put("studentBaseEnum",mChooseContent[5]);
                java.net.URL url = new java.net.URL(URL.getStudentPublishRewardURL(target.toString()));
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
                        Toast.makeText(PublishRewardOptionsStudentActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PublishRewardOptionsStudentActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PublishRewardOptionsStudentActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}