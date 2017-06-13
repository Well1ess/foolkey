package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a29149.yuyuan.AbstractObject.AbstractDTO;
import com.example.a29149.yuyuan.AbstractObject.AbstractFragment;
import com.example.a29149.yuyuan.AbstractObject.YYBaseAdapter;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Search.action.SearchAction;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的Fragment都要继承这个抽象类
 * Created by geyao on 2017/6/12.
 */

public abstract class YYSearchBaseFragment extends AbstractFragment {

    //下拉刷新的Layout
    protected SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    protected DynamicListView listView;

    //填充数据的Adapter
    protected YYBaseAdapter mListAdapter;

    //搜索的异步任务
    protected SearchAction searchAction ;

    //缓存当前view，方便再次切换到这个view时，不需要执行onCreateView方法
    protected View view;
    protected Context mContext;

    //搜索到的结果集合
    protected List<AbstractDTO> dataList = new ArrayList<>();

    //记录请求的页数
    int pageNo = 1;

    //搜索的关键字
    String keyValue;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 判断view有没有创建，如果创建了，则不需要重新加载。
        mContext = getContext();
        if (view == null) {
            //绑定UI
            view = inflater.inflate(R.layout.fragment_reward_discovery, container, false);
            listView = (DynamicListView) view.findViewById(R.id.content);
            mSlideLayout = (SlideRefreshLayout) view.findViewById(R.id.srl_slide_layout);

            //子类自己的构造函数
            init();

            //设置列表动态加载
            listView.setOnLoadingListener(new DynamicListView.onLoadingListener() {
                @Override
                public void setLoad() {
                    //TODO:网络传输，这里就算没有结果，也不会消失转转转的动画
                    search(  pageNo+"", keyValue);
                    pageNo++;
                }
            });
            //设置列表下拉时的刷新
            mSlideLayout.setRotateView(view.findViewById(R.id.iv_refresh));
            mSlideLayout.setOnSlideRefreshListener(
                    new SlideRefreshLayout.onSlideRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //TODO:网络通信
                            //获取主页的热门课程
                            if (MainStudentActivity.shapeLoadingDialog != null) {
                                MainStudentActivity.shapeLoadingDialog.show();
                            }
                            //由于是刷新，所以首先清空所有数据
                            pageNo = 1;
                            search( pageNo + "", keyValue);
                            pageNo++;
                        }
                    });


            //绑定Adapter与ListView
            listView.setAdapter(mListAdapter);
        }
        return view;
    }


    /**
     * 各个子类自己的构造方法
     * @Author:        geyao
     * @Date:          2017/6/13
     * @Description: 需要新建Adapter，设定listView的点击事件
     */
    protected abstract void init();

    /**
     * 更新 搜索界面 的信息显示
     * 一般发生在点悬赏进去以后，对悬赏信息进行了改动、删除
     * 这个更新操作，放在RewardActivity调用
     */
    public void updateSearchRewardList(){
        mListAdapter.notifyDataSetInvalidated();
    }


    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索，由外部调用
     * @param pageNo    页码
     * @param keyValue  关键字
     */
    public void search(String pageNo, String keyValue){
        searchAction = new SearchAction( (SearchActivity) getActivity());

        /**
         * 搜索的回调，在这里处理ListView，Adapter，与数据的关系
         * 在别的地方只需要做Action.execute即可
         * 因为搜索的Action总是会调用这个方法
         * 所以不需要重复绑定adapter与ListView
         * @Author:        geyao
         * @Date:          2017/6/12
         * @Description:
         */
        searchAction.setAfterResult(new SearchAction.AfterResult() {
            @Override
            public void handleResult(List data) {
                //判空处理
                //执行顺序2
                if (data == null){
                    data = new ArrayList();
                }
                //TODO 把搜索到的结果不加判断地直接赋值
                dataList = data;
                //设置数据
                mListAdapter.setData(data);

            }
        });

        if (keyValue == null || keyValue.equals(""))
            keyValue = onTypeListener.getKeyWord();

        //执行顺序 1
        searchAction.execute("reward", pageNo + "", keyValue);
    }


    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索的抽象方法，由SearchActivity调用
     * @param pageNo
     * @param keyValue
     */
//    public abstract void search(String pageNo, String keyValue);

    /**
     * @Author:        geyao
     * @Date:          2017/6/13
     * @Description:   获取搜索框输入的回调函数，由Activity在onCreateView方法里注册
     */
    public interface OnTypeListener{
        public String getKeyWord();
    }

    /**
     * @Author:        geyao
     * @Date:          2017/6/13
     * @Description:   Activity的匿名内部类
     */
    protected OnTypeListener onTypeListener;

    /**
     * @Author:        geyao
     * @Date:          2017/6/13
     * @Description:   设值的方法
     * @param onTypeListener
     */
    public void setOnTypeListener(OnTypeListener onTypeListener){
        this.onTypeListener = onTypeListener;
    }
}
