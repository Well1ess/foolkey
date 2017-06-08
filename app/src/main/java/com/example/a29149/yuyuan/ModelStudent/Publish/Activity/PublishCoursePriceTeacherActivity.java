package com.example.a29149.yuyuan.ModelStudent.Publish.Activity;

import android.app.Activity;
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
import com.example.resource.component.baseObject.AbstractActivity;

/**
 * Created by MaLei on 2017/5/8.
 * Email:ml1995@mail.ustc.edu.cn
 * 学生填写课程价格
 */

public class PublishCoursePriceTeacherActivity extends AbstractActivity implements View.OnClickListener {

    private ImageView mReturn;
    private ImageView mGo;

    private EditText mCoursePrice;//课程价格
    private String[] courseChooseContent;//保存用户填写的信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_course);
        initView();
        courseChooseContent = GlobalUtil.getInstance().getCourseChooseContent();
    }

    private void initView() {
        mReturn = (ImageView) findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);
        mGo = (ImageView) findViewById(R.id.iv_go);
        mGo.setOnClickListener(this);

        mCoursePrice = (EditText) findViewById(R.id.ed_course_price);

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
        if (TextUtils.isEmpty(mCoursePrice.getText()))
            Toast.makeText(this, "请填写课程信息", Toast.LENGTH_SHORT).show();
        else
        {
            courseChooseContent[3] = mCoursePrice.getText().toString();
            GlobalUtil.getInstance().setCourseChooseContent(courseChooseContent);
            Intent intent = new Intent(PublishCoursePriceTeacherActivity.this, PublishCourseOptionsTeacherActivity.class);
            startActivity(intent);
        }
    }


}
