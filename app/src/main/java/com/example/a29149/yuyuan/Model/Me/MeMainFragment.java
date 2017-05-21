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
    private TextView mChangeRole;//切换用户角色
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

        mChangeRole = (TextView) view.findViewById(R.id.change_role);
        mChangeRole.setOnClickListener(this);

        mOwnerReward = (TextView) view.findViewById(R.id.owner_reward);
        mOwnerReward.setOnClickListener(this);

        mOwnerCourse = (TextView) view.findViewById(R.id.tv_course);
        mOwnerCourse.setOnClickListener(this);


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
            case R.id.change_role:
                changeRole();
                break;
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

    //切换用户角色
    private void changeRole() {
        //验证身份
        TeacherDTO teacherDTO = GlobalUtil.getInstance().getTeacherDTO();

        if(teacherDTO != null)
        {
            Log.i("malei",teacherDTO.toString());
            VerifyStateEnum verifyState = teacherDTO.getVerifyState();
            Log.i("malei",verifyState.toString());
            Log.i("malei",teacherDTO.toString());
            Log.i("malei",verifyState.toString());
            //如果是已认证老师或者是认证中的老师，则直接接单
            if(verifyState.compareTo(VerifyStateEnum.processing) == 0
                    || verifyState.compareTo(VerifyStateEnum.verified) == 0)
            {
                Intent intent = new Intent(getActivity(),MainTeacherActivity.class);
                startActivity(intent);
            }
            else
            {
                //Toast.makeText(getContext(), "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
                displayInfo.setMsg("抱歉，您现在不是已认证老师，请先认证？\n \n 点击 确定 申请认证");

                displayInfo.getDialog().show();

            }
        }
        else
        {
            Log.i("malei","teacherDTO是空的");
            //不是已认证老师，跳转到申请认证页面
            Toast.makeText(getContext(), "抱歉，您现在不是已认证老师，请先认证！", Toast.LENGTH_SHORT).show();
            displayInfo.setMsg("抱歉，您现在不是已认证老师，请先认证？\n \n 点击 确定 申请认证");

            displayInfo.getDialog().show();
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


    //获取抵扣卷
    public class CouponAction extends AsyncTask<String, Integer, String> {

        public CouponAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            System.out.println();
            System.out.println(this.getClass() + "这里有个方法依然是写死的\n");
            return URL.doWithCouponURL("1", "20");
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

    //切换身份
    public class ChangeRole extends AsyncTask<String, Integer, String> {

        public ChangeRole() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return URL.doWithSwitchToTeacher();
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

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {
                JSONObject target = new JSONObject();
                String token = GlobalUtil.getInstance().getToken();
                target.put("token",token);
                java.net.URL url = new java.net.URL(URL.getApplyVerifyTeacher(target.toString()));
                Log.i("malei",target.toString());
                con = (HttpURLConnection) url.openConnection();
                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("POST");
                con.setRequestProperty("contentType", "UTF-8");

                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return sb.toString();
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
