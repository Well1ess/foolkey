package com.example.a29149.yuyuan.AccessOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.log;

public class AccessOrderActivity extends AppCompatActivity {

    //课程评价
    @ViewInject(R.id.course_access)
    private RatingBar mCourseAccess;

    //用于记录课程评价分数
    private float mCourseAccessScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_order);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        //可以修改软键盘位置，也可以在AndroidManifest.xml里面配置
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mCourseAccess.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mCourseAccessScore = rating;
                log.d(this, rating +"");
            }
        });
    }
}
