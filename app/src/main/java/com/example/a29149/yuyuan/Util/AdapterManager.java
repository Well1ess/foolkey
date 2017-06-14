package com.example.a29149.yuyuan.Util;

import android.app.Activity;
import android.content.Context;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
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
    public void updateData(Class<?> cls) {
        for (YYBaseAdapter adapter : adapterStack){
            if (adapter.getClass().equals(cls)) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  更新某种Adapter的某个DTO
     *               由子类的Adapter执行
     * @param cls    需要更新的Adapter类型
     * @param newDTO 新的Adapter
     */
    public void updateData(Class<?> cls, AbstractDTO newDTO){
        for (YYBaseAdapter adapter : adapterStack){
            if (adapter.getClass().equals(cls)) {
                adapter.updateData(newDTO);
                //adapter.notifyDataSetChanged(); 这一步移交给Adapter做了
            }
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  删除某种Adapter的某个DTO，由子类去实现
     *
     * @param cls    需要更新的Adapter类型
     * @param oldDTO 要删除的DTO
     */
    public void removeDate(Class<?> cls, AbstractDTO oldDTO){
        for (YYBaseAdapter adapter : adapterStack) {
            if (adapter.getClass().equals(cls)) {
                adapter.remove( oldDTO );
            }
        }
    }

    /**
     *
     * Author:       geyao
     * Date:         2017/6/14
     * Email:        gy2016@mail.ustc.edu.cn
     * Description:  添加某个dto，不管原list里有没有这个dto
     * @param cls    要更改的Adapter
     * @param dto    要添加的dto
     * @param headFlag
     *               true：添加到首
     *               false：添加到尾
     */
    public void addData(Class<?> cls, AbstractDTO dto, boolean headFlag){
        for (YYBaseAdapter adapter : adapterStack ) {
            if (adapter.getClass().equals(cls)) {
                if (headFlag){
                    adapter.addData(0, dto);
                }else {
                    adapter.addData( dto );
                }
            }
        }
    }

}