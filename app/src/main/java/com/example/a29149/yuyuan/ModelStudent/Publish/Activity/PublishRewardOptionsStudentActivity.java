package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.AbstractObject.YYBaseController;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Enum.CourseTimeDayEnum;
import com.example.a29149.yuyuan.Enum.StudentBaseEnum;
import com.example.a29149.yuyuan.Enum.TeachMethodEnum;
import com.example.a29149.yuyuan.Enum.TeacherRequirementEnum;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.AdapterManager;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.action.PublishRewardAction;
import com.example.a29149.yuyuan.controller.course.reward.PublishController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.example.a29149.yuyuan.controller.course.reward.PublishRewardController;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生填写悬赏上课时间、基础、理想老师
 */

public class PublishRewardOptionsStudentActivity extends AbstractActivity implements View.OnClickListener {
    private static final String TAG = "PublishRewardOptionsStu";
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

    private String[] rewardChooseContent = new String[8] ;//保存用户填写的信息，初始化默认值
    //发布悬赏的异步任务
    private PublishRewardAction publishRewardAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_reward);

        //从上一个Activity获取信息
        rewardChooseContent[0] = getIntent().getStringExtra("tag");
        rewardChooseContent[1] = getIntent().getStringExtra("title");
        rewardChooseContent[2] = getIntent().getStringExtra("description");
        rewardChooseContent[3] = getIntent().getStringExtra("price");

        initView();

        //rewardChooseContent = new String[]{"","","","","不限","小白","不限","不限"};
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


    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  发布按钮
     */
    private void goNext() {
        //发布到服务器
        //新建RewardDTO
        RewardDTO rewardDTO = new RewardDTO();
        rewardDTO.setTopic( rewardChooseContent[1] );
        rewardDTO.setTechnicTagEnum(TechnicTagEnum.valueOf( rewardChooseContent[0] ) );
        rewardDTO.setDescription( rewardChooseContent[2] );
        rewardDTO.setPrice( Double.parseDouble( rewardChooseContent[3] ) );

        //默认选择
        String courseTimeDayEnumStr;
        if ( rewardChooseContent[4] != null && !rewardChooseContent[4].equals(""))
            ;
        else
            rewardChooseContent[4] = "不限";
        courseTimeDayEnumStr = rewardChooseContent[4];

        String studentBaseEnumStr;
        if ( rewardChooseContent[5] != null && !rewardChooseContent[5].equals(""))
            ;
        else
            rewardChooseContent[5] = "小白";
        studentBaseEnumStr = rewardChooseContent[5];

        String teachMethodEnumStr;
        if ( rewardChooseContent[6] != null && !rewardChooseContent[6].equals(""))
            ;
        else
            rewardChooseContent[6] = "不限";
        teachMethodEnumStr = rewardChooseContent[6];

        String teachRequirementEnumStr;
        if ( rewardChooseContent[7] != null && !rewardChooseContent[7].equals(""))
            ;
        else
            rewardChooseContent[7] = "不限";
        teachRequirementEnumStr = rewardChooseContent[7];

        rewardDTO.setCourseTimeDayEnum(CourseTimeDayEnum.valueOf(courseTimeDayEnumStr));
        rewardDTO.setStudentBaseEnum(StudentBaseEnum.valueOf(studentBaseEnumStr));
        rewardDTO.setTeachMethodEnum(TeachMethodEnum.valueOf(teachMethodEnumStr));
        rewardDTO.setTeacherRequirementEnum(TeacherRequirementEnum.valueOf(teachRequirementEnumStr));

        //新建异步任务，传输悬赏对象
        publishRewardAction = new PublishRewardAction(rewardDTO);
        //注册回调函数
        publishRewardAction.setOnAsyncTask(new YYBaseAction.OnAsyncTask<RewardWithStudentSTCDTO>() {
            @Override
            public void onSuccess(RewardWithStudentSTCDTO data) {
                Toast.makeText(PublishRewardOptionsStudentActivity.this, "发布成功", Toast.LENGTH_SHORT);
                AdapterManager.getInstance().addData(RewardAdapter.class, data, true);
                finish();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onNull() {

            }
        });
        publishRewardAction.execute();
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
}
