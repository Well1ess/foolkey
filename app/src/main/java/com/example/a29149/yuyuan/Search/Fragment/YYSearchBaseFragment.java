package com.example.a29149.yuyuan.Search.Fragment;

import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;

/**
 * 搜索的Fragment都要继承这个抽象类
 * Created by geyao on 2017/6/12.
 */

public abstract class YYSearchBaseFragment extends AbstractFragment {

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索的抽象方法，由SearchActivity调用
     * @param pageNo
     * @param keyValue
     */
    public abstract void search(String pageNo, String keyValue);
}
