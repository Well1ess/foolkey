package com.example.a29149.yuyuan.Model.Me;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.DTO.TeacherDTO;
import com.example.a29149.yuyuan.Enum.VerifyStateEnum;
import com.example.a29149.yuyuan.Model.Me.Coupon.CouponActivity;
import com.example.a29149.yuyuan.Model.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.Model.Me.Reward.OwnerRewardActivity;
import com.example.a29149.yuyuan.Model.Me.Setting.SettingActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Teacher.Index.course.OwnerCourseTeacherActivity;
import com.example.a29149.yuyuan.TeacherMain.MainTeacherActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;
import com.example.a29149.yuyuan.controller.userInfo.GetCouponController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.ApplyToVerifyController;
import com.example.a29149.yuyuan.controller.userInfo.teacher.SwitchToTeacherController;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

public class MeMainFragment extends Fragment implements View.OnClickListener {

    private View view;
    //显示选项的对话框
    private WarningDisplayDialog.Builder displayInfo;

    private TextView mTitle;//用户名
    private TextView mOwnerReward;//我的悬赏
    private TextView mOwnerCourse;//我的课程

    @ViewInject(R.id.virtual_money)
    private TextView mVirtualMoney;

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

        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();

        return view;
    }

    private void initView()
    {
        int virtualMoney = DoubleParseInt(GlobalUtil.getInstance().getStudentDTO().getVirtualCurrency());
        mVirtualMoney.setText(virtualMoney+"");

        mTitle = (TextView) view.findViewById(R.id.title);
        mTitle.setText(GlobalUtil.getInstance().getStudentDTO().getNickedName());

        mOwnerReward = (TextView) view.findViewById(R.id.owner_reward);
        mOwnerReward.setOnClickListener(this);

        mOwnerCourse = (TextView) view.findViewById(R.id.tv_course);
        mOwnerCourse.setOnClickListener(this);

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
            case R.id.tv_course:
                Intent intent2 = new Intent(getActivity(),OwnerCourseTeacherActivity.class);//课程
                //Intent intent1 = new Intent(getActivity(),OwnerRewardTeacherActivity.class);//悬赏
                startActivity(intent2);
                break;
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

}
