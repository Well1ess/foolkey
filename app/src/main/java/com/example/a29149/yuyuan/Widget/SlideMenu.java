package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.example.a29149.yuyuan.Util.log;

/**
 * Created by 寻乐人 on 2017/4/8.
 */

public class SlideMenu extends FrameLayout {

    private final int mHorizontalPadding = 60;
    private final int mVerticalPadding = 5;

    private int mLastX = 0;

    private int mLastInterceptX = 0;
    private int mLastInterceptY = 0;

    private int mScreen;
    private int mMaxScroll;

    private VelocityTracker mTracker;
    private Scroller mScroller;

    public SlideMenu(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTracker = VelocityTracker.obtain();
        mScroller = new Scroller(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        mScreen = displayMetrics.widthPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                //当正在滑动及滑动动画没有播放完则拦截
                if (!mScroller.isFinished()) {
                    isIntercept = true;
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //x方向的滑动距离更大则认为是水平滑动，拦截
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY))
                    isIntercept = true;
                else
                    isIntercept = false;
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }

        mLastX = x;

        mLastInterceptX = x;
        mLastInterceptY = y;

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mMaxScroll == 0)
            throw new IllegalArgumentException();

        int x = (int) event.getX();
        int y = (int) event.getY();
        mTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int offsetX = deltaX;

                //向左滑动，且大于最大滑动距离，则开启弹性滑动(使用log函数求对数)
                if (getScrollX() >= mMaxScroll && deltaX < 0)
                    offsetX = -(int) (Math.log(Math.abs(deltaX)) / Math.log(2.2));
                //向右滑动，距离为0，则开启弹性滑动
                if (getScrollX() <= 0 && deltaX > 0)
                    offsetX = (int) (Math.log(deltaX) / Math.log(2.2));
                //当delta为0，则offset为0，否则，log在0处为无穷大，容易闪屏。
                if (deltaX == 0)
                    offsetX = 0;
                //只有以上三种情况使用log，其他情况依然是线性移动
                scrollBy(-offsetX, 0);

                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();

                //滑动到边缘，回弹
                if (scrollX <= 0) {
                    smoothScrollBy(-scrollX, 0);
                    break;
                }
                //滑动到边缘，回弹
                if (scrollX >= mMaxScroll) {
                    smoothScrollBy(mMaxScroll - scrollX, 0);
                    break;
                }

                mTracker.computeCurrentVelocity(1000);
                float xVelocity = mTracker.getXVelocity();
                if (Math.abs(xVelocity) > 200) {
                    //根据速度计算惯性
                    int dx = (int) (xVelocity / 10000 * mScreen);

                    dx = -dx;

                    //对dx进行纠正，防止滑出屏幕
                    if (xVelocity > 0) {
                        if (scrollX + dx <= 0) {
                            dx = -scrollX;
                        }
                    }

                    //对dx进行纠正，防止滑出屏幕
                    if (xVelocity < 0) {
                        if (scrollX + dx >= mMaxScroll) {
                            dx = this.getWidth() - getScrollX() - mScreen;
                        }
                    }
                    smoothScrollBy(dx, 0);
                }
                mTracker.clear();
                break;
        }

        mLastX = x;

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int mChildHeight = 2 * mVerticalPadding;
        int mMeasureWidth = mHorizontalPadding;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getMeasuredHeight() > mChildHeight)
                mChildHeight += view.getMeasuredHeight();

            mMeasureWidth += view.getMeasuredWidth() + mHorizontalPadding;
        }

        setMeasuredDimension(mMeasureWidth, mChildHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = mHorizontalPadding;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(childLeft,
                    mVerticalPadding,
                    childLeft + childView.getMeasuredWidth(),
                    mVerticalPadding + childView.getMeasuredHeight());

            childLeft += childView.getMeasuredWidth() + mHorizontalPadding;
        }

        //判断最大可滑动距离
        mMaxScroll = this.getWidth() > mScreen ? this.getWidth() - mScreen : this.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * @param dx 相对距离
     * @param dy
     * 滑动的结果为 getScrollX() + dx
     */
    public void smoothScrollBy(int dx, int dy) {
        //缓慢滑动
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }


    public void smoothScrollByChild(int index) {
        if (index > this.getChildCount())
            throw new IllegalArgumentException("index 大于子节点个数！");
        int[] location = new int[2];

        View childView = this.getChildAt(index);
        childView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        log.d(this, x);
        log.d(this, y);

        //若子节点在屏幕外面，或者子节点有部分在外面
        if (x > mScreen || x + childView.getWidth() > mScreen) {
            this.smoothScrollBy(x - mScreen + childView.getWidth(), 0);
            return;
        }
        //同理
        if (x < 0) {
            this.smoothScrollBy(x, 0);
            return;
        }
    }

    @Override
    public void computeScroll() {
        //computeScrollOffset()会不断更新mScroller的坐标
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


}
