package com.example.a29149.yuyuan.Model.Me.Coupon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;

public class CouponActivity extends AppCompatActivity {

    @ViewInject(R.id.coupon_list)
    private ListView mCouponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
    }
}
