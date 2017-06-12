package com.example.a29149.yuyuan.Search.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a29149.yuyuan.DTO.RewardDTO;
import com.example.a29149.yuyuan.DTO.RewardWithStudentSTCDTO;
import com.example.a29149.yuyuan.DTO.StudentDTO;
import com.example.a29149.yuyuan.Enum.TechnicTagEnum;
import com.example.a29149.yuyuan.Main.MainStudentActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Activity.RewardActivity;
import com.example.a29149.yuyuan.ModelStudent.Discovery.Adapter.RewardAdapter;
import com.example.a29149.yuyuan.R;
import com.example.a29149.yuyuan.Search.GetSearchResultEvent;
import com.example.a29149.yuyuan.Search.SearchAction;
import com.example.a29149.yuyuan.Search.SearchActivity;
import com.example.a29149.yuyuan.Util.Annotation.AnnotationUtil;
import com.example.a29149.yuyuan.Util.Annotation.ViewInject;
import com.example.a29149.yuyuan.Util.AppManager;
import com.example.a29149.yuyuan.Widget.DynamicListView;
import com.example.a29149.yuyuan.Widget.SlideRefreshLayout;

import java.util.ArrayList;
import java.util.List;
//FIXME TODO 自习对比了代码，依然不会显示数据
public class RewardSearchFragment extends YYSearchBaseFragment {
    private static final String TAG = "RewardSearchFragment";
    //下拉刷新的Layout
    @ViewInject(R.id.srl_slide_layout)
    private SlideRefreshLayout mSlideLayout;

    //上划到底部动态更新的List
    @ViewInject(R.id.content)
    private DynamicListView mRewardList;

    //填充数据的Adapter
    private RewardAdapter mListAdapter;

    //搜索的异步任务
    private SearchAction searchAction ;

    //缓存当前view，方便再次切换到这个view时，不需要执行onCreateView方法
    private View view;
    private Context mContext;

    //搜索到的结果集合
    private List<RewardWithStudentSTCDTO> dataList = new ArrayList<>();

    //记录请求的页数
    int pageNo = 1;

    String keyValue;

    public RewardSearchFragment() {
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

            //新建Action
            SearchActivity searchActivity = (SearchActivity) AppManager.getActivity(SearchActivity.class);
            searchAction = new SearchAction(searchActivity);

            //新建adapter
            mListAdapter = new RewardAdapter(mContext);
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

            //TODO FIXME 这样写死，是有数据的，应该还是数据没有set进去 而且搜索以后，没有刷新
            RewardWithStudentSTCDTO rewardWithStudentSTCDTO = new RewardWithStudentSTCDTO();
            RewardDTO rewardDTO = new RewardDTO();
            StudentDTO studentDTO = new StudentDTO();

            rewardDTO.setTopic(" 11");
            rewardDTO.setPrice(11.0);
            rewardDTO.setTechnicTagEnum(TechnicTagEnum.Android);
            studentDTO.setNickedName("  ");

            rewardWithStudentSTCDTO.setRewardDTO( rewardDTO );
            rewardWithStudentSTCDTO.setStudentDTO(studentDTO);


            dataList.add( rewardWithStudentSTCDTO );
            mListAdapter.setData(dataList);
            mRewardList.setAdapter(mListAdapter);




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
                            search( pageNo + "", keyValue);
                            pageNo++;
                        }
                    });


        }

        return view;
    }




    /**
     * 更新 搜索界面 的信息显示
     * 一般发生在点悬赏进去以后，对悬赏信息进行了改动、删除
     * 这个更新操作，放在RewardActivity调用
     */
    public void updateSearchRewardList(){
        mListAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getResult(GetSearchResultEvent searchResultEvent) {
        keyValue = searchResultEvent.getKeyValue();
        if (searchResultEvent.isResult()) {
            mListAdapter.notifyDataSetChanged();
            pageNo++;
        }
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
                Log.d(TAG, "handleResult: 186 " + data.size());
                //TODO 把搜索到的结果不加判断地直接赋值
                dataList = data;
                //先设置数据
                RewardAdapter mListAdapter1 = new RewardAdapter(mContext);
                mListAdapter1.setData(data);
                //绑定Adapter与ListView
                mRewardList.setAdapter(mListAdapter1);
            }
        });

        //执行顺序 1
        searchAction.execute("reward", pageNo + "", keyValue);
    }
}
