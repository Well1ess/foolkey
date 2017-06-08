package com.example.a29149.yuyuan.ModelStudent.Me.Coupon;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.OnClick;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.AbstractObject.AbstractAppCompatActivity;

/**
 * Created by 张丽华 on 2017/5/10.
 * Description:我拥有的优惠劵
 */

public class CouponActivity extends AbstractAppCompatActivity {

    @ViewInject(R.id.coupon_list)
    private ListView mCouponList;

    private CouponListAdapter couponListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        couponListAdapter = new CouponListAdapter(this);
        mCouponList.setAdapter(couponListAdapter);
    }

    @OnClick(R.id.ib_return)
    public void setReturnListener(View view)
    {
        this.finish();
    }
}
