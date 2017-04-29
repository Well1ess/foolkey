package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.a29149.yuyuan.R;

/**
 * Created by 张丽华 on 2017/4/26.
 * Description:
 */

public class DynamicListView extends ListView {

    private int mLastVisibleItem;//最后一个可见的Item

    private int mTotalItemCount;//中的Item个数

    private boolean mIsLoading = false;//是否动态刷新中

    private onLoadingListener onLoadingListener;

    private View mFooter;

    public DynamicListView(Context context) {
        super(context);
        init(context);
    }

    public DynamicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
        this.addFooterView(mFooter);
        mFooter.setVisibility(View.GONE);

        //设置本list的滑动监听
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //当前可见Item为最后一个Item，且没有在加载，则加载
                if (mLastVisibleItem == mTotalItemCount && scrollState == SCROLL_STATE_IDLE){
                    if (!mIsLoading){
                        mIsLoading = true;
                        mFooter.setVisibility(View.VISIBLE);
                        onLoadingListener.setLoad();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                DynamicListView.this.mLastVisibleItem = firstVisibleItem + visibleItemCount;
                DynamicListView.this.mTotalItemCount = totalItemCount;
            }
        });
    }

    //加载完成
    public void onLoadFinish(){
        mIsLoading = false;
        mFooter.setVisibility(View.GONE);
    }


    public void setOnLoadingListener(onLoadingListener onLoadingListener){
        this.onLoadingListener = onLoadingListener;
    }

    public interface onLoadingListener{
        void setLoad();
    }
}
