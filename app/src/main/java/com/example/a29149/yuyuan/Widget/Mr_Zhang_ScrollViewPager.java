package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.R;

import java.util.List;

/**
 * 华哥写的自定义的ScrollViewPager
 * 用来做父fragment管理子fragment
 * 可以实现类似于TabHost的功能
 * Created by geyao on 2017/6/10.
 */

public class Mr_Zhang_ScrollViewPager extends FrameLayout {

    //滑动的速度要求
    private static final int SCROLL_SPEED = 100;

    //滑动超过屏幕的 1/N 后，翻页
    private static final int SWITCH_THRESHOLD = 10;

    //测量速度的时间间隔
    private static final int TIME_FOR_SPEED = 400;

    //记录当前child的位置
    private static int mCurrentChild = 0;
    //记录多少个fragment，由xml传递
    private static int maxChildViewCount = 0;

    //记录屏幕尺寸
    private int mScreenWidth;
    private int mScreenHeight;

    //记录上次拦截的触摸位置
    private int mInterceptX = 0;
    private int mInterceptY = 0;

    //记录上次触摸位置
    private int mLastX = 0;
    private int mLastY = 0;

    //记录手指滑动的速度
    private VelocityTracker mTracker;

    private Scroller mScroller;

    //比如某个fragment被选中时的回掉
    private onChildSelectedListener onChildSelectedListener;

    //父fragment
    private AbstractFragment mFragment;
    //子fragment列表
    private List<AbstractFragment> fragmentList;
    //要替换的那个FrameLayout的id
    private int mViewContainerId;
    private static final String TAG = "Mr_Zhang_ScrollViewPager";

    /**
     * @param onChildSelectedListener
     * @discription 设置监听
     * @author 29149
     * @time 2017/6/10 9:40
     */
    public void setOnChildSelectedListener(Mr_Zhang_ScrollViewPager.onChildSelectedListener onChildSelectedListener) {
        this.onChildSelectedListener = onChildSelectedListener;
    }

    /**
     * @param viewContainerId 替换的id
     * @param fragment 父fragment
     * @param fragments 子fragment列表
     * @discription 对fragment切换注入数据
     * @author 29149
     * @time 2017/6/10 9:41
     */
    public void setFragment(int viewContainerId, AbstractFragment fragment, List<AbstractFragment> fragments) {
        mViewContainerId = viewContainerId;
        mFragment = fragment;
        this.fragmentList = fragments;
    }

    public Mr_Zhang_ScrollViewPager(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public Mr_Zhang_ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Mr_Zhang_ScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Mr_Zhang_ScrollViewPager);
            maxChildViewCount = typedArray.getInt(R.styleable.Mr_Zhang_ScrollViewPager_pageMaxChildView, Integer.MAX_VALUE);
            if (maxChildViewCount == Integer.MAX_VALUE)
                throw new IllegalArgumentException("你确定你没忘记在xml里面设置属性吗？");
        }

        mScroller = new Scroller(context);
        mTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                //如果动画未播放完则拦截
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //如果竖直方向偏移量小于水平方向则拦截
                int deltaX = x - mInterceptX;
                int deltaY = y - mInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }

        mLastX = x;
        mLastY = y;
        mInterceptX = x;
        mInterceptY = y;

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //跟随手指移动
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(TIME_FOR_SPEED);
                float xVelocity = mTracker.getXVelocity();
                //如果水平方向速度大于阈值，即快速滑动一下
                if (Math.abs(xVelocity) >= SCROLL_SPEED) {
                    //速度大于0则表示右滑。。。
                    mCurrentChild = xVelocity > 0 ? mCurrentChild - 1 : mCurrentChild + 1;
                } else {
                    //如果水平方向速度小于400，则按照滑动的距离
                    //如果滑动了屏幕的一般，且是向左滑动则+1
                    if (Math.abs(getScrollX()) > mScreenWidth / SWITCH_THRESHOLD && getScrollX() > 0) {
                        mCurrentChild++;

                    } else if (Math.abs(getScrollX()) > mScreenWidth / SWITCH_THRESHOLD && getScrollX() < 0) {
                        //如果滑动了屏幕的一般，且是向右滑动则-1
                        mCurrentChild--;

                    }
                }
                mCurrentChild = Math.max(0, Math.min(mCurrentChild, maxChildViewCount - 1));

                //切换
                mFragment.switchFragment(mViewContainerId, fragmentList.get(mCurrentChild));
                //重置
                scrollTo(0, 0);

                if (onChildSelectedListener != null)
                    onChildSelectedListener.onSelected( fragmentList.get(mCurrentChild), mCurrentChild);
                mTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }


    public interface onChildSelectedListener {
        void onSelected(AbstractFragment yy, int pos);
    }

}
