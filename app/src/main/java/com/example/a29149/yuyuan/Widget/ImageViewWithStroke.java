package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Created by geyao on 2017/5/24.
 */

public class ImageViewWithStroke extends AppCompatImageView {

    //外圈白线和内圈黑线边框之间的间隔。
    private int gap = 25;

    //平衡在计算半径时候因为除以2导致半径计算的误差
    private int balance_factor = 2;

    public ImageViewWithStroke(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageViewWithStroke(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewWithStroke(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawInnerBlackCircle(canvas, 4);
        drawOutWhiteCircle(canvas, 1);
    }


    //画最外层的白色边线
    private void drawOutWhiteCircle(Canvas canvas, int strokeWidth) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        int w = this.getPaddingLeft() + this.getPaddingRight();
        int x = getWidth() - w;
        int r = x / 2;

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r + strokeWidth + gap, paint);
    }

    //画最内层的黑色边框
    private void drawInnerBlackCircle(Canvas canvas, int strokeWidth) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        int w = this.getPaddingLeft() + this.getPaddingRight();
        float x = getWidth() - w;
        float r = x / 2;

        //半径减去平衡因子是因为在取整时候四舍五入
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (r - balance_factor) + strokeWidth, paint);
    }
}
