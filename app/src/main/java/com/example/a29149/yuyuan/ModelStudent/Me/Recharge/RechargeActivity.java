package com.example.a29149.yuyuan.ModelStudent.Me.Recharge;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Me.MeMainFragment;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.controller.money.ChargeMoneyController;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;

import org.json.JSONObject;


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
    @OnClick(R.id.tv_return)
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
     * 会先通知MainStudentActivity的MeMainFragment刷新数值
     * 如果null
     * 则通知源activity，完成更新
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
            if (result != null) {
                //获取意图
                Intent intent = getIntent();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultFlag = jsonObject.getString("result");

                    if (resultFlag.equals("success")) {
                        //充值成功
                        //提示用户
                        Toast.makeText(RechargeActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();
                        //从服务器获取余额的最新值
                        Double virtualCurrency = Double.parseDouble(jsonObject.getString("virtualCurrency") + "");
                        //更新本地学生的资料
                        GlobalUtil.getInstance().getStudentDTO().setVirtualCurrency(virtualCurrency);
                        //获取学生activity
                        MainStudentActivity mainStudentActivity = (MainStudentActivity) AppManager.getActivity(MainStudentActivity.class);
                        if (mainStudentActivity != null){
                            //如果学生的activity不为null，则可以直接调用它的fragment
                            MeMainFragment fragment = (MeMainFragment) mainStudentActivity.getMeMainFragment();
                            //更新fragment里，金额的显示
                            fragment.setVirtualMoney( virtualCurrency + "" );
                        }else {
                            //如果这个时候学生的activity不存在了
                            //通知源activity，充值完成
                            intent.putExtra("virtualCurrency", virtualCurrency + "");
                            setResult(RESULT_OK, intent);
                        }

                    } else {
                        //充值失败 TODO
                        Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    //失败
                    setResult(RESULT_CANCELED, intent);
                    e.printStackTrace();
                    Toast.makeText(RechargeActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }finally {
//                    Log.d(TAG, "onPostExecute: 135");
                    //不管怎么样，都要finish
                    finish();
                }
            } else { //没有获取到结果
                Toast.makeText(RechargeActivity.this, "网络连接失败T_T", Toast.LENGTH_SHORT).show();
            }
            finish();
        }

    }

}
