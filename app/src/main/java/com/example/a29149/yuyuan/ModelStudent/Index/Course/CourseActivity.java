package com.example.a29149.yuyuan.ModelStudent.Index.Course;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.yuyuan.DTO.CourseTeacherDTO;
import com.example.a29149.yuyuan.OriginIndex.OriginIndexActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Util.GlobalUtil;

public class CourseActivity extends AppCompatActivity {

    //获取传递来的Intent
    private Intent intent;

    //保存点击的位置
    private int mPosition;

    @ViewInject(R.id.author)
    private TextView mAuthor;

    @ViewInject(R.id.head)
    private ImageView mHead;

    //课程标签
    @ViewInject(R.id.label)
    private TextView mCourseLabel;

    //课程评价
    @ViewInject(R.id.courseEvaluate)
    private TextView mCourseEvaluation;

    //教师个人评价
    @ViewInject(R.id.teacherEvaluate)
    private TextView mTeacherEvaluation;

    //教师教授的人数
    @ViewInject(R.id.numberEvaluate)
    private TextView mNumberEvaluation;

    //声望值
    @ViewInject(R.id.reputation)
    private TextView mReputation;

    //课程标题
    @ViewInject(R.id.title)
    private TextView mCourseTitle;

    //课程内容
    @ViewInject(R.id.course_content)
    private TextView mCourseContent;

    //课程价格
    @ViewInject(R.id.price)
    private TextView mCoursePrice;

    //创建课程的时间
    @ViewInject(R.id.createTime)
    private TextView mCreateTime;

    //课程备注
    @ViewInject(R.id.remark)
    private TextView mCourseRemark;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        //初始化传递来的参数
        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);

        //组件注入数据
        initData();
    }

    private void initData() {

        if (mPosition == -1)
        {
            Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(GlobalUtil.getInstance().getTechnicTagEnum());

        Log.i("葛尧",
//                 GlobalUtil.getInstance()
//                        .getCourseTeacherPopularDTOs(GlobalUtil.getInstance().getTechnicTagEnum()).toString()
                GlobalUtil.getInstance().getTechnicTagEnum().toString()
        );

        CourseTeacherDTO courseTeacherDTO = GlobalUtil.getInstance()
                .getCourseTeacherPopularDTOs(GlobalUtil.getInstance().getTechnicTagEnum())
                .get(mPosition)
                .getCourseTeacherDTO();

        mAuthor.setText("付费课程·"+ courseTeacherDTO.getCreatorId());
        mCourseLabel.setText(courseTeacherDTO.getTechnicTagEnum().toString());
        mCourseEvaluation.setText("s"+"/5");
        mTeacherEvaluation.setText("s"+"/5");
        mNumberEvaluation.setText("455");
        mReputation.setText("ssss");
        mCourseTitle.setText(courseTeacherDTO.getTopic());
        mCourseContent.setText(courseTeacherDTO.getDescription());
        mCoursePrice.setText(courseTeacherDTO.getPrice()+"");
        mCreateTime.setText("");
        mCourseRemark.setText("希望先和我联系和沟通一下！！！");
    }

    @OnClick(R.id.key_value)
    public void setSearchListener(View view) {
        Intent searchActivity = new Intent(this, SearchActivity.class);
        startActivity(searchActivity,
                ActivityOptions.makeSceneTransitionAnimation(this, view, "searchView").toBundle());
    }

    @OnClick(R.id.head)
    public void setHeadListener(View view) {
        Intent toTeacherIndexActivity = new Intent(this, OriginIndexActivity.class);
        startActivity(toTeacherIndexActivity,
                ActivityOptions.makeSceneTransitionAnimation(this, view, "shareHead").toBundle());
    }

    @OnClick({R.id.chart, R.id.shopping, R.id.buy, R.id.bt_return})
    public void setRadioButtonListener(View view) {
        switch (view.getId())
        {
            case R.id.chart:
                break;
            case R.id.shopping:
                break;
            case R.id.buy:
                Intent intent = new Intent(this, BuyCourseActivity.class);
                intent.putExtra("position", mPosition);
                startActivity(intent);

                //引入Activity栈对Activity进行管理。
                //如果购买成功则结束CourseActivity、BuyCourseActivity,跳转至订单界面
                //如果没有购买则正常返回
                AppManager.getInstance().addActivity(this);
                break;
            case R.id.bt_return:
                finish();
                break;
        }
    }
}
