package com.example.a29149.yuyuan.ModelStudent.Me.Recharge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.ModelStudent.Me.MeMainFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.money.ChargeMoneyController;
import com.example.resource.component.baseObject.AbstractAppCompatActivity;

import org.json.JSONObject;

import static com.example.a29149.yuyuan.Main.MainStudentActivity.SHOW_OF_FIFTH_TAG;


/**
 * Created by MaLei on 2017/5/9.
 * Email:ml1995@mail.ustc.edu.cn
 * 用户充值界面
 */

public class RechargeActivity extends AbstractAppCompatActivity {

    private static final String TAG = "RechargeActivity";

    @ViewInject(R.id.et_amount)
    private EditText mMoney;

    Intent intent;

    String money = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        Log.d(TAG, "onCreate: 49 " + getIntent());

    }

    /**
     * 返回按键
     * @param view
     */
    @OnClick(R.id.ib_return)
    public void setBtReturnListener(View view)
    {
        Intent intent = getIntent();
        intent.putExtra("date_return", "返回");
        setResult(RESULT_CANCELED, intent);
        this.finish();
    }

    /**
     * 点击充值按钮
     * @param view
     */
    @OnClick(R.id.tv_recharge)
    public void setRechargeListener(View view)
    {
        if (mMoney.getText().toString().equals(""))
        {
            Toast.makeText(this, "金额不能为0", Toast.LENGTH_SHORT).show();
            return;
        }

        money = mMoney.getText().toString();
        if (Integer.parseInt(money)  > 9999){
            Toast.makeText(this, "一次金额不能超过9999哦", Toast.LENGTH_SHORT).show();
            return;
        }
        Recharge recharge = new Recharge();
        recharge.execute();
    }

    /**
     * 充值
     */
    public class Recharge extends AsyncTask<String, Integer, String> {

        public Recharge() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return ChargeMoneyController.execute(money);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            log.d(this, result);
            if (result != null) {
                Intent intent = getIntent();
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {

                        Toast.makeText(RechargeActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();
                        Double virtualCurrency = Double.parseDouble(jsonObject.getString("virtualCurrency") + "");

                        GlobalUtil.getInstance().getStudentDTO().setVirtualCurrency(virtualCurrency);

                        //通知MainStudentActivity，后者再调用fragment的方法来进行数据刷新

                        intent.putExtra("virtualCurrency", virtualCurrency + "");
                        setResult(RESULT_OK, intent);

                    } else {
                        Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    //失败
                    setResult(RESULT_CANCELED, intent);
                    e.printStackTrace();
                    Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }finally {
//                    Log.d(TAG, "onPostExecute: 135");
                    finish();
                }
            } else {
                Toast.makeText(RechargeActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    }

}
