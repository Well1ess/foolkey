package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardDTO;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.reward.AcceptController;
import com.example.a29149.yuyuan.controller.course.reward.RefuseController;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;
import com.example.resource.util.image.GlideCircleTransform;

import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/11.
 * Email:ml1995@mail.ustc.edu.cn
 * 申请悬赏的老师主页
 */

public class TeacherIndexActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    private TextView mDescription;//老师简介
    private TextView mTitle;//标题
    private TextView mInfo;//老师的slogan
    private TextView mTeacherSex;//老师的性别
    private TextView mTeacherOrganization;//老师所属组织
    private TextView mTeacherEducation;//老师学历
    private TextView mFlollowNum;//关注老师人数
    private TextView mCourseNum;//老师授课次数
    private TextView mTeacheringTime;//老师授课时长
    private CheckBox mTeacherState;//老师认证状态
    private TextView mTeacherVerifyState; // "已认证字样"
    private TextView mTeacherScore;//老师评价分数
    private TextView mTeacherGithub;//老师Github账户url
    private TextView mTeacherIndex;//老师主页
    private TextView mTeacherEmail;//老师邮箱

    private TeacherAllInfoDTO mTeacherAllInfoDTO;//老师信息
    private StudentDTO mStudentDTO;//学生信息
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;//申请信息
    private RewardDTO mRewardDTO;//悬赏信息DTO
    private RadioButton mRewardAgree;//同意该老师申请
    private RadioButton mRewardDisagree;//不同意该老师申请
    private ImageButton mReturn;//返回


    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;
    private WarningDisplayDialog.Builder displayInfo1;


    private ImageView mTeacherPhoto;

    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        Intent intent = getIntent();
        String positionIn = intent.getStringExtra("positionIn");//gridview的position
        Log.i("malei",positionIn+"");
        Integer posiIn = Integer.parseInt(positionIn);
        String positionOut = intent.getStringExtra("positionOut");//listview的position
        Log.i("malei",positionOut+"");
        Integer posiOut = Integer.parseInt(positionOut);
        mTeacherAllInfoDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut)
                .getApplicationRewardWithTeacherSTCDTOS().get(posiIn).getTeacherAllInfoDTO();

        applicationStudentRewardDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut)
                .getApplicationRewardWithTeacherSTCDTOS().get(posiIn).getApplicationStudentRewardDTO();

        mRewardDTO = GlobalUtil.getInstance().getApplicationStudentRewardAsStudentSTCDTOs().get(posiOut).getRewardDTO();
        mStudentDTO = GlobalUtil.getInstance().getStudentDTO();
        Log.i("malei",mTeacherAllInfoDTO.toString());
        //余额不足提示
        displayInfo = new WarningDisplayDialog.Builder(this);
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("充      值", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent toRechargeActivity = new Intent(TeacherIndexActivity.this, RechargeActivity.class);
                startActivity(toRechargeActivity);
                //这里其实不该finish，目前的问题是充值完了，也没有刷新，fake
                finish();
            }
        });
        displayInfo.create();
        //显示确认接单
        displayInfo1 = new WarningDisplayDialog.Builder(this);
        displayInfo1.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo1.setPositiveButton("同      意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AgreeApplyRewardAction().execute();
                dialog.dismiss();
            }
        });
        displayInfo1.create();

        initView();
        initData();
    }

    private void initData() {
        String description = mTeacherAllInfoDTO.getDescription()+"";
        mDescription.setText(description);
        mTitle.setText(mTeacherAllInfoDTO.getNickedName()+"的主页");
        mInfo.setText(mTeacherAllInfoDTO.getSlogan());
        if (mTeacherAllInfoDTO.getSexTagEnum().compareTo(SexTagEnum.男)==0)
            mTeacherSex.setText("男");
        else
            mTeacherSex.setText("女");
        mTeacherOrganization.setText(mTeacherAllInfoDTO.getOrganization());
        mTeacherEducation.setText("硕士");//后台服务器没提供该字段
        mFlollowNum.setText(mTeacherAllInfoDTO.getFollowerNumber()+"");
        mCourseNum.setText(mTeacherAllInfoDTO.getTeachingNumber()+"");
        mTeacheringTime.setText(mTeacherAllInfoDTO.getTeachingTime()+"");
        if (mTeacherAllInfoDTO.getRoleEnum().compareTo(RoleEnum.teacher)==0)
            mTeacherState.setChecked(true);
        else {
            mTeacherState.setChecked(false);
            mTeacherVerifyState.setText("未认证");
        }
        mTeacherScore.setText(mTeacherAllInfoDTO.getTeacherAverageScore()+"");
        mTeacherGithub.setText(mTeacherAllInfoDTO.getGithubUrl());
        mTeacherIndex.setText(mTeacherAllInfoDTO.getBlogUrl());
        mTeacherEmail.setText(mTeacherAllInfoDTO.getEmail());

        //填充头像
        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto( mTeacherAllInfoDTO.getUserName() ))
                .error(R.drawable.photo_placeholder1)
                .transform(new GlideCircleTransform( this ))
                .into( mTeacherPhoto );
    }

    private void initView() {
        mDescription = (TextView) findViewById(R.id.tv_description);
        mTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        mInfo = (TextView) findViewById(R.id.tv_slogan);
        mTeacherSex = (TextView) findViewById(R.id.tv_teacherSex);
        mTeacherOrganization = (TextView) findViewById(R.id.tv_teacherOriganization);
        mTeacherEducation = (TextView) findViewById(R.id.tv_teacherEducation);
        mFlollowNum = (TextView) findViewById(R.id.tv_followNum);
        mCourseNum = (TextView) findViewById(R.id.tv_courseNum);
        mTeacheringTime = (TextView) findViewById(R.id.tv_teachingTime);
        mTeacherState = (CheckBox) findViewById(R.id.cb_teacherState);
        mTeacherVerifyState = (TextView) findViewById(R.id.tv_teacherVerifyState);
        mTeacherScore = (TextView) findViewById(R.id.tv_evaluateScore);
        mTeacherGithub = (TextView) findViewById(R.id.tv_github);
        mTeacherIndex = (TextView) findViewById(R.id.tv_teacherIndex);
        mTeacherEmail = (TextView) findViewById(R.id.tv_email);
        mTeacherPhoto = (ImageView)findViewById(R.id.iv_photo);



        mRewardAgree = (RadioButton) findViewById(R.id.rb_main_menu_agree);
        mRewardAgree.setOnClickListener(this);
        mRewardDisagree = (RadioButton)findViewById(R.id.rb_main_menu_disagree);
        mRewardDisagree.setOnClickListener(this);
        mReturn = (ImageButton) findViewById(R.id.tv_return);
        mReturn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.rb_main_menu_agree:
                agreeApplyReward();
                break;
            case R.id.rb_main_menu_disagree:
                new DisagreeApplyRewardAction().execute();
                break;
            case R.id.ib_return:
                this.finish();
                break;
            default:
                break;
        }
    }

    //学生点击同意
    private void agreeApplyReward() {
        if(mStudentDTO.getVirtualCurrency() < mRewardDTO.getPrice()) {
            displayInfo.setMsg("您余额不足？\n \n 请及时充值");

            displayInfo.getDialog().show();
        }else{
            displayInfo1.setMsg("是 否 确 认？\n \n ");
            displayInfo1.getDialog().show();

        }

    }


    /**
     * 拒绝申请悬赏请求Action
     */
    public class DisagreeApplyRewardAction extends AsyncTask<String, Integer, String> {


        public DisagreeApplyRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return RefuseController.execute(applicationStudentRewardDTO.getId() + "");

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
                        Toast.makeText(TeacherIndexActivity.this, "拒绝申请成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TeacherIndexActivity.this,OwnerRewardActivity.class);
                        TeacherIndexActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(TeacherIndexActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TeacherIndexActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 同意申请悬赏请求Action
     */
    public class AgreeApplyRewardAction extends AsyncTask<String, Integer, String> {


        public AgreeApplyRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println();
            System.out.println(this.getClass() + "这里的优惠券Id依然是写死的！\n");
            return AcceptController.execute(
                    applicationStudentRewardDTO.getId()+"",
                    "", // 这里原本应该是优惠券的Id
                    mRewardDTO.getPrice() + ""
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
                        Toast.makeText(TeacherIndexActivity.this, "同意申请悬赏成功！", Toast.LENGTH_SHORT).show();
                        mStudentDTO.setVirtualCurrency( mStudentDTO.getVirtualCurrency() - mRewardDTO.getPrice() );
                        Intent intent = new Intent(TeacherIndexActivity.this,MainStudentActivity.class);
                        TeacherIndexActivity.this.startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(TeacherIndexActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TeacherIndexActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
