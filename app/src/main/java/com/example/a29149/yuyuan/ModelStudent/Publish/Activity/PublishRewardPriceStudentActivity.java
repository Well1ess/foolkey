package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.GlobalUtil;
import com.example.a29149.yuyuan.AbstractObject.AbstractActivity;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生填写悬赏价格
 */

public class PublishRewardPriceStudentActivity extends AbstractActivity implements View.OnClickListener {

    private ImageView mReturn;
    private ImageView mGo;

    private EditText mRewardPrice;//悬赏价格
    private String[] rewardChooseContent;//保存用户填写的信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_reward);
        initView();
        rewardChooseContent = GlobalUtil.getInstance().getRewardChooseContent();
    }

    private void initView() {
        mReturn = (ImageView) findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);
        mGo = (ImageView) findViewById(R.id.iv_go);
        mGo.setOnClickListener(this);

        mRewardPrice = (EditText) findViewById(R.id.ed_reward_price);

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
            default:
                break;
        }
    }

    private void goNext() {
        //提交用户的信息
        if (TextUtils.isEmpty(mRewardPrice.getText()))
            Toast.makeText(this, "请填写价格", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent = new Intent(PublishRewardPriceStudentActivity.this, PublishRewardOptionsStudentActivity.class);
            intent.putExtra("price", mRewardPrice.getText() + "");
            intent.putExtra("title", getIntent().getStringExtra("title") + "");
            intent.putExtra("description", getIntent().getStringExtra("description") + "");
            intent.putExtra("tag", getIntent().getStringExtra("tag") + "");

            startActivity(intent);
            finish();
        }
    }


}
