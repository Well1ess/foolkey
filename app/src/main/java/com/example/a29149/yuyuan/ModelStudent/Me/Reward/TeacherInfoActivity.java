package com.example.a29149.yuyuan.ModelStudent.Me.Reward;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;
import com.example.a29149.yuyuan.AbstractObject.YYBaseAction;
import com.example.a29149.yuyuan.DTO.ApplicationRewardWithTeacherSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardAsStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.ApplicationStudentRewardDTO;
import com.example.a29149.yuyuan.DTO.CourseAbstract;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherAllInfoDTO;
import com.example.a29149.yuyuan.Enum.RoleEnum;
import com.example.a29149.yuyuan.Enum.SexTagEnum;
import com.example.a29149.yuyuan.ModelStudent.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.AdapterManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.action.AcceptRewardApplicationAction;
import com.example.a29149.yuyuan.action.RejectRewardApplicationAction;
import com.example.a29149.yuyuan.business_object.PriceCaculator;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.resource.util.image.GlideCircleTransform;

/**
 * Created by MaLei on 2017/5/11.
 * Email:ml1995@mail.ustc.edu.cn
 * 申请悬赏的老师主页
 *
 * Author:       geyao
 * Date:         2017/6/15
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  进行了修改，现在要在intent中放置很多信息 teacherAllInfoDTO applicationStudentRewardDTO courseDTO
 *
 */

public class TeacherInfoActivity extends AbstractAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TeacherInfoActivity";
    private TextView mDescription;//老师简介
    private TextView mTitle;//标题
    private TextView mInfo;//老师的slogan
    private TextView mTeacherSex;//老师的性别
    private TextView mTeacherOrganization;//老师所属组织
    private TextView mTeacherEducation;//老师学历
    private TextView mFollowNum;//关注老师人数
    private TextView mCourseNum;//老师授课次数
    private TextView mTeacheringTime;//老师授课时长
    private CheckBox mTeacherState;//老师认证状态
    private TextView mTeacherVerifyState; // "已认证字样"
    private TextView mTeacherScore;//老师评价分数
    private TextView mTeacherGithub;//老师Github账户url
    private TextView mTeacherIndex;//老师主页
    private TextView mTeacherEmail;//老师邮箱
    private TextView mPrestige; // 老师的声望

    //从intent中获取
    private TeacherAllInfoDTO mTeacherAllInfoDTO;//老师信息
    private StudentDTO mStudentDTO;//学生信息
    private ApplicationStudentRewardDTO applicationStudentRewardDTO;//申请信息
    //这是adapter传过来要用的,单个老师的信息
    private ApplicationRewardWithTeacherSTCDTO applicationRewardWithTeacherSTCDTO;
    //这是整个悬赏下面的DTO
    private ApplicationStudentRewardAsStudentSTCDTO applicationStudentRewardAsStudentSTCDTO;

    private CourseAbstract mRewardDTO;//悬赏信息DTO
    private RadioButton mRewardAgree;//同意该老师申请
    private RadioButton mRewardDisagree;//不同意该老师申请
    private ImageButton mReturn;//返回


    //显示选项的对话框
    //余额不足
    private WarningDisplayDialog.Builder displayInfo;
    //同意确认
    private WarningDisplayDialog.Builder displayInfo1;
    //拒绝确认
    private WarningDisplayDialog.Builder rejectDisplayInfo;


    private ImageView mTeacherPhoto;

    //拒绝申请的异步任务
    private RejectRewardApplicationAction rejectRewardApplicationAction;
    //接受申请的异步任务
    private AcceptRewardApplicationAction acceptRewardApplicationAction;

    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        //获取
        Intent intent = getIntent();
        //获取整个悬赏下所有的信息，这一步的获取是为了成功后删除某个悬赏，使用的时候，记得判空
        applicationStudentRewardAsStudentSTCDTO = (ApplicationStudentRewardAsStudentSTCDTO) intent.getSerializableExtra("parentDTO");
        //获取悬赏信息
        mRewardDTO = (CourseAbstract) intent.getSerializableExtra("courseDTO");
        //如果这个activity是由adapter传过来，则需要获取adapter的item的数据
        applicationRewardWithTeacherSTCDTO =
                (ApplicationRewardWithTeacherSTCDTO) intent.getSerializableExtra("DTO");
        //获取教师所有的信息
//        mTeacherAllInfoDTO = (TeacherAllInfoDTO) intent.getSerializableExtra("teacherAllInfoDTO");
        mTeacherAllInfoDTO = applicationRewardWithTeacherSTCDTO.getTeacherAllInfoDTO();
        //获取一些申请的信息
//        applicationStudentRewardDTO = (ApplicationStudentRewardDTO) intent.getSerializableExtra("applicationStudentRewardDTO");
        applicationStudentRewardDTO = applicationRewardWithTeacherSTCDTO.getApplicationStudentRewardDTO();

        //获取登陆者的信息
        mStudentDTO = GlobalUtil.getInstance().getStudentDTO();

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
                Intent toRechargeActivity = new Intent(TeacherInfoActivity.this, RechargeActivity.class);
                startActivity(toRechargeActivity);
            }
        });
        displayInfo.create();

        //拒绝申请
        rejectDisplayInfo = new WarningDisplayDialog.Builder(this);
        rejectDisplayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        rejectDisplayInfo.setPositiveButton("拒绝申请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //新建一个拒绝的异步任务
                rejectRewardApplicationAction = new RejectRewardApplicationAction(applicationStudentRewardDTO.getId() + "");
                rejectRewardApplicationAction.setOnAsyncTask(new YYBaseAction.OnAsyncTask<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        //拒绝成功
                        //拒绝成功时，从该课程下面，删去老师的头像
                        //TODO 从Adapter中删除这个申请元素
                        //FIXME 这里总是出错，服务器也有问题
                        AdapterManager.getInstance().removeData(TeacherApplicationAdapter.class, applicationRewardWithTeacherSTCDTO );
                        finish();
                    }

                    @Override
                    public void onFail() {
                        //拒绝失败
                        Toast.makeText(TeacherInfoActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNull() {
                        //无网络
                        Toast.makeText(TeacherInfoActivity.this, NULL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
                //执行
                rejectRewardApplicationAction.execute();
            }
        });
        rejectDisplayInfo.create();


        //接受申请
        displayInfo1 = new WarningDisplayDialog.Builder(this);
        displayInfo1.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //点击同意接单
        displayInfo1.setPositiveButton("同      意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里做了接收申请的工作
                //FIXME 这里的价格没有经过运算
                acceptRewardApplicationAction = new AcceptRewardApplicationAction(
                        applicationStudentRewardDTO.getId() + "",
                        "",
                        PriceCaculator.getRewardPrice(mRewardDTO, null) + ""
                );
                //设定返回的动作
                acceptRewardApplicationAction.setOnAsyncTask(new YYBaseAction.OnAsyncTask<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        //同意请求
                        Toast.makeText(TeacherInfoActivity.this, SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
                        //同意申请，就会整个移除这个悬赏item的显示
                        //FIXME 并没有移除，需要在原Activity中将listView与Adapter重新绑定一次
                        if (applicationStudentRewardAsStudentSTCDTO != null){
                            AdapterManager.getInstance().removeData(RewardApplicationAdapter.class, applicationStudentRewardAsStudentSTCDTO);
                        }
                        //应该是做result处理
                        finish();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(TeacherInfoActivity.this, FAIL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNull() {
                        Toast.makeText(TeacherInfoActivity.this, NULL_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                });
                //进行网络传输
                acceptRewardApplicationAction.execute();
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
        mTeacherEducation.setText("学历");//后台服务器没提供该字段
        mFollowNum.setText(mTeacherAllInfoDTO.getFollowerNumber()+"");
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
        mPrestige.setText(mTeacherAllInfoDTO.getPrestige() + "");

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
        mFollowNum = (TextView) findViewById(R.id.tv_followNum);
        mCourseNum = (TextView) findViewById(R.id.tv_courseNum);
        mTeacheringTime = (TextView) findViewById(R.id.tv_teachingTime);
        mTeacherState = (CheckBox) findViewById(R.id.cb_teacherState);
        mTeacherVerifyState = (TextView) findViewById(R.id.tv_teacherVerifyState);
        mTeacherScore = (TextView) findViewById(R.id.tv_evaluateScore);
        mTeacherGithub = (TextView) findViewById(R.id.tv_github);
        mTeacherIndex = (TextView) findViewById(R.id.tv_teacherIndex);
        mTeacherEmail = (TextView) findViewById(R.id.tv_email);
        mTeacherPhoto = (ImageView)findViewById(R.id.iv_photo);
        mPrestige = (TextView)findViewById(R.id.tv_prestige);


        mRewardAgree = (RadioButton) findViewById(R.id.rb_main_menu_agree);
        mRewardAgree.setOnClickListener(this);
        mRewardDisagree = (RadioButton)findViewById(R.id.rb_main_menu_disagree);
        mRewardDisagree.setOnClickListener(this);
        mReturn = (ImageButton) findViewById(R.id.ib_return);
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
            case R.id.rb_main_menu_disagree://点击拒绝按钮
                rejectApplication();
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
     *
     * Author:       geyao
     * Date:         2017/6/15
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  拒绝申请
     *
     */
    private void rejectApplication(){
        rejectDisplayInfo.setMsg("确认拒绝对方的上课申请吗？");
        rejectDisplayInfo.getDialog().show();
    }
}
