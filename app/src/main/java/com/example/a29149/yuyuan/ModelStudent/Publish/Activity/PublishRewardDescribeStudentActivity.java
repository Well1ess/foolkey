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
import com.example.resource.component.baseObject.AbstractActivity;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生填写悬赏内容、标题、标签
 */

public class PublishRewardDescribeStudentActivity extends AbstractActivity implements View.OnClickListener {

    private ImageView mReturn;
    private ImageView mGo;

    private EditText mRewardTitle;//悬赏标题
    private EditText mRewardContent;//悬赏标题
    private  TextView mTag;//悬赏标签
    private String[] rewardChooseContent;//保存用户填写的信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_reward);
        initView();
        rewardChooseContent = GlobalUtil.getInstance().getRewardChooseContent();
    }

    private void initView() {
        mReturn = (ImageView) findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);
        mGo = (ImageView) findViewById(R.id.iv_go);
        mGo.setOnClickListener(this);

        mRewardTitle = (EditText) findViewById(R.id.ed_reward_title);
        mRewardContent = (EditText) findViewById(R.id.ed_reward_content);

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
        if (TextUtils.isEmpty(mRewardTitle.getText()) || TextUtils.isEmpty(mRewardContent.getText()))
            Toast.makeText(this, "请填写悬赏信息", Toast.LENGTH_SHORT).show();
        else
        {
            rewardChooseContent[0] = mRewardTitle.getText().toString();
            rewardChooseContent[2] = mRewardContent.getText().toString();
            GlobalUtil.getInstance().setRewardChooseContent(rewardChooseContent);
            Intent intent = new Intent(PublishRewardDescribeStudentActivity.this, PublishRewardPriceStudentActivity.class);
            startActivity(intent);
        }
    }

    //弹出悬赏选择标签
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
                        rewardChooseContent[1] = Const.REWARD_TAG[which];
                        dialog.dismiss();
                    }
                }).create();
        alertDialogChooseTag.show();
    }
}
