package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Const;
import com.example.a29149.yuyuan.Util.GlobalUtil;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 老师填写课程内容、标题、标签
 */

public class  PublishCoursedDescribeTeacherActivity extends Activity implements View.OnClickListener {

    private ImageView mReturn;
    private ImageView mGo;

    private EditText mCourseTitle;//课程标题
    private EditText mCourseContent;//课程标题
    private  TextView mTag;//课程标签
    private String[] courseChooseContent;//保存用户填写的信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_course);
        initView();
        courseChooseContent = GlobalUtil.getInstance().getCourseChooseContent();
    }

    private void initView() {
        mReturn = (ImageView) findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);
        mGo = (ImageView) findViewById(R.id.iv_go);
        mGo.setOnClickListener(this);

        mCourseTitle = (EditText) findViewById(R.id.ed_course_title);
        mCourseContent = (EditText) findViewById(R.id.ed_course_content);

        mTag = (TextView) findViewById(R.id.tv_tag);
        mTag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_go:
                goNext();
                break;
            case R.id.tv_tag:
                chooseTag();
                break;
            default:
                break;
        }
    }

    private void goNext() {
        //提交用户的信息
        if (TextUtils.isEmpty(mCourseTitle.getText()) || TextUtils.isEmpty(mCourseContent.getText()))
            Toast.makeText(this, "请填写课程信息", Toast.LENGTH_SHORT).show();
        else
        {
            courseChooseContent[0] = mCourseTitle.getText().toString();
            courseChooseContent[2] = mCourseContent.getText().toString();
            GlobalUtil.getInstance().setCourseChooseContent(courseChooseContent);
            Intent intent = new Intent(PublishCoursedDescribeTeacherActivity.this, PublishCoursePriceTeacherActivity.class);
            startActivity(intent);
        }
    }

    //弹出课程选择标签
    private AlertDialog alertDialogChooseTag;
    public void chooseTag(){
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择内容标签");
        customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(18);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);


        ArrayAdapter<String> teachTypeItem = new ArrayAdapter<>(this,
                R.layout.dialog_team_project_item,
                Const.REWARD_TAG);

        alertDialogChooseTag = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teachTypeItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTag.setText("标签："+Const.REWARD_TAG[which]);
                        courseChooseContent[1] = Const.REWARD_TAG[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
    }
}
