package com.example.a29149.yuyuan.Util;

import android.app.Activity;
import android.content.Context;

import com.example.a29149.yuyuan.AbstractObject.YYBaseAdapter;

import java.util.Stack;

/**
 * Author:       geyao
 * Date:         2017/6/14
 * Email:        gy2016@mail.ustc.edu.cn
 * Description:  类似于AppManager的管理器,用来更新数据列表
 *
 */

public class AdapterManager {
    private static Stack<YYBaseAdapter> adapterStack = new Stack<>();
    private static volatile AdapterManager instance;

    private AdapterManager() {
        super();
    }

    public static AdapterManager getInstance() {
        if (instance == null) {
            synchronized (AdapterManager.class) {
                if (instance == null) {
                    instance = new AdapterManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addAdapter(YYBaseAdapter adapter) {
        if (adapterStack == null) {
            adapterStack = new Stack<>();
        }
        adapterStack.add(adapter);
    }

    /**
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:
     * @param cls 要移除的Adapter类
     */
    public void removeAdapter(Class<?> cls) {
        for (YYBaseAdapter adapter : adapterStack) {
            if (adapter.getClass().equals(cls)) {
                adapterStack.remove(adapter);
            }
        }
    }

    /**
     *  Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description      通知所有的目标Adapter类，进行更新
     *
     * @param cls 要更新的Adapter类
     */
    public void updateDate(Class<?> cls) {
        for (YYBaseAdapter adapter : adapterStack){
            if (adapter.getClass().equals(cls)) {
                adapter.notifyDataSetChanged();
            }
        }
    }


}