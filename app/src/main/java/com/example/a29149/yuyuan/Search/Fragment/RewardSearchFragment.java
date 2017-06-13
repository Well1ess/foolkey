package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardAdapter;
import com.example.a29149.yuyuan.Search.action.SearchAction;

//FIXME TODO 自习对比了代码，依然不会显示数据
public class RewardSearchFragment extends YYSearchBaseFragment {
    private static final String TAG = "RewardSearchFragment";

    /**
     * 各个子类自己的构造方法
     *
     * @Author: geyao
     * @Date: 2017/6/13
     * @Description: 需要新建Adapter，设定listView的点击事件
     */
    @Override
    protected void init() {
        //新建adapter
        mListAdapter = new RewardAdapter(mContext);

        //给每个item设置点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查看某个具体的课程订单
                Intent intent = new Intent(mContext, RewardActivity.class);
                //新建Bundle，放置具体的DTO
                Bundle bundle = new Bundle();
                //从类变量的List里获取具体的DTO
                bundle.putSerializable("DTO", dataList.get(position));
                //将Bundle放置在intent里，并开启新Activity
                intent.putExtras( bundle );
                startActivity( intent );
            }
        });

        //设定搜索条件
        condition = SearchAction.FILTER2;
    }

}
