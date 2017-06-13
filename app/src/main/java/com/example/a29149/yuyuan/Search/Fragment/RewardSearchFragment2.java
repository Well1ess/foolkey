package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.action.YYAbstractSearchAction;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;
import com.example.a29149.yuyuan.controller.search.SearchRewardController;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class RewardSearchFragment2 extends YYSearchBaseFragment {
    private static final String TAG = "RewardSearchFragment2";
    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;

    //填充数据的Adapter
    private RewardAdapter mListAdapter;

    //缓存当前view，方便再次切换到这个view时，不需要执行onCreateView方法
    private View view;
    private Context mContext;

    //搜索到的结果集合
    private List<RewardWithStudentSTCDTO> dataList = new ArrayList<>();

    //记录请求的页数
    int pageNo = 1;

    String keyValue;

    public RewardSearchFragment2() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 判断view有没有创建，如果创建了，则不需要重新加载。
        mContext = getContext();
        if (view == null) {
            //绑定UI
            view = inflater.inflate(R.layout.fragment_reward_discovery, container, false);
            AnnotationUtil.injectViews(this, view);
            AnnotationUtil.setClickListener(this, view);
            Log.d(TAG, "onCreateView: 73");

            //新建adapter
            mListAdapter = new RewardAdapter(mContext);

            //如果不在这里set，就会出错，这样以后，UI就正常了，起码可以拖拽
            mRewardList.setAdapter( mListAdapter );
            //不管先设置数据，还是先绑定UI，只要在OnCreate方法中，就没有问题

            //TODO FIXME 这样写死，有数据
//            RewardWithStudentSTCDTO rewardWithStudentSTCDTO = new RewardWithStudentSTCDTO();
//            RewardDTO rewardDTO = new RewardDTO();
//            StudentDTO studentDTO = new StudentDTO();
//
//            rewardDTO.setTopic(" 11");
//            rewardDTO.setPrice(11.0);
//            rewardDTO.setTechnicTagEnum(TechnicTagEnum.Android);
//            studentDTO.setNickedName("  ");
//
//            rewardWithStudentSTCDTO.setRewardDTO( rewardDTO );
//            rewardWithStudentSTCDTO.setStudentDTO(studentDTO);
//
//            showList(rewardWithStudentSTCDTO);

            //FIXME 目前只有按钮点的搜索无法使用了

            //给每个item设置点击监听器
            mRewardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            //设置列表动态加载
            mRewardList.setOnLoadingListener(new DynamicListView.onLoadingListener() {
                @Override
                public void setLoad() {
                    //TODO:网络传输
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
                            //TODO 这里数据写死的
                            search( pageNo + "", "学习");
                            pageNo++;
                        }
                    });
        }
//        search("1", "");
        return view;
    }

    private void showList(List<RewardWithStudentSTCDTO> rewardWithStudentSTCDTO){
        dataList.addAll( rewardWithStudentSTCDTO );
        mListAdapter.setData(rewardWithStudentSTCDTO);
    }

    /**
     * 更新 搜索界面 的信息显示
     * 一般发生在点悬赏进去以后，对悬赏信息进行了改动、删除
     * 这个更新操作，放在RewardActivity调用
     */
    public void updateSearchRewardList(){
        mListAdapter.notifyDataSetChanged();
    }

    /**
     * @Author:        geyao
     * @Date:          2017/6/12
     * @Description:   搜索，由外部调用
     * @param pageNo    页码
     * @param keyValue  关键字
     */
    public void search(String pageNo, String keyValue){
        RewardSearchAction searchAction = new RewardSearchAction();
        searchAction.execute("reward", pageNo + "", keyValue);
    }

    /**
     * 搜索悬赏的Action
     * Created by geyao on 2017/6/12.
     */

    public class RewardSearchAction extends YYAbstractSearchAction {

        //网络传输
        private SearchRewardController searchRewardController = new SearchRewardController();
        /**
         * @Author: geyao
         * @Date: 2017/6/12
         * @Description: 搜索前做的事情
         */
        @Override
        protected String before( String... params) {
            //网络传输
            mPageNo = params[1];
            searchRewardController.setKeyWord(params[2]);
            searchRewardController.setPageNo(params[1]);
            searchRewardController.execute();
            return searchRewardController.getResult();
        }

        /**
         * @Author: geyao
         * @Date: 2017/6/12
         * @Description: 搜索后做的事情，请求第一页
         */
        @Override
        protected void getOnePage() {
            Log.d(TAG, "getOnePage: 186");
            //TODO FIXME 这样写死，没有数据
//            RewardWithStudentSTCDTO rewardWithStudentSTCDTO = new RewardWithStudentSTCDTO();
//            RewardDTO rewardDTO = new RewardDTO();
//            StudentDTO studentDTO = new StudentDTO();
//
//            rewardDTO.setTopic(" 11");
//            rewardDTO.setPrice(11.0);
//            rewardDTO.setTechnicTagEnum(TechnicTagEnum.Android);
//            studentDTO.setNickedName("  ");
//
//            rewardWithStudentSTCDTO.setRewardDTO( rewardDTO );
//            rewardWithStudentSTCDTO.setStudentDTO(studentDTO);
//
//
//            dataList.add( rewardWithStudentSTCDTO );
//            mListAdapter.setData(dataList);
//            mRewardList.setAdapter(mListAdapter);
            updateSearchRewardList();
            showList(searchRewardController.getRewardWithStudentSTCDTOList());
            updateSearchRewardList();
        }

        /**
         * @Author: geyao
         * @Date: 2017/6/12
         * @Description: 搜索后做的事情，请求更多页
         */
        @Override
        protected void getMorePage() {

        }
    }
}
