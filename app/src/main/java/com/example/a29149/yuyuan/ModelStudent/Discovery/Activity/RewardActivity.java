package com.example.a29149.yuyuan.ModelStudent.Discovery.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.Main.MainTeacherActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardListAdapter;
import com.example.a29149.yuyuan.ModelStudent.Discovery.DiscoveryMainFragment;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Fragment.RewardDiscoveryFragment;
import com.example.a29149.yuyuan.ModelStudent.Me.Reward.RewardModifyActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.course.reward.ApplyController;
import com.example.a29149.yuyuan.controller.course.reward.DeleteController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;
import com.example.resource.util.image.GlideCircleTransform;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 悬赏详情
 */

public class RewardActivity extends AbstractAppCompatActivity implements View.OnClickListener {
    //日志的标志
    private static final String TAG = "RewardActivity";


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
    private TextView mOperateTime; //最后修改时间
    @ViewInject(R.id.iv_photo)
    private ImageView photo;


    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);   //绑定UI
        //获取意图
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //获取位置
        position = extras.getInt("position");
        Log.i("malei", position + "");
        if (position != -1) {
            //获取信息
            studentDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getStudentDTO();
            rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();

        } else {
            studentDTO = new StudentDTO();
            rewardDTO = new RewardDTO();
        }

        //注解式绑定
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
        //初始化数据与试图
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
        //删除按钮
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
//    @OnClick({R.id.rb_want_learn, R.id.rb_chat, R.id.tv_applyOrder})
//    public void setMenuListener(View view) {
//        switch (view.getId()) {
//            case R.id.rb_want_learn:
//                //TODO:网络传输
//                break;
//            case R.id.rb_chat:
//                //TODO:网络传输
//                break;
//        }
//    }

    private void initView() {
        mOrder = (RadioButton) findViewById(R.id.tv_applyOrder);
        mOrder.setOnClickListener(this);
        mReturn = (ImageButton) findViewById(R.id.ib_return);
        mReturn.setOnClickListener(this);
        //绑定UI
        mNickedName = (TextView) findViewById(R.id.tv_nickedName);
        mRewardPrice = (TextView) findViewById(R.id.tv_price);
        mRewardTopic = (TextView) findViewById(R.id.tv_title);
        mRewardDescription = (TextView) findViewById(R.id.tv_description);
        mCreateTime = (TextView) findViewById(R.id.tv_createTime);
        mButtonMiddle = (RadioButton) findViewById(R.id.rb_chat);
        mButtonLeft = (RadioButton) findViewById(R.id.rb_want_learn);
        mPrestige = (TextView) findViewById(R.id.tv_prestige);
        mOperateTime = (TextView) findViewById(R.id.tv_createTime);
        mButtonLeft.setOnClickListener(this);
        //判断这个悬赏是不是自己的
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
        //加载图片
        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto(studentDTO.getUserName()))
                .transform(new GlideCircleTransform(this))
                .placeholder(R.drawable.photo_placeholder1)
                .into(photo);
    }


    /**
     * 设置数据
     */
    private void initData() {
        mNickedName.setText(studentDTO.getNickedName());
        mRewardPrice.setText(rewardDTO.getPrice() + "");
        mRewardTopic.setText(rewardDTO.getTopic() + "");
        mRewardDescription.setText(rewardDTO.getDescription());
        mPrestige.setText( studentDTO.getPrestige() + "");
        mOperateTime.setText( "悬赏居然没有日期，真是醉了" );
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

    /**
     * 添加点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按键
            case R.id.ib_return:
                this.finish();
                break;
            //右边按钮
            //包含接单和悬赏2种操作
            case R.id.tv_applyOrder:{
                if (mOrder.getText().equals("修改悬赏")) {  //自己的悬赏，就是修改悬赏资料
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
                    startActivityForResult(modifyIntent, Const.FROM_REWARD_TO_MODIFY);


                } else {    //他人的悬赏，接单
                    displayInfo.setMsg("您确定要此单吗？\n \n 点击 接单 将发送申请");
                    //接单的逻辑，写在了对话框的逻辑里
                    displayInfo.getDialog().show();
                }
            }break;
            //左边按钮
            //包含删除、"我也想学"2种逻辑
            case R.id.rb_want_learn:{
                if (mButtonLeft.getText() != null && mButtonLeft.getText().equals("删除悬赏")){
                    //删除悬赏
                    if ( rewardDTO.getRewardStateEnum().equals(RewardStateEnum.待接单) ){
                        deleteVerifyConfirm.setMsg("点击删除，将不可恢复，确定吗？");
                        deleteVerifyConfirm.getDialog().show();
                    }else {
                        //已经被接单的悬赏不能被删除，这里的已接单，是指用户已经同意老师申请、并付款了
                        Toast.makeText(this, "已接单的悬赏不可被删除哦", Toast.LENGTH_SHORT);
                    }
                }else {
                    //非自己的悬赏，则可以点击我也想学，发布一个新的
                    //TODO
                }
            }break;
            //中间的按钮
            //聊天
            case R.id.rb_chat:{
                //TODO
            }
            default:
                break;
        }
    }

    /**
     * 根据操作，获取不同的结果，采取不同的行为
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 意图带了额外数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){//成功了
            //根据不同的请求做不同的处理
            switch (requestCode){
                //修改悬赏成功后
                case Const.FROM_REWARD_TO_MODIFY:{
                    //更新本页数据
                    updateDate();
                    //更新发现的listView里的数据
                    //搜索的ListView不会在这里更新
                    updateRewardList();
                }break;
                default:break;
            }
        }else { //操作失败

        }
    }

    /**
     * 刷新fragment里界面显示list
     * 增删 reward 的时候就可能用到
     */
    public static void updateRewardList(){
        //首先获取2个activity
        //学生的
        MainStudentActivity mainStudentActivity =
                (MainStudentActivity) AppManager.getActivity(MainStudentActivity.class);
        //老师的
        MainTeacherActivity mainTeacherActivity =
                (MainTeacherActivity) AppManager.getActivity(MainTeacherActivity.class);

        //针对不同的activity采取不同的行为
        if (mainStudentActivity != null){
            //学生activity可更新，则获取DiscoveryMainFragment
            DiscoveryMainFragment discoveryMainFragment =
                    (DiscoveryMainFragment) mainStudentActivity.getDiscoveryMainFragment();
            //从
            RewardDiscoveryFragment fragment = discoveryMainFragment.getRewardDiscoveryFragment();
            //调用更新方法
            fragment.updateRewardList();
        }
        if (mainTeacherActivity != null){
            //老师activity可更新
            //TODO
        }
    }

    /**
     * 刷新本页面显示的信息，修改了悬赏信息以后就可能用到
     */
    private void updateDate(){
        //重新获取一次rewardDTO，不然可能不会重新获取
        rewardDTO = GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().get(position).getRewardDTO();
        //设置价格
        mRewardPrice.setText(rewardDTO.getPrice() + "");
        //设置标题
        mRewardTopic.setText(rewardDTO.getTopic() + "");
        //设置描述
        mRewardDescription.setText(rewardDTO.getDescription());
        //设置时间
        //FIXME
        mOperateTime.setText( "悬赏居然没有日期，真是醉了" );
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
            //发送申请的请求，并接收返回结果
            return ApplyToVerifyController.execute();
        }

        /**
         * 对结果进行处理
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
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
                    if (resultFlag.equals("success")) {
                        Toast.makeText(RewardActivity.this, "  成功接单  \n等待学生选择\n  要耐心哦  ", Toast.LENGTH_LONG).show();

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
                        //更新数据
                        GlobalUtil.getInstance().getRewardWithStudentSTCDTOs().remove(position);
                        //刷新界面显示
                        updateRewardList();

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
