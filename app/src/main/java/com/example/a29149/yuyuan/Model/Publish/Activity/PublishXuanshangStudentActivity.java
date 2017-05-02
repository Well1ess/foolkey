package com.example.a29149.yuyuan.Model.Publish.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;

/**
 * Created by MaLei on 2017/5/1 0014.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生发布悬赏
 */

public class PublishXuanshangStudentActivity extends Activity implements View.OnClickListener {

    private TextView mCancel;//取消
    private TextView mPublish;//发布
    private EditText mXuanshangTitle;//悬赏标题
    private TextView mXuanshangTag;//悬赏标签
    private TextView mTagChoose;//悬赏标签选择按钮
    private  EditText mXuanshangCost;//悬赏费用
    private TextView mCourseType;//授课方式
    private TextView mCourseTypeChoose;//选择授课方式按钮
    private TextView mTeacherSex;//老师性别
    private TextView mTeacherSexChoose;//选择老师性别按钮
    private TextView mTeacherType;//老师类别
    private TextView mTeacherTypeChoose;//选择老师类别按钮
    private String[] mChooseContent;//保存用户选择的信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_xuanshang_student);
        initView();
        mChooseContent = new String[6];
    }

    private void initView() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mCancel.setOnClickListener(this);
        mPublish = (TextView) findViewById(R.id.tv_publish);
        mPublish.setOnClickListener(this);
        //悬赏标题
        mXuanshangTitle = (EditText) findViewById(R.id.et_title);
        //悬赏标签
        mXuanshangTag = (TextView) findViewById(R.id.tv_tag);
        mTagChoose = (TextView) findViewById(R.id.tv_tagChoose);
        mTagChoose.setOnClickListener(this);
        //悬赏花费
        mXuanshangCost = (EditText) findViewById(R.id.et_cost);
        //授课方式
        mCourseType = (TextView) findViewById(R.id.tv_courseType);
        mCourseTypeChoose = (TextView) findViewById(R.id.tv_courseTypeChoose);
        mCourseTypeChoose.setOnClickListener(this);
        //老师性别
        mTeacherSex = (TextView) findViewById(R.id.tv_teacherSex);
        mTeacherSexChoose = (TextView) findViewById(R.id.tv_teacherSexChoose);
        mTeacherSexChoose.setOnClickListener(this);
        //老师类别
        mTeacherType = (TextView) findViewById(R.id.tv_teacherType);
        mTeacherTypeChoose = (TextView) findViewById(R.id.tv_teacherTypeChoose);
        mTeacherTypeChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.tv_publish:
                publish();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_tagChoose:
                chooseTag();
                break;
            case R.id.tv_courseTypeChoose:
                chooseCourseType();
                break;
            case R.id.tv_teacherSexChoose:
                chooseTeacherSex();
                break;
            case R.id.tv_teacherTypeChoose:
                chooseTeacherType();
                break;
            default:
                break;
        }
    }

    private void publish() {
        mChooseContent[0] = mXuanshangTitle.getText().toString();
        mChooseContent[2] = mXuanshangCost.getText().toString();
        Log.i("malei",mChooseContent[0]);
        Log.i("malei",mChooseContent[1]);
        Log.i("malei",mChooseContent[2]);
        Log.i("malei",mChooseContent[3]);
        Log.i("malei",mChooseContent[4]);
        Log.i("malei",mChooseContent[5]);
    }

    //弹出悬赏选择标签
    private AlertDialog alertDialogChooseTag;
    public void chooseTag(){
        final String[] xuanshangTag = {"java","C", "Javascript","Python","PHP","Html5","Android","iOS","数据库","其它"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                xuanshangTag);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mXuanshangTag.setText("标签："+xuanshangTag[which]);
                        mChooseContent[1] = xuanshangTag[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
    }


    //弹出授课方式选择标签
    private AlertDialog alertDialogChooseCourseType;
    public void  chooseCourseType(){
        final String[] courseType = {"线上","线下", "不限"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择授课方式");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                courseType);

        alertDialogChooseCourseType = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCourseType.setText("授课方式："+courseType[which]);
                        mChooseContent[3] = courseType[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseCourseType.show();
    }

    //弹出老师性别选择标签
    private AlertDialog alertDialogChooseTeacherSex;
    public void chooseTeacherSex(){
        final String[] teacherSex = {"男","女"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                teacherSex);

        alertDialogChooseTeacherSex = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeacherSex.setText("老师性别："+teacherSex[which]);
                        mChooseContent[4] = teacherSex[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTeacherSex.show();
    }

    //弹出老师类别选择标签
    private AlertDialog alertDialogChooseTeacherType;
    public void chooseTeacherType(){
        final String[] teacherType = {"java","C", "Javascript","Python","PHP","Html5","Android","iOS","数据库","其它"};
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择老师类别");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                teacherType);

        alertDialogChooseTeacherType = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeacherType.setText("老师类别："+teacherType[which]);
                        mChooseContent[5] = teacherType[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTeacherType.show();
    }


}
