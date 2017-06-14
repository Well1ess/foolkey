package com.example.a29149.yuyuan.AbstractObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.a29149.yuyuan.Util.AdapterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张丽华 on 2017/6/10.
 * Description: 所以Adapter的基类
 */
public abstract class YYBaseAdapter<T> extends BaseAdapter {

    //保存数据
    protected List<T> dataList = new ArrayList<>();
    //上下文
    protected Context mContext;
    //加载布局
    protected LayoutInflater layoutInflater;

    private static final String TAG = "YYBaseAdapter";

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
        AdapterManager.getInstance().addAdapter(this);
    }

    /**
     * 构造函数
     * @param mContext 上下文
     */
    public YYBaseAdapter(Context mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        AdapterManager.getInstance().addAdapter(this);
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
        if (newData == null)
            return false;
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
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   向数据源中添加之前不存在的数据
     * @param newData
     * @param head 如果是true，头插，否则尾插
     * @return
     */
    public boolean addExtinctDataList(List<T> newData, boolean head){
        if (dataList == null) { // 源list为空
            if (newData != null) {
                dataList = newData;
                notifyDataSetChanged();
                return true;
            }else {
                dataList = new ArrayList<>();
                notifyDataSetChanged();
                return true;
            }
        }else {
            //源List不为空
            //目标list为空
            if (newData == null) {
                return true;
            }
            //目标list不为空
            else {
                for (T data : newData){
                    if (!dataList.contains(data)){
                        //源数据不含目标数据
                        if (head) // 头插
                            dataList.add(0, data);
                        else { // 尾插
                            dataList.add(data);
                        }
                    }

                }
                notifyDataSetChanged();
                return true;
            }
        }
    }

    /**
     * 往头部添加数据
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   往头部添加数据
     * @param newData
     * @return
     */
    public boolean addDateListToHead(List<T> newData){
        if (newData == null)
            return false;
        if (dataList == null) {
            dataList = newData;
            notifyDataSetChanged();
            return true;
        } else {
            for (T data : newData){
                dataList.add(0, data);
            }
            return true;
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
        addData(position, data);
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
    public void clearData() {
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

    public void setData(List<T> data) {
        if (data != null) {
            this.dataList = data;
            Log.d(TAG, "setData: 241 " +  data.size() );
            notifyDataSetChanged();
        }
    }

    public List<T> getDataList(){
        return dataList;
    }
}
