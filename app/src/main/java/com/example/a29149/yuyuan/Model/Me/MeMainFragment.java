package com.example.a29149.yuyuan.Model.Me;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CouponDTO;
import com.example.a29149.yuyuan.Model.Discovery.Activity.ApplyRewardTeacherActivity;
import com.example.a29149.yuyuan.Model.Me.Coupon.CouponActivity;
import com.example.a29149.yuyuan.Model.Me.Recharge.RechargeActivity;
import com.example.a29149.yuyuan.Model.Me.Setting.SettingActivity;
import com.example.a29149.yuyuan.Model.Publish.Activity.ApplyAuthenticationTeacherActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.log;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import static com.example.a29149.yuyuan.Util.Const.FROM_ME_FRAGMENT_TO_RECHARGE;

public class MeMainFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_me_main, container, false);
        view.findViewById(R.id.change_role).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ApplyAuthenticationTeacherActivity.class);
                startActivity(intent);
            }
        });

        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();

        return view;
    }

    private void initView()
    {
        int virtualMoney = DoubleParseInt(GlobalUtil.getInstance().getStudentDTO().getVirtualCurrency());
        mVirtualMoney.setText(virtualMoney+"");
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

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                java.net.URL url = new java.net.URL(URL.getCouponURL("1", "10"));
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getCouponURL("1", "1"));

                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("GET");
                con.setRequestProperty("contentType", "GBK");

                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {

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

            StringBuffer sb = new StringBuffer();
            BufferedReader reader = null;
            HttpURLConnection con = null;

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", GlobalUtil.getInstance().getToken());

                java.net.URL url = new java.net.URL(URL.getSwitchToTeacher());
                con = (HttpURLConnection) url.openConnection();
                log.d(this, URL.getSwitchToTeacher());

                // 设置允许输出，默认为false
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);

                con.setRequestMethod("GET");
                con.setRequestProperty("contentType", "GBK");

                // 获得服务端的返回数据
                InputStreamReader read = new InputStreamReader(con.getInputStream());
                reader = new BufferedReader(read);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {

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


}
