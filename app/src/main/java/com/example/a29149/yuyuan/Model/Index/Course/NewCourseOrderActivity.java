package com.example.a29149.yuyuan.Model.Index.Course;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.Secret.AESOperator;
import com.example.a29149.yuyuan.Util.URL;
import com.example.a29149.yuyuan.Util.UserConfig;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.Widget.Dialog.PayDialog;
import com.example.a29149.yuyuan.Widget.Dialog.WarningDisplayDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class NewCourseOrderActivity extends AppCompatActivity {

    //取消订单时的警告
    private WarningDisplayDialog.Builder mDisplayDialog;

    //付款时的对话框
    private PayDialog.Builder mPayDialog;

    String amount;
    String virtualMoney;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course_order);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        //获取从BuyCourseActivity传递的数据
        Intent intent = getIntent();
        Bundle order = intent.getExtras();

        String num = order.getString("num");
        amount = order.getString("orderMoney");
        virtualMoney = GlobalUtil.getInstance().getStudentDTO().getVirtualCurrency()+"";

        UserConfig userConfig = new UserConfig(NewCourseOrderActivity.this);
        password = userConfig.getStringInfo(UserConfig.xmlPASSWORD);

        mPayDialog = new PayDialog.Builder(this);
        mPayDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mPayDialog.setPositiveButton("付      款", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //账户余额不足
                if (Double.parseDouble(amount) > Double.parseDouble(virtualMoney))
                {
                    dialog.dismiss();
                    Toast.makeText(NewCourseOrderActivity.this, "余额不足，请充值！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //不dismiss
                if (!mPayDialog.getmPassword().equals(password))
                {
                    Toast.makeText(NewCourseOrderActivity.this, "密码输入错误!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                //TODO：网络通信
                PayAction payAction = new PayAction();
                payAction.execute();

            }
        });
        mPayDialog.create();
        mPayDialog.setmAmount(amount);
        mPayDialog.setmVirtualMoney(virtualMoney);


        mDisplayDialog = new WarningDisplayDialog.Builder(this);
        mDisplayDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDisplayDialog.setPositiveButton("确      定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                NewCourseOrderActivity.this.finish();
            }
        });
        mDisplayDialog.create();
    }

    @OnClick(R.id.bt_return)
    public void setBtReturnListener(View view)
    {
        this.finish();
    }

    @OnClick(R.id.cancel)
    public void setCancelListener(View view)
    {
        mDisplayDialog.setMsg("您确定要取消该订单吗？");
        mDisplayDialog.getDialog().show();
    }

    @OnClick(R.id.buy)
    public void setBuyListener(View view)
    {
        mPayDialog.getDialog().show();
    }

    //付款
    public class PayAction extends AsyncTask<String, Integer, String> {

        public PayAction() {
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

                log.d(this, GlobalUtil.getInstance().getAESKey());

                String validation = java.net.URLEncoder.encode(
                        AESOperator.getInstance().encrypt(jsonObject.toString()).replaceAll("\n", "愚"));

                java.net.URL url = new java.net.URL(URL.getLogOutURL(jsonObject.toString(),
                        validation,
                        ""));

                con = (HttpURLConnection) url.openConnection();
                log.d(this, AESOperator.getInstance().encrypt(jsonObject.toString()));
                log.d(this, "解密："+AESOperator.getInstance().decrypt(AESOperator.getInstance().encrypt(jsonObject.toString())));
                log.d(this, URL.getLogOutURL(jsonObject.toString(),
                        validation,
                        ""));
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
            }catch (Exception e) {
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

                    if (resultFlag.equals("success")) {

                        Toast.makeText(NewCourseOrderActivity.this, "付款成功！", Toast.LENGTH_SHORT).show();

                        NewCourseOrderActivity.this.finish();

                    } else {
                        Toast.makeText(NewCourseOrderActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(NewCourseOrderActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(NewCourseOrderActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
