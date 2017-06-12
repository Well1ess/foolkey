package com.example.a29149.yuyuan.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by geyao on 2017/6/12.
 */
@SuppressLint("AppCompatCustomTextView")
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        init(context);
//        setShadow(2, 2);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
//        setShadow(2, 2);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
//        setShadow(2, 2);
    }

    private void init(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "qianqiu.ttf");
        setTypeface(typeface);
        setShadow(2, 2);
    }

    private void setShadow(int x, int y){
        setShadowLayer(1f, x, y, Color.BLACK);
    }
}
