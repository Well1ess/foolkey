package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.RewardDTO;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.controller.course.reward.UpdateController;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.example.a29149.yuyuan.Enum.CourseTimeDayEnum.工作日;

/**
 * 修改个人悬赏信息
 * Created by GR on 2017/5/26.
 */

public class RewardModifyActivity extends AbstractActivity implements View.OnClickListener {
    private static final String TAG = "RewardModifyActivity";
    private RewardDTO mRewardDTO;//悬赏信息

//    private String topic; //悬赏标题
//    private String description; //悬赏描述
//    private String technicTagEnum; // 悬赏的领域
//    private String price; // 悬赏的价格
//    private String courseTimeDayEnum; // 悬赏需要的上课时间
//    private String studentBaseEnum; //学生基础
//    private String teachMethodEnum; //授课方法
//    private String teacherRequirementEnum; //对老师的要求
//    private String rewardId; // 悬赏的id

    private RewardDTO rewardDTO; // 悬赏的DTO

//    private int position; // listView里的位置

//    0-topic
//    1-technicTag
//    2-description
//    3-price
//    4-上课时间
//    5-学生基础
//    6-上课方式
//    7-老师类型
    private String[] rewardChooseContent = GlobalUtil.getInstance().getRewardChooseContent();

    //悬赏标题
    @ViewInject(R.id.et_topic)
    private EditText mTopic;
    //详情
    @ViewInject(R.id.et_description)
    private EditText mDescription;

    //悬赏标签
    @ViewInject(R.id.tv_technicTag)
    private TextView mTechnicTag;



    //金额
    @ViewInject(R.id.et_reward_price)
    private EditText mRewardPrice;

    //===上课时间====
    //工作日
    @ViewInject(R.id.cb_workday)
    private CheckBox mWorkday;
    //节假日
    @ViewInject(R.id.cb_holiday)
    private CheckBox mHoliday;
    //不限
    @ViewInject(R.id.cb_everyday)
    private CheckBox mEveryday;

    //====个人基础====
    //小白
    @ViewInject(R.id.cb_whiteman)
    private CheckBox mWhiteman;
    //入门
    @ViewInject(R.id.cb_littleman)
    private CheckBox mLittleman;
    //熟练
    @ViewInject(R.id.cb_muchman)
    private CheckBox mMuchman;

    //====教学方式====
    //线上
    @ViewInject(R.id.cb_onLine)
    private CheckBox mOnline;
    //线下
    @ViewInject(R.id.cb_offLine)
    private CheckBox mOffline;
    //都可以
    @ViewInject(R.id.cb_onAndOff)
    private CheckBox mOnAndOff;

    //====老师要求====
    //认证的
    @ViewInject(R.id.cb_onlyTeacher)
    private CheckBox mOnlyTeacher;
    //都可以
    @ViewInject(R.id.cb_everybady)
    private CheckBox mEverybody;

    //返回按钮
    @ViewInject(R.id.iv_return)
    private ImageView mReturn;

    @ViewInject(R.id.tv_go)
    private TextView mSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_modify);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        rewardDTO = ( RewardDTO ) getIntent().getSerializableExtra("DTO");


        mSubmit.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mTechnicTag.setOnClickListener(this);
        mWorkday.setOnClickListener(this);
        mHoliday.setOnClickListener(this);
        mEveryday.setOnClickListener(this);
        mWhiteman.setOnClickListener(this);
        mLittleman.setOnClickListener(this);
        mOnline.setOnClickListener(this);
        mOffline.setOnClickListener(this);
        mOnAndOff.setOnClickListener(this);
        mMuchman.setOnClickListener(this);
        mOnlyTeacher.setOnClickListener(this);
        mEverybody.setOnClickListener(this);

        initData();



    }

    private void initData() {

        //展示
        mTopic.setText(rewardDTO.getTopic());
        mDescription.setText(rewardDTO.getDescription());
        mTechnicTag.setText(rewardDTO.getTechnicTagEnum().toString() + "");
        mRewardPrice.setText(rewardDTO.getPrice() + "");

        //教学时间
//        String courseTimeEnumStr = mRewardDTO.getCourseTimeDayEnum().toString();
        switch (rewardDTO.getCourseTimeDayEnum()) {
            case 工作日                    : {
                mWorkday.setChecked(true);
                mHoliday.setChecked(false);
                mEveryday.setChecked(false);
            }
            break;
            case 节假日: {
                mWorkday.setChecked(false);
                mHoliday.setChecked(true);
                mEveryday.setChecked(false);
            }
            break;
            case 不限: {
                mWorkday.setChecked(false);
                mHoliday.setChecked(false);
                mEveryday.setChecked(true);
            }
            break;
            default:
                break;
        }
        //学生基础
//        StudentBaseEnum.
//        String studentBaseEnumStr = mRewardDTO.getStudentBaseEnum().toString();
        switch (rewardDTO.getStudentBaseEnum()) {
            case 小白: {
                mWhiteman.setChecked(true);
                mLittleman.setChecked(false);
                mMuchman.setChecked(false);
            }
            break;
            case 入门: {
                mWhiteman.setChecked(false);
                mLittleman.setChecked(true);
                mMuchman.setChecked(false);
            }
            break;
            case 熟练: {
                mWhiteman.setChecked(false);
                mLittleman.setChecked(false);
                mMuchman.setChecked(true);
            }
            break;
            default:
                break;
        }

        //教学方式
//        String teachMethodEnumStr = mRewardDTO.getTeachMethodEnum().toString();
        switch (rewardDTO.getTeachMethodEnum()) {
            case 线上: {
                mOnline.setChecked(true);
                mOffline.setChecked(false);
                mOnAndOff.setChecked(false);
            }
            break;
            case 线下: {
                mOnline.setChecked(false);
                mOffline.setChecked(true);
                mOnAndOff.setChecked(false);
            }
            break;
            case 不限: {
                mOnline.setChecked(false);
                mOffline.setChecked(false);
                mOnAndOff.setChecked(true);
            }
            break;
            default:
                break;
        }

        //老师要求
//        String teacherRequirementEnumStr = mRewardDTO.getTeacherRequirementEnum().toString();
        switch (rewardDTO.getTeacherRequirementEnum()) {
            case 认证老师: {
                mOnlyTeacher.setChecked(true);
                mEverybody.setChecked(false);
            }
            break;
            case 不限: {
                mOnlyTeacher.setChecked(false);
                mEverybody.setChecked(true);
            }
            break;
            default:
                break;
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_go:
                goNext();
                break;
            case R.id.tv_technicTag:
                chooseTag();
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
        rewardChooseContent[0] = mTopic.getText().toString();
        rewardChooseContent[1] = mTechnicTag.getText().toString();
        rewardChooseContent[2] = mDescription.getText().toString();
        rewardChooseContent[3] = mRewardPrice.getText().toString();


        //Log.i("malei",rewardChooseContent.toString());
        //发布到服务器
        ModifyRewardAction modifyRewardAction = new ModifyRewardAction();
        modifyRewardAction.execute();
    }

    //弹出悬赏选择标签
    private AlertDialog alertDialogChooseTag;

    public void chooseTag() {
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                Const.REWARD_TAG);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTechnicTag.setText("标签：" + Const.REWARD_TAG[which]);
                        rewardChooseContent[1] = Const.REWARD_TAG[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
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
        rewardChooseContent[6] = mOnline.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffline.setChecked(false);
        mOnAndOff.setChecked(false);
    }

    //选择线下
    private void chooseOnAndOff() {
        rewardChooseContent[6] = mOnAndOff.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOffline.setChecked(false);
        mOnline.setChecked(false);
    }

    //选择线下或线上
    private void chooseOffLine() {
        rewardChooseContent[6] = mOffline.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mOnline.setChecked(false);
        mOnAndOff.setChecked(false);
    }

    //选择了仅老师
    private void chooseOnlyTeacher() {
        rewardChooseContent[7] = mOnlyTeacher.getText().toString();
        //mWorkday.setTextColor(Integer.parseInt("9ccc65"));
        mEverybody.setChecked(false);
    }

    //选择了不限
    private void chooseEveryBady() {
        rewardChooseContent[7] = mEverybody.getText().toString();
        mOnlyTeacher.setChecked(false);
    }



    /**
     * 发布悬赏请求Action
     */
    public class ModifyRewardAction extends AsyncTask<String, Integer, String> {

        private UpdateController updateController = new UpdateController();

        private String[] mChooseContent;

        public ModifyRewardAction() {
            super();
        }
        /**
         * 先获取需要的信息
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mChooseContent = GlobalUtil.getInstance().getRewardChooseContent();
        }

        /**
         * 发出更新请求
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            //默认选择
            String courseTimeDayEnumStr;
            if ( mChooseContent[4] != null && !mChooseContent[4].equals(""))
                ;
            else
                mChooseContent[4] = "不限";
            courseTimeDayEnumStr = mChooseContent[4];

            String studentBaseEnumStr;
            if ( mChooseContent[5] != null && !mChooseContent[5].equals(""))
                ;
            else
                mChooseContent[5] = "小白";
            studentBaseEnumStr = mChooseContent[5];

            String teachMethodEnumStr;
            if ( mChooseContent[6] != null && !mChooseContent[6].equals(""))
                ;
            else
                mChooseContent[6] = "不限";
            teachMethodEnumStr = mChooseContent[6];

            String teachRequirementEnumStr;
            if ( mChooseContent[7] != null && !mChooseContent[7].equals(""))
                ;
            else
                mChooseContent[7] = "不限";
            teachRequirementEnumStr = mChooseContent[7];

            return
                    UpdateController.execute(
                            rewardDTO.getId() + "",
                            mChooseContent[1],
                            mChooseContent[0],
                            mChooseContent[2],
                            mChooseContent[3],
                            mChooseContent[4],
                            mChooseContent[6],
                            mChooseContent[7],
                            mChooseContent[5]
                    );
        }

        /**
         * 对更新请求的结果进行操作
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    if (resultFlag.equals("success")) {
                        Toast.makeText(RewardModifyActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        //获取更新后的悬赏信息
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<RewardDTO>() {
                        }.getType();
                        RewardDTO rewardDTO = new Gson().fromJson(jsonObject.getString("courseStudentDTO"), type);
                        //更新到缓存
                        Intent intent = new Intent();
                        //把从网络获取到的rewardDTO返回到上一个DTO
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("DTO", rewardDTO);
                        intent.putExtras(bundle);
                        //通知结果
                        setResult(RESULT_OK, intent);
                        finish();
                        return;
                        //发布成功后跳转到首页面
//                        Intent toMainActivity = new Intent(RewardModifyActivity.this, MainStudentActivity.class);
//                        startActivity(toMainActivity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RewardModifyActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RewardModifyActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }
            setResult(RESULT_CANCELED);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
