package com.example.a29149.yuyuan.AbstractObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 张丽华 on 2017/6/10.
 * Description: 所以Adapter的基类
 */
public abstract class YYBaseAdapter<T> extends BaseAdapter {

    //保存数据
    private List<T> data;
    //上下文
    private Context mContext;
    //加载布局
    private LayoutInflater layoutInflater;

    /**
     * @param data    数据
     * @param context 上下文
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public YYBaseAdapter(List<T> data, Context context) {
        this.data = data;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if (data == null)
            return null;
        else if (data.size() - 1 < position)
            return data;

        return null;
    }

    @Override
    public long getItemId(int position) {
        if (data == null)
            return 0;
        else
            return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, layoutInflater);
    }

    public abstract View createView(int position, View convertView, LayoutInflater layoutInflater);

    /**
     * @param data 目标数据
     * @return
     * @discription 添加单个数据
     * @author 29149
     * @time 2017/6/10 15:13
     */
    public boolean addData(T data) {
        if (data == null)
            return false;
        boolean result = this.data.add(data);
        notifyDataSetChanged();
        return result;
    }

    /**
     * @param newData 新的数据集
     * @return 结果
     * @discription 添加数据集
     * @author 29149
     * @time 2017/6/10 15:15
     */
    public boolean addData(List<T> newData) {
        if (data == null) {
            data = newData;
            notifyDataSetChanged();
            return true;
        } else {
            boolean result = data.addAll(newData);
            notifyDataSetChanged();
            return result;
        }
    }

    /**
     * @param position 位置
     * @param data     目标数据
     * @return 结果
     * @discription 特定位置添加数据
     * @author 29149
     * @time 2017/6/10 15:15
     */
    public boolean addData(int position, T data) {
        if (data == null)
            return false;
        this.data.add(position, data);
        notifyDataSetChanged();
        return true;
    }

    /**
     * @param position 位置
     * @return 结果
     * @discription 特定位置移出数据
     * @author 29149
     * @time 2017/6/10 15:16
     */
    public T remove(int position) {
        if (position > data.size())
            return null;
        T temp = data.remove(position);
        notifyDataSetChanged();
        return temp;
    }

    /**
     * @param data 目标数据
     * @return 结果
     * @discription 移出目标数据
     * @author 29149
     * @time 2017/6/10 15:16
     */
    public boolean remove(T data) {
        boolean remove = this.data.remove(data);
        notifyDataSetChanged();
        return remove;
    }

    /**
     * @discription 清空数据
     * @author 29149
     * @time 2017/6/10 15:16
     */
    public void resetData() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * @param data 新的数据集
     * @discription 重置数据集
     * @author 29149
     * @time 2017/6/10 15:16
     */

    public void resetData(List<T> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public List<T> getData(){
        return data;
    }
}
