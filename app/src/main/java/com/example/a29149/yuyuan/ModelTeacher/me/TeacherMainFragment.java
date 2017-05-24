package com.example.a29149.yuyuan.ModelTeacher.me;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Main.ImageUploadActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Coupon.CouponActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Reward.OwnerRewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Setting.SettingActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.info.ModifyMyInfoActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.GetCouponController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.resource.util.image.GlideCircleTransform;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

/**
 * Created by MaLei on 2017/5/3 0014.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师我的页面
 */

public class TeacherMainFragment extends Fragment implements View.OnClickListener {

    private View view;
    //显示选项的对话框
//    private WarningDisplayDialog.Builder displayInfo;

    private StudentDTO studentDTO;

    private TextView mTitle;//用户名
    private TextView mChangeRole;//切换用户角色
    private TextView mOwnerReward;//我的悬赏
    private TextView mOwnerCourse;//我的课程
    private  ImageView mHeadImage;//用户头像

    @ViewInject(R.id.cash_rmb)
    private TextView mCash;

    @ViewInject(R.id.virtual_money)
    private TextView mVirtualMoney;

    @ViewInject(R.id.slogan)
    private TextView mUserSlogan;

    @ViewInject(R.id.name)
    private TextView mUserName;

    @ViewInject(R.id.modify_info)
    private TextView mModifyInfo;

    @ViewInject(R.id.prestige)
    private TextView reputation;

    @ViewInject(R.id.email)
    private TextView email;

    @ViewInject(R.id.github)
    private TextView github;

    @ViewInject(R.id.technicTag)
    private TextView technicTag;

    private RequestManager glide;

    public TeacherMainFragment() {

    }

    public static TeacherMainFragment newInstance() {
        TeacherMainFragment fragment = new TeacherMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_me_teacher, container, false);

        studentDTO = GlobalUtil.getInstance().getStudentDTO();

        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();







        return view;
    }

    private void initView()
    {
        studentDTO = GlobalUtil.getInstance().getStudentDTO();

        int virtualMoney = DoubleParseInt(studentDTO.getVirtualCurrency());
        int realMoney = DoubleParseInt(studentDTO.getCash());
        mVirtualMoney.setText(virtualMoney+"");
        mCash.setText(realMoney + "");

        String prestige = studentDTO.getPrestige() + "";
        reputation.setText( prestige );
        //根据声望的长度，来经行位置的调整
        reputation.setPadding(0,0,
                - (prestige.length() - 1 ) * 5 + 20
                ,0);
        reputation.setTextSize( 125/(4 + prestige.length()) );

        email.setText(studentDTO.getEmail() + "");
        github.setText(studentDTO.getGithubUrl() + "");
        if (studentDTO.getTechnicTagEnum() != null)
            technicTag.setText(studentDTO.getTechnicTagEnum() + "");
        else
            ;


        mTitle = (TextView) view.findViewById(R.id.title);
        mTitle.setText(studentDTO.getNickedName() + "");
        mUserName.setText("————"+studentDTO.getNickedName());

        if (TextUtils.isEmpty(studentDTO.getSlogan()))
            mUserSlogan.setText("这个人很懒，什么都没留下！");
        else
            mUserSlogan.setText(studentDTO.getSlogan());

        mOwnerReward = (TextView) view.findViewById(R.id.owner_reward);
        mOwnerReward.setOnClickListener(this);

//        mOwnerCourse = (TextView) view.findViewById(R.id.tv_course);
//        mOwnerCourse.setOnClickListener(this);

        mHeadImage = (ImageView) view.findViewById(R.id.head);
        mHeadImage.setOnClickListener(this);

        //图片加载器
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        mHeadImage.setAnimation(alphaAnimation);
        mHeadImage.setVisibility(View.VISIBLE);
        alphaAnimation.start();

        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto( studentDTO.getUserName() ) )
                .transform(new GlideCircleTransform(getActivity()))
                .into(mHeadImage);

//        reputation.setText( studentDTO.getPrestige() );



    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.owner_reward:
                Intent intent1 = new Intent(getActivity(),OwnerRewardActivity.class);
                startActivity(intent1);
                break;
//            case R.id.tv_course:
//                Intent intent2 = new Intent(getActivity(),OwnerCourseTeacherActivity.class);//课程
//                //Intent intent1 = new Intent(getActivity(),OwnerRewardTeacherActivity.class);//悬赏
//                startActivity(intent2);
//                break;
            case R.id.head:
                Intent intent3 = new Intent(getActivity(),ImageUploadActivity.class);
                startActivity(intent3);
                break;
            case R.id.modify_info:{
                Intent intent4 = new Intent(getActivity(), ModifyMyInfoActivity.class);
                startActivity(intent4);
            }break;
            default:
                break;
        }
    }


    public int DoubleParseInt(Double d1) {
        if(d1 == null)
            return 0;
        Double d2 = d1 % 1;
        String str1 = new String((d1 - d2) + "");
        str1 = str1.split("\\.")[0];
        int i1 = Integer.parseInt(str1);
        return i1;
    }

    @OnClick(R.id.owner_reward)
    public void setCheckReward(View view)
    {
        //跳转到悬赏详情
        startActivity(new Intent(getActivity(), OwnerRewardActivity.class));
    }

    @OnClick(R.id.setting)
    public void setSettingListener(View view) {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        AppManager.getInstance().addActivity(getActivity());
        startActivity(intent);
    }

    @OnClick(R.id.recharge)
    public void setRechargeListener(View view)
    {
        startActivityForResult(new Intent(getActivity(), RechargeActivity.class), FROM_ME_FRAGMENT_TO_RECHARGE);
    }

    @OnClick(R.id.check_coupon)
    public void setCheckCouponListener(View view) {
        //TODO:网络通信
        CouponAction couponAction = new CouponAction();
        couponAction.execute();
    }

    @OnClick(R.id.modify_info)
    public void setmModifyInfo(View view){
        Intent intent4 = new Intent(getActivity(), ModifyMyInfoActivity.class);
        startActivity(intent4);
    }

   /* @OnClick(R.id.change_role)
    public void setChangeRoleListener(View view){
        //TODO:网络通信
        ChangeRole changeRole = new ChangeRole();
        changeRole.execute();

    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FROM_ME_FRAGMENT_TO_RECHARGE:
                initView();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    //获取抵扣卷
    public class CouponAction extends AsyncTask<String, Integer, String> {

        public CouponAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println();
            System.out.println(this.getClass() + "这里有个方法依然是写死的\n");
            return GetCouponController.execute("1");
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
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<CouponDTO>>() {
                        }.getType();
                        List<CouponDTO> couponDTOs = new Gson().fromJson(jsonObject.getString("couponList"), type);

                        GlobalUtil.getInstance().setCouponDTOList(couponDTOs);

                        startActivity(new Intent(getActivity(), CouponActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "JSON解析异常！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "返回结果异常！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
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
                    Log.i("malei",jsonObject.getString("teacherDTO"));
                    if(teacherDTO != null)
                    {
                        //存储老师DTO
                        GlobalUtil.getInstance().setTeacherDTO(teacherDTO);

                    }


                    if (resultFlag.equals("success")) {
                        Toast.makeText(getContext(), "认证成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "返回结果为fail！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
