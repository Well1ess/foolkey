package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.controller.course.course.PublishController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师填写课程上课时间、课程总时长、上课方式
 */

public class PublishCourseOptionsTeacherActivity extends AbstractActivity implements View.OnClickListener {

    private ImageView mReturn;
    private TextView mGo;

    private CheckBox mWorkday;//工作日
    private CheckBox mHoliday;//节假日
    private CheckBox mEveryday;//节假日
    private Button mDecreaseTime;//减少时长
    private Button mAddTime;//增加时长
    private EditText mAmountTime;//输入时长
    private CheckBox mOnLine;//线上
    private CheckBox mOffLine;//线下
    private CheckBox mOnAndOff;//不限

    private String[] courseChooseContent;//保存用户填写的信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_course);
        initView();
        courseChooseContent = GlobalUtil.getInstance().getCourseChooseContent();
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
        mDecreaseTime = (Button) findViewById(R.id.btnDecrease);
        mDecreaseTime.setOnClickListener(this);
        mAddTime = (Button) findViewById(R.id.btnIncrease);
        mAddTime.setOnClickListener(this);
        mAmountTime = (EditText) findViewById(R.id.et_Amount);
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
            case R.id.btnDecrease:
                timeDecrease();
                break;
            case R.id.btnIncrease:
                timeIncrease();
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
            default:
                break;
        }
    }

    private void goNext() {
        //提交用户的信息
        courseChooseContent[5] = mAmountTime.getText().toString();
        GlobalUtil.getInstance().setCourseChooseContent(courseChooseContent);
        Log.i("malei", courseChooseContent.toString());
        //发布到服务器
        new PublishCourseAction().execute();
    }


    //选择了工作日
    private void chooseWeekday() {
        courseChooseContent[4] = mWorkday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mHoliday.setChecked(false);
        mEveryday.setChecked(false);
    }

    //选择了休息日
    private void chooseHoliday() {
        courseChooseContent[4] = mHoliday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWorkday.setChecked(false);
        mEveryday.setChecked(false);
    }

    //选择了不限
    private void chooseEveryday() {
        courseChooseContent[4] = mEveryday.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mWorkday.setChecked(false);
        mHoliday.setChecked(false);
    }

    //增加时长
    private void timeIncrease() {
        int i = Integer.parseInt(mAmountTime.getText().toString());
        i++;
        mAmountTime.setText(i+"");
    }
    //减少时长
    private void timeDecrease() {
        int i = Integer.parseInt(mAmountTime.getText().toString());
        i--;
        if(i<0)
        {
            i=0;
        }
        mAmountTime.setText(i+"");
    }



    //选择线上
    private void chooseOnLine() {
        courseChooseContent[6] = mOnLine.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffLine.setChecked(false);
        mOnAndOff.setChecked(false);
    }
    //选择线下
    private void chooseOnAndOff() {
        courseChooseContent[6] = mOnAndOff.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffLine.setChecked(false);
        mOnLine.setChecked(false);
    }
    //选择线下或线上
    private void chooseOffLine() {
        courseChooseContent[6] = mOffLine.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOnLine.setChecked(false);
        mOnAndOff.setChecked(false);
    }



    /**
     * 发布课程Action，发布课程
     */
    public class PublishCourseAction extends AsyncTask<String, Integer, String> {

        public PublishCourseAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {


            String topic = courseChooseContent[0];
            String technicTagEnum = courseChooseContent[1];
            String description = courseChooseContent[2];
            String price = courseChooseContent[3];
            //默认选择
            if ( courseChooseContent[4] != null && !courseChooseContent[4].equals(""))
                ;
            else
                courseChooseContent[4] = "不限";
            String courseTimeDayEnum = courseChooseContent[4];
            String duration = courseChooseContent[5];
            //默认选择
            if ( courseChooseContent[6] != null && !courseChooseContent[6].equals(""))
                ;
            else
                courseChooseContent[6] = "不限";
            String teachMethodEnum = courseChooseContent[6];

            return PublishController.execute(
                    topic,
                    technicTagEnum,
                    description,
                    price,
                    courseTimeDayEnum,
                    duration,
                    teachMethodEnum
            );

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    if (resultFlag.equals("success")) {
                        Toast.makeText(PublishCourseOptionsTeacherActivity.this, "发布课程成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PublishCourseOptionsTeacherActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PublishCourseOptionsTeacherActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
