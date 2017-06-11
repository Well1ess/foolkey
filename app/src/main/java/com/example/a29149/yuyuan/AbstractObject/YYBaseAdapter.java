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
    protected List<T> dataList;
    //上下文
    protected Context mContext;
    //加载布局
    protected LayoutInflater layoutInflater;

    /**
     * @param dataList    数据
     * @param context 上下文
     * @discription 构造函数
     * @author 29149
     * @time 2017/6/10 15:12
     */
    public YYBaseAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        if (dataList == null)
            return null;
        if (position >= 0 || position < dataList.size())
            return dataList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (dataList == null)
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
        boolean result = this.dataList.add(data);
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
    public boolean addDataLst(List<T> newData) {
        if (dataList == null) {
            dataList = newData;
            notifyDataSetChanged();
            return true;
        } else {
            boolean result = dataList.addAll(newData);
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
        this.dataList.add(position, data);
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
        if (position > dataList.size())
            return null;
        T temp = dataList.remove(position);
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
        boolean remove = this.dataList.remove(data);
        notifyDataSetChanged();
        return remove;
    }

    /**
     * @discription 清空数据
     * @author 29149
     * @time 2017/6/10 15:16
     */
    public void resetData() {
        if (dataList != null) {
            dataList.clear();
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
            this.dataList = data;
            notifyDataSetChanged();
        }
    }

    public List<T> getDataList(){
        return dataList;
    }
}
