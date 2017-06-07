package com.example.a29149.yuyuan.ModelStudent.Discovery.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.RewardStateEnum;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;
import com.example.a29149.yuyuan.ModelStudent.Me.Reward.RewardModifyActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.reward.ApplyController;
import com.example.a29149.yuyuan.controller.course.reward.DeleteController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.resource.util.image.GlideCircleTransform;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏详情
 */

public class RewardActivity extends AppCompatActivity implements View.OnClickListener {


    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;
    //显示认证的对话框
    private WarningDisplayDialog.Builder verifyConfirm;

    private WarningDisplayDialog.Builder deleteVerifyConfirm;
    private RadioButton mOrder;//我要接单

    private RadioButton mButtonMiddle; // 联系悬赏人
    private RadioButton mButtonLeft; // 左边的按钮

    private int position = -1;//item位置
    private StudentDTO studentDTO;//发布悬赏的学生信息
    private RewardDTO rewardDTO;//悬赏信息
    private TextView mNickedName;//发布悬赏人的信息
    private TextView mRewardPrice;//悬赏价格
    private TextView mRewardTopic;//悬赏标题
    private TextView mRewardDescription;//悬赏描述
    private TextView mCreateTime;//创建悬赏的时间
    private TextView mPrestige;
    private ImageButton mReturn;//返回按键
    @ViewInject(R.id.iv_photo)
    private ImageView photo;


    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        position = extras.getInt("position");
        Log.i("malei", position + "");
        if (position != -1) {
            studentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();
            rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();

        } else {
            studentDTO = new StudentDTO();
            rewardDTO = new RewardDTO();
        }


        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        initView();
        initData();

        //确认是否接单的按钮设置
        displayInfo = new WarningDisplayDialog.Builder(this);
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("接      单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //点击接单后发送请求
                applyRewardTeacher();
            }
        });
        displayInfo.create();

        //确认是否认证的按钮设置
        verifyConfirm = new WarningDisplayDialog.Builder(this);
        verifyConfirm.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        verifyConfirm.setPositiveButton("申      请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //进行认证
                applyVerify();
            }
        });
        verifyConfirm.create();

        deleteVerifyConfirm = new WarningDisplayDialog.Builder(this);
        deleteVerifyConfirm.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteVerifyConfirm.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //删除悬赏
                DeleteRewardAction deleteRewardAction = new DeleteRewardAction();
                deleteRewardAction.execute();
            }
        });
        deleteVerifyConfirm.create();
    }

    //为底部菜单添加监听
    @OnClick({R.id.rb_want_learn, R.id.rb_chat, R.id.tv_applyOrder})
    public void setMenuListener(View view) {
        switch (view.getId()) {
            case R.id.rb_want_learn:
                //TODO:网络传输
                break;
            case R.id.rb_chat:
                //TODO:网络传输
                break;

        }
    }

    private void initView() {
        mOrder = (RadioButton) findViewById(R.id.tv_applyOrder);
        mOrder.setOnClickListener(this);
        mReturn = (ImageButton) findViewById(R.id.tv_return);
        mReturn.setOnClickListener(this);

        mNickedName = (TextView) findViewById(R.id.tv_nickedName);
        mRewardPrice = (TextView) findViewById(R.id.tv_price);
        mRewardTopic = (TextView) findViewById(R.id.title);
        mRewardDescription = (TextView) findViewById(R.id.tv_description);
        mCreateTime = (TextView) findViewById(R.id.tv_createTime);
        mButtonMiddle = (RadioButton) findViewById(R.id.rb_chat);
        mButtonLeft = (RadioButton) findViewById(R.id.rb_want_learn);
        mPrestige = (TextView) findViewById(R.id.tv_prestige);
        mButtonLeft.setOnClickListener(this);

        if (rewardDTO.getCreatorId() == null ||
                rewardDTO.getCreatorId().equals(GlobalUtil.getInstance().getStudentDTO().getId())) {
            //这个是自己的悬赏
            if (rewardDTO.getRewardStateEnum().equals(RewardStateEnum.待接单)) {
                //可以随便删除
                mButtonLeft.setText("删除悬赏");
                mButtonMiddle.setVisibility(View.GONE);
                mOrder.setText("修改悬赏");
            } else {
                //已完成的悬赏
                mButtonLeft.setVisibility(View.GONE);
                mOrder.setVisibility(View.GONE);
                mButtonMiddle.setText("一键再次发布");
                mButtonMiddle.setVisibility(View.VISIBLE);
            }

        }

        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName()))
                .transform(new GlideCircleTransform(this))
                .placeholder(R.drawable.photo_placeholder1)
                .into(photo);
    }


    private void initData() {
        mNickedName.setText(studentDTO.getNickedName());
        mRewardPrice.setText(rewardDTO.getPrice() + "");
        mRewardTopic.setText(rewardDTO.getTopic() + "");
        mRewardDescription.setText(rewardDTO.getDescription());
        mPrestige.setText( studentDTO.getPrestige() + "");
    }

    //申请认证
    private void applyVerify() {
        ApplyAuthenticationTeacherAction applyAuthenticationTeacherAction = new ApplyAuthenticationTeacherAction();
        applyAuthenticationTeacherAction.execute();
    }

    //老师申请接单悬赏
    private void applyRewardTeacher() {
        //验证身份
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();

        if (teacherDTO != null) {
            Log.i("malei", teacherDTO.toString());
            VerifyStateEnum verifyState = teacherDTO.getVerifyState();
            Log.i("malei", verifyState.toString());
            Log.i("malei", teacherDTO.toString());
            Log.i("malei", verifyState.toString());
            //如果是已认证老师或者是认证中的老师，则直接接单
            if (verifyState.compareTo(VerifyStateEnum.processing) == 0
                    || verifyState.compareTo(VerifyStateEnum.verified) == 0) {
                new RewardActivity.ApplyRewardAction().execute();
            } else {
//            Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
                verifyConfirm.setMsg("还不是老师哦\n \n 立即申请吧 ^_^");

                verifyConfirm.getDialog().show();
            }
        } else {
            Log.i("malei", "teacherDTO是空的");
            //不是已认证老师，跳转到申请认证页面
//        Toast.makeText(this, "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            verifyConfirm.setMsg("还不是老师哦\n \n 立即申请吧 ^_^");

            verifyConfirm.getDialog().show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                this.finish();
                break;
            case R.id.tv_applyOrder:{
                if (mOrder.getText().equals("修改悬赏")) {
                    Log.i("malei", mOrder.getText() + "");
                    Intent modifyIntent = new Intent(this, RewardModifyActivity.class);
                    modifyIntent.putExtra("position", 0);
                    modifyIntent.putExtra("topic", rewardDTO.getTopic());
                    modifyIntent.putExtra("description", rewardDTO.getDescription());
                    modifyIntent.putExtra("technicTagEnum", rewardDTO.getTechnicTagEnum().toString());
                    modifyIntent.putExtra("price", rewardDTO.getPrice() + "");
                    modifyIntent.putExtra("courseTimeDayEnum", rewardDTO.getCourseTimeDayEnum().toString());
                    modifyIntent.putExtra("studentBaseEnum", rewardDTO.getStudentBaseEnum().toString());
                    modifyIntent.putExtra("teachMethodEnum", rewardDTO.getTeachMethodEnum().toString());
                    modifyIntent.putExtra("teacherRequirementEnum", rewardDTO.getTeacherRequirementEnum().toString());
                    modifyIntent.putExtra("rewardId", rewardDTO.getId() + "");
                    startActivity(modifyIntent);
//                    this.finish();

                } else {
                    displayInfo.setMsg("您确定要此单吗？\n \n 点击 接单 将发送申请");

                    displayInfo.getDialog().show();
                    break;
                }
            }
            case R.id.rb_want_learn:{
                if (mButtonLeft.getText() != null && mButtonLeft.getText().equals("删除悬赏")){
                    //删除订单
                    if (
                            rewardDTO.getRewardStateEnum().equals(RewardStateEnum.待接单)
                            ){
                        deleteVerifyConfirm.setMsg("点击删除，将不可恢复，确定吗？");
                        deleteVerifyConfirm.getDialog().show();
                    }else {
                        Toast.makeText(this, "已接单的悬赏不可被删除哦", Toast.LENGTH_SHORT);
                    }

                }else {

                }
            }
            default:
                break;
        }
    }

    /**
     * 认证老师请求Action
     */
    public class ApplyAuthenticationTeacherAction extends AsyncTask<String, Integer, String> {

        public ApplyAuthenticationTeacherAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            return ApplyToVerifyController.execute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<StudentDTO>() {
                    }.getType();
                    StudentDTO studentDTO = new Gson().fromJson(jsonObject.getString("studentDTO"), type);
                    //存储学生信息DTO
                    GlobalUtil.getInstance().setStudentDTO(studentDTO);
                    //获取老师信息DTO
                    java.lang.reflect.Type type1 = new com.google.gson.reflect.TypeToken<TeacherDTO>() {
                    }.getType();
                    TeacherDTO teacherDTO = new Gson().fromJson(jsonObject.getString("teacherDTO"), type1);
                    Log.i("malei", jsonObject.getString("teacherDTO"));
                    if (teacherDTO != null) {
                        //存储老师DTO
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);
                        Log.i("geyao  ", "认证后存储老师DTO了嘛？ " + this.getClass());
                    }


                    if (resultFlag.equals("success")) {
                        //认证成功之后，就接单
                        Toast.makeText(RewardActivity.this, "申请成功，立即为您接单", Toast.LENGTH_SHORT).show();
                        ApplyRewardAction applyRewardAction = new ApplyRewardAction();
                        applyRewardAction.execute();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 申请悬赏请求Action
     */
    public class ApplyRewardAction extends AsyncTask<String, Integer, String> {


        public ApplyRewardAction() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return ApplyController.execute(rewardDTO.getId() + "");

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");
                    Log.i("malei", result);


                    if (resultFlag.equals("success")) {
                        Toast.makeText(RewardActivity.this, "  成功接单  \n等待学生选择\n  要耐心哦  ", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(RewardActivity.this, "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RewardActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    public class DeleteRewardAction extends AsyncTask<String, String, String>{

        private DeleteController deleteController = new DeleteController();

        @Override
        protected String doInBackground(String... strings) {
            deleteController.setRewardId( rewardDTO.getId() + "" );
            return deleteController.execute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if ("success".equals(jsonObject.getString("result"))){
                        Toast.makeText(RewardActivity.this, "删除成功", Toast.LENGTH_SHORT);
                        finish();
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(RewardActivity.this, "未连接到网络，请检查", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
