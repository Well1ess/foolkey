package com.example.a29149.yuyuan.ModelStudent.Me;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Main.ImageUploadActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Coupon.CouponActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Reward.OwnerRewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.Setting.SettingActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.info.FreshInfo;
import com.example.a29149.yuyuan.ModelStudent.Me.info.ModifyMyInfoActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Main.MainTeacherActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.business_object.com.PictureInfoBO;
import com.example.a29149.yuyuan.controller.userInfo.GetCouponController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.resource.util.image.GlideCircleTransform;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_MODIFY;
import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

public class MeMainFragment extends Fragment implements View.OnClickListener , FreshInfo{

    private static final String TAG = "MeMainFragment";

    private View view;
    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;

    private StudentDTO studentDTO;

    private TextView mTitle;//用户名
    private TextView mChangeRole;//切换用户角色
    private TextView mOwnerReward;//我的悬赏
    private TextView mOwnerCourse;//我的课程
    private  ImageView mHeadImage;//用户头像

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

    public MeMainFragment() {

    }

    public static MeMainFragment newInstance() {
        MeMainFragment fragment = new MeMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_me_main, container, false);

        studentDTO = GlobalUtil.getInstance().getStudentDTO();

        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();







        return view;
    }

    private void initView()
    {
        studentDTO = GlobalUtil.getInstance().getStudentDTO();

        mTitle = (TextView) view.findViewById(R.id.title);
        mOwnerReward = (TextView) view.findViewById(R.id.owner_reward);
        mHeadImage = (ImageView) view.findViewById(R.id.head);


        int virtualMoney = DoubleParseInt(studentDTO.getVirtualCurrency()) ;
        setVirtualMoney(virtualMoney + "");
        setPrestige( studentDTO.getPrestige() + "" );
        setTechnicTag(studentDTO.getTechnicTagEnum());
        set2stNickedName( studentDTO.getNickedName() );
        setUserSlogan(studentDTO.getSlogan());
        setHeadImage( studentDTO.getUserName() );
        setEmail( studentDTO.getEmail() );
        setGithub( studentDTO.getGithubUrl() );

        mOwnerReward.setOnClickListener(this);
        mHeadImage.setOnClickListener(this);




        displayInfo = new WarningDisplayDialog.Builder(getContext());
        displayInfo.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        displayInfo.setPositiveButton("确      定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //点击确定后跳发送申请认证
                new ApplyAuthenticationTeacherAction().execute();
                Intent intent = new Intent(getContext(), MainTeacherActivity.class);
                startActivity(intent);
            }
        });
        displayInfo.create();

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

    /**
     * 充值
     * @param view
     */
    @OnClick(R.id.recharge)
    public void setRechargeListener(View view)
    {
        Intent intent = new Intent(getActivity(), RechargeActivity.class);
        getActivity().startActivityForResult(intent, FROM_ME_FRAGMENT_TO_RECHARGE);
//        startActivityForResult(new Intent(getActivity(), RechargeActivity.class), FROM_ME_FRAGMENT_TO_RECHARGE);
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
        getActivity().startActivityForResult(intent4, FROM_ME_FRAGMENT_TO_MODIFY);
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

    /**
     * 这并不能起作用，还是要在activity里用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Deprecated
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FROM_ME_FRAGMENT_TO_RECHARGE:
                initView();
                break;
        }
    }


    /**
     * 设置余额
     * 用于充值后更新
     * @param money
     */
    public void setVirtualMoney(String money){
        mVirtualMoney.setText(money + "");
    }

    /**
     * 设置slogan
     * @param title
     */
    public void setTitle(String title) {
        if (title == null || title.equals(""))
            title = "神秘人";
        this.mTitle.setText( title );
    }


    /**
     * 设置头像
     * @param userName
     */
    public void setHeadImage(String userName) {
        glide = Glide.with(this);
        glide.load(PictureInfoBO.getOnlinePhoto( userName+"" ) )
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransform(getActivity()))
                .into(mHeadImage);
    }


    /**
     * 设置slogan
     * @param slogan
     */
    public void setUserSlogan(String slogan) {
        if (slogan == null || slogan.equals(""))
            slogan = "懒汉，什么都没说";
        this.mUserSlogan.setText( slogan );
    }

    /**
     * slogan下面的名称，也是昵称
     * @param nickedName
     */
    public void set2stNickedName(String nickedName) {
        if (nickedName == null || nickedName.equals(""))
            nickedName = "神秘人";
        this.mUserName.setText( "—— " + nickedName);
    }

    /**
     * 设置声望
     * @param prestige
     */
    public void setPrestige(String prestige) {
        reputation.setText( prestige );
        //根据声望的长度，来经行位置的调整
        reputation.setPadding(0,0,
                - (prestige.length() - 1 ) * 5 + 20
                ,0);
        reputation.setTextSize( 125/(4 + prestige.length()) );
    }

    /**
     * 设置邮箱
     * @param email
     */
    public void setEmail(String email) {
        if (email == null || "".equals(email))
            email = "电子邮箱";
        this.email.setText(email + "");
    }

    /**
     * 设置github账号
     * @param github
     */
    public void setGithub(String github) {
        if (github == null || "".equals(github))
            github = "github地址";
        this.github.setText(github + "");
    }

    /**
     * 设置技术标签
     * @param technicTag
     */
    public void setTechnicTag(TechnicTagEnum technicTag) {
        if ( technicTag == null)
            technicTag = TechnicTagEnum.其他;
            this.technicTag.setText(technicTag + "");
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
//                        Toast.makeText(getActivity(), "JSON解析异常！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "返回结果异常！", Toast.LENGTH_SHORT).show();
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
                        Log.i("geyao  ", "认证后存储老师DTO了嘛？ " + this.getClass());
                    }


                    if (resultFlag.equals("success")) {
                        Toast.makeText(getContext(), "认证成功！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), "返回结果为fail！", Toast.LENGTH_SHORT).show();
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


    public FreshInfo freshInfo;

    public void setFreshInfo(FreshInfo freshInfo){
        this.freshInfo = freshInfo;
    }

    @Override
    public void fresh() {

    }
}
