package com.example.a29149.yuyuan.AbstractObject;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

/**
 * 根据子item的高度自适应RecyclerView的高度
 * Created by geyao on 2017/6/23.
 */

public class YYLayoutManager extends LinearLayoutManager {

    private int maxHeight;

    @SuppressWarnings("UnusedDeclaration")
    public YYLayoutManager(Context context) {
        super(context);
    }

    @SuppressWarnings("UnusedDeclaration")
    public YYLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
    private int[] mMeasuredDimension = new int[2];

    @Override
    public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
        try {
            if(maxHeight == 0 && getItemCount() > 0) {
                View child = recycler.getViewForPosition(0);
                RecyclerView.LayoutParams params = (LayoutParams) child.getLayoutParams();
                child.measure(widthSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int height = child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom()
                        + params.topMargin + params.bottomMargin;
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
            heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }catch (IndexOutOfBoundsException e){
            //
        }
    }
}
