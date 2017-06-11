package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.a29149.yuyuan.R;

/**
 * Created by 29149 on 2017/4/5.
 */

public class SlideRefreshLayout extends FrameLayout {
    private static final String TAG = "SlideRefreshLayout";
    //滑动跟手的程度，越小越灵敏
    private static final double SCROLL_SPEED = 1.5;
    //滑动的阈值
    private static final int SCROLL_THRESHOLD = 50;
    //测量速度的时间间隔
    private static final int OBSERVE_TIME = 1000;
    //滑动速度的阈值
    private static final int SPEED_THRESHOLD = 150;

    //手指滑动距离的阈值
    private static final int FINGER_DISTANCE = 150;


    //记录手指滑动的速度
    private VelocityTracker mTracker;

    private View mRotateView;

    private Context mContext;

    private Scroller mScroller;
    private int mLastX = 0;
    private int mLastY = 0;

    private int mListInterceptX = 0;
    private int mListInterceptY = 0;

    private onSlideRefreshListener onSlideRefreshListener;

    public SlideRefreshLayout(Context context) {
        super(context);
        mScroller = new Scroller(context);
        mContext = context;
        mTracker = VelocityTracker.obtain();
    }

    public SlideRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mContext = context;
        mTracker = VelocityTracker.obtain();
    }

    public SlideRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mContext = context;
        mTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept = false;

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
//                if (!mScroller.isFinished())
//                {
//                   mScroller.abortAnimation();
//                   isIntercept = true;
//                }
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = x - mListInterceptX;
                int deltaY = y - mListInterceptY;

                if (isPositionInTop() && Math.abs(deltaX) < Math.abs(deltaY) && deltaY > 0)
                    isIntercept = true;
                else
                    isIntercept = false;
                break;
            case MotionEvent.ACTION_UP:
                mListInterceptX = mListInterceptY = 0;
                isIntercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;

        mListInterceptX = x;
        mListInterceptY = y;
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                startRotate();
                int deltaY = y - mLastY;

                //TODO 这里其实应该是记录速度，但总是失败，我只有根据手指滑动的距离来做了
                if (deltaY > FINGER_DISTANCE){
                    onSlideRefreshListener.onRefresh();
                    break;
                }

                int offsetY = 0;
                if (deltaY != 0 && deltaY > 0)
                    offsetY = (int) (Math.log(deltaY) / Math.log(SCROLL_SPEED));
                if (deltaY != 0 && deltaY < 0 && getScrollY() < 0)
                    offsetY = -(int) (Math.log(Math.abs(deltaY)) / Math.log(SCROLL_SPEED));

                scrollBy(0, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();

                abortRotate();
                if (scrollY < - SCROLL_THRESHOLD) {
                    onSlideRefreshListener.onRefresh();
                }
                smoothScrollBy(0, -scrollY);
                break;
        }
        //清空速度追踪器
        mTracker.clear();
        mLastY = y;
        mLastX = x;
        return true;
    }

    public void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(0, getScrollY(), 0, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    //设置回调接口
    public void setOnSlideRefreshListener(onSlideRefreshListener onSlideRefreshListener) {
        this.onSlideRefreshListener = onSlideRefreshListener;
    }

    public boolean isPositionInTop() {
        //第二个子节点为列表
        ListView listView = null;
        for (int i = 0; i < this.getChildCount(); i++) {
            View childView = this.getChildAt(i);
            if (childView instanceof ListView) {
                listView = (ListView) childView;
                break;
            }
        }
        if (listView.getFirstVisiblePosition() == 0) {
            View view = listView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

    public void setRotateView(View view) {
        mRotateView = view;
    }

    private void startRotate() {
        if (mRotateView != null && mRotateView.getAnimation() == null) {
            Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            operatingAnim.setInterpolator(new LinearInterpolator());
            if (operatingAnim != null && mRotateView != null) {
                mRotateView.startAnimation(operatingAnim);
            }
        }
    }

    private void abortRotate() {
        if (mRotateView != null && mRotateView.getAnimation() != null) {
            mRotateView.clearAnimation();
        }
    }


    public interface onSlideRefreshListener {
        void onRefresh();
    }
}
