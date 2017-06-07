package com.example.a29149.yuyuan.ModelStudent.Index.Course;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.Enum.CourseTypeEnum;
import com.example.a29149.yuyuan.Enum.TeachMethodEnum;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.Util.log;
import com.example.a29149.yuyuan.controller.course.course.ApplyController;

import org.json.JSONObject;

public class BuyCourseActivity extends AppCompatActivity {

    //获取传递来的Intent
    private Intent intent;

    //保存点击的位置
    private int mPosition;

    //用于保存购买的个数
    private float mNum = 0;

    //购买的个数
    @ViewInject(R.id.num)
    private TextView mCourseNum;

    //授方式选择对话框
    private Dialog mSelectTeachType;

    //授课方式
    @ViewInject(R.id.teach_type)
    private TextView mTeachType;

    //总的订单金额
    @ViewInject(R.id.money)
    private TextView mOrderMoney;

    //单价
    private Double mUnitPrice = 0.0;
    //总金额
    private double sum;
    //上课方式
    private TeachMethodEnum teachMethodEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_course);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        //初始化
        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);
        mUnitPrice = GlobalUtil.getInstance().getCourseTeacherPopularDTOs(GlobalUtil.getInstance().getTechnicTagEnum())
                .get(mPosition).getCourseTeacherDTO().getPrice();

        //创建授课方式选择的对话框
        createTeachTypeDialog();

    }

    //创建授课方式选择的对话框
    public void createTeachTypeDialog(){
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择授课方式");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        final ArrayAdapter<String> teachTypeItem = new ArrayAdapter<String>(this,
                R.layout.dialog_team_project_item,
                Const.TEACH_METHOD);

        mSelectTeachType = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teachMethodEnum = TeachMethodEnum.values()[which];
                        mTeachType.setText(Const.TEACH_METHOD[which]);
                        dialog.dismiss();
                    }
                }).create();
    }

    @OnClick(R.id.tv_return)
    public void setReturnListener(View view)
    {
        //取消
        AppManager.getInstance().removeActivity(CourseActivity.class);
        this.onBackPressed();
    }

    //提交订单
    @OnClick(R.id.confirm_buy)
    public void setConfirmBuyListener(View view)
    {
        if (mPosition == -1)
        {
            Toast.makeText(this, "获取课程详细信息失败！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (teachMethodEnum == null)
        {
            Toast.makeText(this, "请选择授课方式！", Toast.LENGTH_SHORT).show();
            return;
        }

        //确定购买（生成订单）
        AppManager.getInstance().finishActivity(CourseActivity.class);

        //TODO:网络数据传输
        SubmitNewOrderAction submitNewOrderAction = new SubmitNewOrderAction();
        submitNewOrderAction.execute(
                GlobalUtil.getInstance().getCourseTeacherPopularDTOs(GlobalUtil.getInstance().getTechnicTagEnum())
                        .get(mPosition).getCourseTeacherDTO().getId()+"",
                sum+"",
                mNum+"",
                1+"",
                teachMethodEnum.toString(),
                CourseTypeEnum.values()[0].toString(),
                GlobalUtil.getInstance().getCourseTeacherPopularDTOs(GlobalUtil.getInstance().getTechnicTagEnum()).
                        get(mPosition).getCourseTeacherDTO().getCreatorId()+""
                );
    }

    @OnClick(R.id.reduce_num)
    public void setReduceNumListener(View view){
        if (mNum > 0.0)
            mNum-=0.5;
        else
            mNum = 0;

        sum = mNum * mUnitPrice;
        mOrderMoney.setText("￥ " + sum);
        mCourseNum.setText(mNum+"");
    }

    @OnClick(R.id.add_num)
    public void setAddNumListener(View view)
    {
        mNum+=0.5;

        sum = mNum * mUnitPrice;
        mOrderMoney.setText("￥ " + sum);
        mCourseNum.setText(mNum+"");
    }

    //授课方式的选择
    @OnClick(R.id.teach_type)
    public void setTeachTypeListener(View view)
    {
        mSelectTeachType.show();
    }


    public class SubmitNewOrderAction extends AsyncTask<String, Integer, String> {

        public SubmitNewOrderAction() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            return ApplyController.execute(
                    params[0],
                    params[1],
                    params[2],
                    params[3],
                    params[4],
                    params[5],
                    params[6]
            );
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


                        Bundle orderDetail = new Bundle();
                        orderDetail.putString("teachType", mTeachType.getText().toString());
                        orderDetail.putString("remark", "");
                        orderDetail.putString("deduce", 1+"");
                        orderDetail.putString("num", mNum+"");
                        orderDetail.putString("orderMoney", sum+"");

                        Intent toOrderDetail = new Intent(BuyCourseActivity.this, NewCourseOrderActivity.class);
                        toOrderDetail.putExtras(orderDetail);
                        startActivity(toOrderDetail);

                        BuyCourseActivity.this.finish();

                    } else {
                        Toast.makeText(BuyCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BuyCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(BuyCourseActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
