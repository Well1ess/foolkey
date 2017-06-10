package com.example.a29149.yuyuan.AbstractObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by geyao on 2017/6/8.
 */

public abstract class AbstractFragment extends Fragment {

    private AbstractFragment mCurrentFragment; //暂存当前的fragment

    /**
     * 切换Fragment的方法
     *
     * @param containerViewId containerView的R.id
     * @param yyFragment      目标fragment
     * @return 切换是否正确
     * @author shs1330
     * @time 2017/6/7 15:43
     */
    public boolean switchFragment(int containerViewId, AbstractFragment yyFragment) {
        if (yyFragment == null)
            return false;

        FragmentTransaction fTransaction = getChildFragmentManager().beginTransaction();
        //判断是否为第一次启动，若mCurrentFragment不为空则为第一次启动
        if (mCurrentFragment != null) {
            //先隐藏当前Fragment
            fTransaction.hide(mCurrentFragment);
        }
        //目标Fragment如果以前没有被添加过
        if (!yyFragment.isAdded()) {
            //先添加
            fTransaction.add(containerViewId, yyFragment);
        }
        //随后显示
        fTransaction.show(yyFragment).commit();
        //保存至当前Fragment
        mCurrentFragment = yyFragment;
        return true;
    }
}
